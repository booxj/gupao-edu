package com.gp.mircoservice.client.customized.annotation;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * TODO description
 * <p>
 *
 * @author booxj
 * @create 2019/6/12 11:29
 * @since
 */
public class RequestMappingMethodInvocationHandler  implements InvocationHandler {

    private final String serviceName;

    private final BeanFactory beanFactory;

    public RequestMappingMethodInvocationHandler(String serviceName, BeanFactory beanFactory) {
        this.serviceName = serviceName;
        this.beanFactory = beanFactory;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // 过滤 @RequestMapping 方法
        GetMapping getMapping = AnnotationUtils.findAnnotation(method, GetMapping.class);

        if (getMapping != null) {
            // 得到 URI
            String[] uri = getMapping.value();
            StringBuilder urlBuilder = new StringBuilder("http://").append(serviceName).append("/").append(uri[0]);

            // 获取方法参数数量
            int count = method.getParameterCount();

            // 方法参数类型集合
            Class<?>[] paramTypes = method.getParameterTypes();
            Annotation[][] annotations = method.getParameterAnnotations();
            StringBuilder queryStringBuilder = new StringBuilder();

            for (int i = 0; i < count; i++) {
                Annotation[] paramAnnotations = annotations[i];
                Class<?> paramType = paramTypes[i];
                RequestParam requestParam = (RequestParam) paramAnnotations[i];

                if (requestParam != null) {
                    String paramName = "";

                    // HTTP 请求参数
                    String requestParamName = StringUtils.hasText(requestParam.value()) ? requestParam.value() :
                            paramName;
                    String requestParamValue = String.class.equals(paramType)
                            ? (String) args[i] : String.valueOf(args[i]);

                    queryStringBuilder.append("&")
                            .append(requestParamName).append("=").append(requestParamValue);
                }
            }

            String queryString = queryStringBuilder.toString();
            if (StringUtils.hasText(queryString)) {
                urlBuilder.append("?").append(queryString);
            }

            // http://${serviceName}/${uri}?${queryString}
            String url = urlBuilder.toString();

            // 获取 RestTemplate ， Bean 名称为“loadBalancedRestTemplate”
            // 获得 BeanFactory
            RestTemplate restTemplate = beanFactory.getBean("lbRestTemplate", RestTemplate.class);

            return restTemplate.getForObject(url, method.getReturnType());
        }

        return null;
    }
}
