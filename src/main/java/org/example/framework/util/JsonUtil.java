package org.example.framework.util;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class JsonUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);
    //private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * parse Object to Json
     * @param obj
     * @return
     */
    public static String toJson(Object obj){
        String json;
        try {
            json = JSONObject.toJSONString(obj);
        }
        catch (Exception e){
            LOGGER.error("parse to JSON failure ",e);
            throw new RuntimeException(e);
        }
        return json;
    }

    /**
     * parse Json string to Object
     * @param jsonStr
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String jsonStr,Class<T> type){
        T pojo;
        try {
            pojo = (T)JSONObject.parseObject(jsonStr);
        }catch (Exception e){
            LOGGER.error("parse to pojo from json failure",e);
            throw new RuntimeException(e);
        }
        return pojo;
    }


}
