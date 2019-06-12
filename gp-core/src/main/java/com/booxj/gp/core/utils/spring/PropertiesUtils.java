package com.booxj.gp.core.utils.spring;

import com.booxj.gp.core.utils.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Properties;
import java.util.Set;


public class PropertiesUtils {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);

    public static Properties findPackageProperties(String scanPackages) throws IOException {

        Properties properties = new Properties();

        //验证及排重包路径,避免父子路径多次扫描
        Set<String> packages = checkPackage(scanPackages);

        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        for (String location : packages) {
            for (Resource resource : resolver.getResources(location)) {
                PropertiesLoaderUtils.fillProperties(properties, new EncodedResource(resource, (Charset) null));
//                PropertiesLoaderUtils.loadProperties(resource);
            }
        }

        return properties;
    }

    static Set<String> checkPackage(String scanPackages) {
        return CollectionUtils.String2Set(scanPackages);
    }

}
