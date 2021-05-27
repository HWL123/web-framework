package org.example.framework.bean;

import org.example.framework.util.CastUtil;
import org.example.framework.util.CollectionUtil;
import org.example.framework.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Param {
    private static List<FileParam> fileParamList;
    private static List<FormParam> formParamsList;

    public Param(List<FileParam> fileParamList, List<FormParam> formParamsList) {
        this.fileParamList = fileParamList;
        this.formParamsList = formParamsList;
    }

    public Param(List<FormParam> formParamsList) {
        this.formParamsList = formParamsList;
    }

    /**
     * return the file param in the form of map
     * @return
     */
    public static Map<String,List<FileParam>> getFileMap(){
        Map<String,List<FileParam>> fileParamMap = new HashMap<String, List<FileParam>>();
        if(CollectionUtil.isNotEmpty(fileParamList)){
            for (FileParam fileParam : fileParamList) {
                String fieldName = fileParam.getFieldName();
                List<FileParam> list = new ArrayList<FileParam>();
                if(fileParamMap.containsKey(fieldName)){
                    list = fileParamMap.get(fieldName);
                }else{
                    list = new ArrayList<FileParam>();
                }
                list.add(fileParam);
                fileParamMap.put(fieldName,list);
            }
        }

        return fileParamMap;
    }

    /**
     * return the form param in the form of map
     * @return
     */
    public static Map<String,Object> getFieldMap(){
        Map<String,Object> map = new HashMap<String, Object>();
        if(CollectionUtil.isNotEmpty(formParamsList)){
            for (FormParam formParam : formParamsList) {
                String fieldName = formParam.getFieldName();
                Object fieldValue = formParam.getFieldValue();
                if(map.containsKey(fieldName)){
                    fieldValue = map.get(fieldName) + StringUtil.STRING_SEPARATOR + fieldValue;
                }
                map.put(fieldName,fieldValue);
            }
        }

        return map;
    }

    /**
     * get all the uploaded files
     * @param fieldName
     * @return
     */
    public List<FileParam> getFileList(String fieldName) {
        return getFileMap().get(fieldName);
    }

    /**
     * get the only one file with specific field name
     * @param fieldName
     * @return
     */
    public FileParam getFile(String fieldName) {
        List<FileParam> fileParamList = getFileList(fieldName);
        if (CollectionUtil.isNotEmpty(fileParamList) && fileParamList.size() == 1) {
            return fileParamList.get(0);
        }
        return null;
    }
    public boolean isEmpty() {
        return CollectionUtil.isEmpty(formParamsList) && CollectionUtil.isEmpty(fileParamList);
    }

    /**
     * 根据参数名获取 String 型参数值
     */
    public String getString(String name) {
        return CastUtil.castString(getFieldMap().get(name));
    }

    /**
     * 根据参数名获取 double 型参数值
     */
    public double getDouble(String name) {
        return CastUtil.castDouble(getFieldMap().get(name));
    }

    /**
     * 根据参数名获取 long 型参数值
     */
    public long getLong(String name) {
        return CastUtil.castLong(getFieldMap().get(name));
    }

    /**
     * 根据参数名获取 int 型参数值
     */
    public int getInt(String name) {
        return CastUtil.castInteger(getFieldMap().get(name));
    }

    /**
     * 根据参数名获取 boolean 型参数值
     */
    public boolean getBoolean(String name) {
        return CastUtil.castBoolean(getFieldMap().get(name));
    }

}
