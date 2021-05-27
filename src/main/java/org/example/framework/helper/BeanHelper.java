package org.example.framework.helper;

import org.example.framework.util.ClassUtil;
import org.example.framework.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BeanHelper {

    private static Map<Class<?>,Object> beanMap = new HashMap<Class<?>, Object>();

    static {
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for (Class<?> cls : beanClassSet) {
            beanMap.put(cls,ReflectionUtil.newInstance(cls));
        }
    }

    public static Map<Class<?>, Object> getBeanMap() {
        return beanMap;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getInstance(Class<T> cls){
        if(beanMap.containsKey(cls)){
            return (T)beanMap.get(cls);
        }else{
            throw new RuntimeException("can not find bean in the beanMap by the class " + cls);
        }

    }

    public static void setBeanMap(Class<?> cls,Object instance){
        beanMap.put(cls, instance);

    }
}
