package org.example.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropsUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropsUtil.class);

    public static Properties loadPros(String fileName){
        Properties properties = null;
        InputStream inputStream = null;
        try{
            inputStream = ClassUtil.getClassLoader().getResourceAsStream(fileName);
            if(inputStream == null){
                throw  new FileNotFoundException(fileName + "file can not found");
            }
            try {
                properties = new Properties();
                properties.load(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }catch (Exception e){
            LOGGER.error("can not load property file");
            throw new RuntimeException(e);
        }finally {
            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    LOGGER.error("close inputStream failure", e);
                    throw new RuntimeException(e);
                }
            }
        }
        return properties;
    }

    public static String getString(Properties properties,String key){
        return getString(properties, key,"");
    }

    public static String getString(Properties properties,String key,String defaultValue){
        String value = defaultValue;
        if(properties.containsKey(key)){
            value = properties.getProperty(key);
        }
        return value;
    }

    public static int getInt(Properties properties,String key){
        return getInt(properties, key,0);
    }

    public static int getInt(Properties properties,String key,int defaultValue){
        int value = defaultValue;
        if(properties.contains(key)){
            value = CastUtil.castInteger(properties.getProperty(key));
        }

        return value;
    }

    public static boolean getBoolean(Properties properties,String key){
        return getBoolean(properties, key);
    }

    public static boolean getBoolean(Properties properties,String key,boolean defaultValue){
        boolean value = defaultValue;
        if(properties.containsKey(key)){
            value = CastUtil.castBoolean(properties.getProperty(key));
        }
        return value;
    }
}
