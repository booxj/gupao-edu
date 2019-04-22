package com.booxj.gp.core.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


public class CollectionUtils {

    public static <E> boolean isEmpty(Collection<E> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 将带","的字符串分离并转化为Set<String>
     *
     * @param str
     * @return
     */
    public static Set<String> String2Set(String str) {
        Set<String> packages = new HashSet<>();
        Arrays.stream(str.split(StringUtils.COMMA)).forEach(s -> packages.add(s));
        return packages;
    }
}
