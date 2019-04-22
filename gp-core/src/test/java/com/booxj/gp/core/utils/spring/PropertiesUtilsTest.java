package com.booxj.gp.core.utils.spring;


import org.junit.Test;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtilsTest {


    @Test
    public void findPackagePropertiesTest() throws IOException {
        Properties properties = PropertiesUtils.findPackageProperties("classpath:*.properties,classpath:inner/*.properties");
        assert (properties != null);
        System.out.println(properties);
    }
}
