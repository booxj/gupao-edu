package com.booxj.gp.pattern.delegate.mvc;

import com.booxj.gp.pattern.delegate.mvc.controller.HelloController;
import net.sf.cglib.proxy.MethodInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ServletDispatcher {

    private List<Handler> handlerMapping = new ArrayList<>();

    public ServletDispatcher() {
        try {
            Class<?> clazz = HelloController.class;
            Handler handler = new Handler(clazz.newInstance(), clazz.getMethod("Hello", new Class[]{String.class}), "/hello");
            handlerMapping.add(handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doService(HttpServletRequest request, HttpServletResponse response) {
        doDispatch(request, response);
    }

    private void doDispatch(HttpServletRequest request, HttpServletResponse response) {
        //   如果按照J2EE的标准、每个url对对应一个Serlvet，url由浏览器输入
        String uri = request.getRequestURI();

        //2、Servlet拿到url以后，要做权衡（要做判断，要做选择）
        //   根据用户请求的URL，去找到这个url对应的某一个java类的方法

        //3、通过拿到的URL去handlerMapping（我们把它认为是策略常量）
        Handler handle = null;
        for (Handler h : handlerMapping) {
            if (uri.equals(h.getUrl())) {
                handle = h;
                break;
            }
        }

        //4、将具体的任务分发给Method（通过反射去调用其对应的方法）
        Object object = null;
        try {
            object = handle.getMethod().invoke(handle.getController(), request.getParameter("name"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //5、获取到Method执行的结果，通过Response返回出去
//        response.getWriter().write(object);
    }

    class Handler {
        private Object controller;
        private Method method;
        private String url;

        public Handler(Object controller, Method method, String url) {
            this.controller = controller;
            this.method = method;
            this.url = url;
        }

        public Object getController() {
            return controller;
        }

        public void setController(Object controller) {
            this.controller = controller;
        }

        public Method getMethod() {
            return method;
        }

        public void setMethod(Method method) {
            this.method = method;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
