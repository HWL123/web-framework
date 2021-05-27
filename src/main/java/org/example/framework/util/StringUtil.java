package org.example.framework.util;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {

    public static final String STRING_SEPARATOR = String.valueOf((char)29);

    public static boolean isEmpty(String s){
        if(s != null){
            s  = s.trim();
        }
        return StringUtils.isBlank(s);
    }

    public static boolean isNotEmpty(String s){
        return StringUtils.isNotBlank(s);
    }

    public static String[] splitString(String str,String separator){
        return StringUtils.splitByWholeSeparator(str,separator);
    }
}
