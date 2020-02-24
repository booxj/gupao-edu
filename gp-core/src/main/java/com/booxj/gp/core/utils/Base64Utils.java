package com.booxj.gp.core.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO description
 *
 * @author booxj
 * @create 2019/7/12 8:41
 * @since
 */
public class Base64Utils {

    private static final String regex = "data:image/(.*?);base64";
    private static final Pattern pattern = Pattern.compile(regex);


    /**
     * 将base64 转化为图片
     *
     * @param filePath
     * @param fileName
     * @param base64
     * @throws IOException
     */
    public static void createImageFromBase64(String filePath, String fileName, String base64) throws IOException {
        filePath = filePath + "/" + fileName + "." + getImageType(base64);
        base64 = base64.replaceFirst("data:(.+?);base64,", "");
        Files.write(Paths.get(filePath), Base64.getDecoder().decode(base64), StandardOpenOption.CREATE);
    }


    public static String getImageType(String base64) {
        Matcher m = pattern.matcher(base64);
        if (m.find()) {
            return m.group(1);
        } else {
            throw new IllegalArgumentException("base64 is illegal");
        }
    }
}
