package com.booxj.gp.core.utils.spring;

import com.booxj.gp.core.utils.CollectionUtils;
import com.booxj.gp.core.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;
import org.springframework.util.SystemPropertyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;


public class PropertiesUtils {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);

    private static final String XML_FILE_EXTENSION = ".xml";

    public static Properties findPackageProperties(String scanPackages) throws IOException {

        Properties properties = new Properties();

        //验证及排重包路径,避免父子路径多次扫描
        Set<String> packages = checkPackage(scanPackages);

        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        for (String location : packages) {
            for (Resource resource : resolver.getResources(location)) {
                fillProperties(properties, new EncodedResource(resource, (Charset) null), new DefaultPropertiesPersister());
            }
        }

        return properties;
    }

    static Set<String> checkPackage(String scanPackages) {
        return CollectionUtils.String2Set(scanPackages);
    }

    static void fillProperties(Properties props, EncodedResource resource, PropertiesPersister persister)
            throws IOException {

        InputStream stream = null;
        Reader reader = null;
        try {
            String filename = resource.getResource().getFilename();
            if (filename != null && filename.endsWith(XML_FILE_EXTENSION)) {
                stream = resource.getInputStream();
                persister.loadFromXml(props, stream);
            } else if (resource.requiresReader()) {
                reader = resource.getReader();
                persister.load(props, reader);
            } else {
                stream = resource.getInputStream();
                persister.load(props, stream);
            }
        } finally {
            if (stream != null) {
                stream.close();
            }
            if (reader != null) {
                reader.close();
            }
        }
    }

}
