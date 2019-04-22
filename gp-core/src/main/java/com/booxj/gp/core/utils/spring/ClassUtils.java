package com.booxj.gp.core.utils.spring;

import com.booxj.gp.core.utils.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.StringUtils;
import org.springframework.util.SystemPropertyUtils;

import java.io.IOException;
import java.util.*;

import static org.springframework.core.io.support.ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX;

/**
 *
 */
public class ClassUtils {

    private static final Logger logger = LoggerFactory.getLogger(ClassUtils.class);

    private static final String DEFAULT_RESOURCE_PATTERN = "*";

    private static ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
    private static MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);

    /**
     * 根据包限定名扫描所有的类
     * input:com.booxj.gp.core.spring   (多个地址用","隔开)
     * output:  com.booxj.gp.core.xx1,com.booxj.gp.core.xx2
     *
     * @param scanPackages
     * @return
     */
    public static Set<String> findPackageClass(String scanPackages) {
        if (StringUtils.isEmpty(scanPackages)) {
            return Collections.EMPTY_SET;
        }
        //验证及排重包路径,避免父子路径多次扫描
        Set<String> packages = checkPackage(scanPackages);

        Set<String> clazzSet = new HashSet<>();
        for (String basePackage : packages) {
            if (StringUtils.isEmpty(basePackage)) {
                continue;
            }
            // 构造扫描路径地址
            String packageSearchPath = buildSearchPath(basePackage);

            try {
                Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
                for (Resource resource : resources) {
                    //检查resource，这里的resource都是class
                    if (resource.getFile().isDirectory()) {
                        clazzSet.addAll(findPackageClass(
                                packageSearchPath.substring(0, packageSearchPath.length() - 1)
                                        + resource.getFilename()));
                    } else {
                        String clazz = loadClassName(metadataReaderFactory, resource);
                        clazzSet.add(clazz);
                    }

                }
            } catch (Exception e) {
                logger.error("获取包下面的类信息失败,package:" + basePackage, e);
            }

        }
        return clazzSet;
    }

    static String buildSearchPath(String basePackage) {
        if (basePackage.startsWith(CLASSPATH_ALL_URL_PREFIX)) {
            return org.springframework.util.ClassUtils.convertClassNameToResourcePath(
                    SystemPropertyUtils.resolvePlaceholders(basePackage))
                    + "/" + DEFAULT_RESOURCE_PATTERN;
        } else {
            return CLASSPATH_ALL_URL_PREFIX + org.springframework.util.ClassUtils.convertClassNameToResourcePath(
                    SystemPropertyUtils.resolvePlaceholders(basePackage))
                    + "/" + DEFAULT_RESOURCE_PATTERN;
        }

    }

    static String loadClassName(MetadataReaderFactory metadataReaderFactory, Resource resource)
            throws IOException {
        try {
            if (resource.isReadable()) {
                MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                if (metadataReader != null) {
                    return metadataReader.getClassMetadata().getClassName();
                }
            }
        } catch (Exception e) {
            logger.error("根据resource获取类名称失败", e);
        }
        return null;
    }


    /**
     * 包名去重
     *
     * @param scanPackages
     * @return
     */
    static Set<String> checkPackage(String scanPackages) {
        return CollectionUtils.String2Set(scanPackages);
    }

}
