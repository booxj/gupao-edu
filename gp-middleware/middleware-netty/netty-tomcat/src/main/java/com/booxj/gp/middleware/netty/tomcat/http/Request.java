package com.booxj.gp.middleware.netty.tomcat.http;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.util.List;
import java.util.Map;

public class Request {

    private HttpRequest httpRequest;

    public Request(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public String getMethod() {
        return httpRequest.method().name();
    }

    public String getUri() {
        return httpRequest.uri();
    }

    public boolean isKeepAlive() {
        return HttpUtil.isKeepAlive(httpRequest);
    }

    public Map<String, List<String>> getParameters() {
        QueryStringDecoder decoder = new QueryStringDecoder(httpRequest.uri());
        return decoder.parameters();
    }

    public String getParameter(String name) {
        Map<String, List<String>> params = getParameters();
        List<String> param = params.get(name);
        if (null != param) {
            return param.get(0);
        } else {
            return null;
        }
    }
}
