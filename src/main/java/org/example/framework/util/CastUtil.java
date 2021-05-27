package org.example.framework.util;

public final class CastUtil {

    public static String castString(Object obj){
        return castString(obj,"");
    }

    public static String castString(Object obj,String defaultValue){
        if(obj != null){
            return String.valueOf(obj);
        }else{
            return defaultValue;
        }
    }


    public static long castLong(Object obj){
        return castLong(obj,0);
    }

    public static long castLong(Object obj,long defaultValue){
        Long value = defaultValue;
        if(obj != null){
            String str = castString(obj);
            if(StringUtil.isNotEmpty(str)){
                try{
                    value = Long.parseLong(str);
                }catch (NumberFormatException e){
                    value = defaultValue;
                }
            }
        }
        return value;
    }

    public static double castDouble(Object obj){
        return castDouble(obj,0);
    }

    public static double castDouble(Object obj,double defaultValue) {
        double value = defaultValue;
        if (obj != null) {
            String str = castString(obj);
            if (StringUtil.isNotEmpty(str)) {
                try {
                    value = Double.parseDouble(str);
                } catch (NumberFormatException e) {
                    value = defaultValue;
                }
            }
        }
        return value;
    }

    public static boolean castBoolean(Object obj){
        return castBoolean(obj,false);
    }

    public static boolean castBoolean(Object obj,boolean defaultValue) {
        boolean value = defaultValue;
        if (obj != null) {
            value = Boolean.parseBoolean(castString(obj));
        }
        return value;
    }

    public static int castInteger(Object obj){
        return castInteger(obj,0);
    }

    public static int castInteger(Object obj,int defaultValue) {
        int value = defaultValue;
        if (obj != null) {
            String str = castString(obj);
            if (StringUtil.isNotEmpty(str)) {
                try {
                    value = Integer.parseInt(str);
                } catch (NumberFormatException e) {
                    value = defaultValue;
                }
            }
        }
        return value;
    }

}
