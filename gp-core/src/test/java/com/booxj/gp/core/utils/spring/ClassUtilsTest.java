package com.booxj.gp.core.utils.spring;

import com.booxj.gp.core.utils.CollectionUtils;
import org.junit.Test;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Set;


public class ClassUtilsTest {

    @Test
    public void findPackageClassTest() {
        Set<String> classSet = ClassUtils.findPackageClass("com.booxj.gp.core.utils.entity");
        assert (!CollectionUtils.isEmpty(classSet));
        classSet.stream().forEach(className -> {
            try {
                Controller controller = Class.forName(className).getAnnotation(Controller.class);
                Service service = Class.forName(className).getAnnotation(Service.class);
                Component component = Class.forName(className).getAnnotation(Component.class);

                if (controller != null || service != null || component != null) {
                    System.out.println(className);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }


    @Test
    public void getParameterNames() {

        LocalVariableTableParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        for (Method method : ClassUtils.class.getMethods()) {

           for (Parameter parameter:method.getParameters()){
               System.out.println(parameter);
           }
//            StringBuilder sb = new StringBuilder(method.getName() + " : ");
//            String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
//            if (parameterNames!=null&&parameterNames.length>0) {
//                for (int i = 0; i < parameterNames.length; i++) {
//                    sb.append(parameterNames[i]).append(",");
//                }
//            }
//            System.out.println(sb.toString());
        }
    }
}
