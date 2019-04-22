package com.booxj.gp.core.utils.spring;

import com.booxj.gp.core.utils.CollectionUtils;
import org.junit.Test;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @Company: 浙江核新同花顺网络信息股份有限公司
 * @ClassName: ClassUtilsTest.java
 * @Description: TODO
 * @Author: wangbo@myhexin.com
 * @CreateDate 2019/4/22 16:18
 * @version: 2.1.0
 */
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
}
