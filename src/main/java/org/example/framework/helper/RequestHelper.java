package org.example.framework.helper;

import com.alibaba.fastjson.JSONObject;
import org.example.framework.bean.FileParam;
import org.example.framework.bean.FormParam;
import org.example.framework.bean.Param;
import org.example.framework.util.ArrayUtil;
import org.example.framework.util.CodeUtil;
import org.example.framework.util.StreamUtil;
import org.example.framework.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

public final class RequestHelper {
    public static Param createParam(HttpServletRequest request) throws IOException {
        List<FormParam> formParamList = new ArrayList<FormParam>();
        String contentType = request.getContentType();
        if(StringUtil.isNotEmpty(contentType) && contentType.equals("application/json")){
            formParamList.addAll(parseParameterFromBody(request));
        }else{
            formParamList.addAll(parseParameterNames(request));
            formParamList.addAll(parseInputStream(request));
        }



        return new Param(formParamList);
    }

    private static List<FormParam> parseInputStream(HttpServletRequest request) throws IOException{
        List<FormParam> list = new ArrayList<FormParam>();
        String body = CodeUtil.decodeURL(StreamUtil.getStringFromInputStream(request.getInputStream()));
        if(StringUtil.isNotEmpty(body)){
            String[] kvs = StringUtil.splitString(body, "&");
            if(ArrayUtil.isNotEmpty(kvs)){
                for (String kv : kvs) {
                    String[] array = StringUtil.splitString(kv, "=");
                    if(ArrayUtil.isNotEmpty(array) && array.length == 2){
                        String key = array[0];
                        String value = array[1];
                        list.add(new FormParam(key,value));
                    }
                }
            }
        }
        return list;
    }

    private static List<FormParam> parseParameterFromBody(HttpServletRequest request){
        List<FormParam> list = new ArrayList<FormParam>();
        BufferedReader bufferedReader;
        try {
            bufferedReader = request.getReader();
            StringBuilder sb = new StringBuilder();
            String segment;
            while((segment = bufferedReader.readLine()) != null){
                sb.append(segment);
            }
            String body = sb.toString();
            if(StringUtil.isNotEmpty(body)){
                JSONObject jsonObject = JSONObject.parseObject(body);
                for (String key : jsonObject.keySet()) {
                    Object value = jsonObject.get(key);
                    list.add(new FormParam(key,value));
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    private static List<FormParam> parseParameterNames(HttpServletRequest request) {
        List<FormParam> list = new ArrayList<FormParam>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while(parameterNames.hasMoreElements()){
            String paramName = parameterNames.nextElement();
            String[] parameterValues = request.getParameterValues(paramName);
            if(ArrayUtil.isNotEmpty(parameterValues)){
                if(parameterValues.length == 1){
                    list.add(new FormParam(paramName,parameterValues[0]));
                }else{
                    StringBuilder sb = new StringBuilder();
                    for(int i = 0;i < parameterValues.length;i++){
                        sb.append(parameterValues[i]);
                        if(i != parameterValues.length - 1){
                            sb.append(StringUtil.STRING_SEPARATOR);
                        }
                    }
                    list.add(new FormParam(paramName,sb.toString()));
                }
            }
        }
        return list;
    }
}
