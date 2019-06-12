package com.gp.mircoservice.client.customized.loadbalance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.stream.Collectors;

/**
 * TODO description
 * <p>
 *
 * @author booxj
 * @create 2019/6/12 10:50
 * @since
 */
public class LoadBalanceRequestInterceptor implements ClientHttpRequestInterceptor {

    private volatile Map<String, Set<String>> urlsCache = new HashMap<>();

    @Autowired
    private DiscoveryClient discoveryClient;

    @Scheduled(fixedDelay = 60 * 1000)
    public void updateUrlsCache() {
        Map<String, Set<String>> newUrlsCache = new HashMap<>(this.urlsCache);

        discoveryClient.getServices().forEach(serverName -> {

            Set<String> newUrls = discoveryClient.getInstances(serverName).stream().map(s ->
                    s.isSecure() ?
                            "https://" + s.getHost() + ":" + s.getPort() :
                            "http://" + s.getHost() + ":" + s.getPort()

            ).collect(Collectors.toSet());

            newUrlsCache.put(serverName, newUrls);
        });
        this.urlsCache = newUrlsCache;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        URI requestURI = request.getURI();

        String serverName = requestURI.getHost();
        String uri = requestURI.getPath();

        //服务器列表快照
        List<String> urls = new LinkedList<>(urlsCache.get(serverName));

        int size = urls.size();
        int index = new Random().nextInt(size);
        //选择一台服务器
        String targetUrl = urls.get(index);

        //最终服务器url
        String actualURL = targetUrl + "/" + uri;
        if (requestURI.getQuery() != null) {
            actualURL += "?" + requestURI.getQuery();
        }

        System.out.println("本次请求的URL:" + actualURL);

        URL url = new URL(actualURL);
        URLConnection urlConnection = url.openConnection();

        HttpHeaders httpHeaders = new HttpHeaders();
        InputStream responseBody = urlConnection.getInputStream();

        return new SimpleClientHttpResponse(httpHeaders, responseBody);
    }

    private static class SimpleClientHttpResponse implements ClientHttpResponse {

        private HttpHeaders headers;
        private InputStream body;

        public SimpleClientHttpResponse(HttpHeaders headers, InputStream body) {
            this.headers = headers;
            this.body = body;
        }

        @Override
        public HttpStatus getStatusCode() throws IOException {
            return HttpStatus.OK;
        }

        @Override
        public int getRawStatusCode() throws IOException {
            return 200;
        }

        @Override
        public String getStatusText() throws IOException {
            return "OK";
        }

        @Override
        public void close() {

        }

        @Override
        public InputStream getBody() throws IOException {
            return this.body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return this.headers;
        }
    }

}
