package org.example.framework.util;

import javafx.beans.binding.ObjectExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class ReflectionUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * create instance for the class
     * @param cls
     * @return
     */
    public static Object newInstance(Class<?> cls){
        Object instance;
        try {
            instance = cls.newInstance();
        } catch (Exception e) {
            LOGGER.error("create instance failure",e);
            throw new RuntimeException(e);
        }
        return instance;
    }

    /**
     * create instance by class name
     * @param className
     * @return
     */
    public static Object newInstanceByName(String className){
        Class<?> cls = ClassUtil.loadClass(className);
        return newInstance(cls);
    }

    /**
     * invoke method
     * @param obj
     * @param method
     * @param args
     */
    public static Object invokeMethod(Object obj, Method method, Object... args){
        Object result;
        try {
            method.setAccessible(true);
            result = method.invoke(obj,args);
        } catch (Exception e) {
            LOGGER.error("invoke method failure",e);
            throw new RuntimeException(e);
        }
        return  result;
    }

    /**
     * set filed value
     * @param obj
     * @param filed
     * @param value
     */
    public static void setFiled(Object obj, Field filed,Object value){
        filed.setAccessible(true);
        try {
            filed.set(obj, value);
        } catch (IllegalAccessException e) {
            LOGGER.error("set filed value failure",e);
            throw new RuntimeException(e);
        }
    }
}
