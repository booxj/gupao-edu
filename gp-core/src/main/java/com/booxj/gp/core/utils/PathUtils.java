package com.booxj.gp.core.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 路径工具类
 *
 * @author booxj
 * @create 2019/6/25 9:32
 * @since
 */
public class PathUtils {

    private PathUtils() {
    }

    public static String getRootPath() throws UnsupportedEncodingException {
        return getAbsolutePath("");
    }
    public static String getAbsolutePath(String resourceName) throws UnsupportedEncodingException {
        String path = Thread.currentThread().getContextClassLoader().getResource(resourceName).getPath();
        path = URLDecoder.decode(path, "utf-8");
        return path.substring(1);
    }
}
