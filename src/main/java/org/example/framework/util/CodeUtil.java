package org.example.framework.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public final class CodeUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CodeUtil.class);

    public static String encodeURL(String source){
        String target;
        try {
            target = URLEncoder.encode(source,"UTF-8");
        } catch (Exception e) {
            LOGGER.error("encode url " + source + " fail," + e);
            throw new RuntimeException(e);
        }

        return target;
    }

    public static String decodeURL(String target){
        String source;
        try {
            source = URLDecoder.decode(target,"UTF-8");
        } catch (Exception e) {
            LOGGER.error("decode url " + target + " fail," + e);
            throw new RuntimeException(e);
        }

        return source;
    }

    public static String md5(String source){
        return DigestUtils.md5Hex(source);
    }

}
