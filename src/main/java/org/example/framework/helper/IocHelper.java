package org.example.framework.helper;

import org.example.framework.annotation.Inject;
import org.example.framework.util.ArrayUtil;
import org.example.framework.util.CollectionUtil;
import org.example.framework.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

public final class IocHelper {

    static {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if(CollectionUtil.isNotEmpty(beanMap)) {
            for (Map.Entry<Class<?>, Object> beanMapEntry : beanMap.entrySet()) {
                Class<?> beanClass = beanMapEntry.getKey();
                Object instance = beanMapEntry.getValue();
                Field[] beanFields = beanClass.getDeclaredFields();
                if (ArrayUtil.isNotEmpty(beanFields)) {
                    for (Field beanField : beanFields) {
                        if (beanField.isAnnotationPresent(Inject.class)) {
                            Class<?> fieldType = beanField.getType();
                            Object beanFieldInstance = beanMap.get(fieldType);
                            if(beanFieldInstance != null) {
                                ReflectionUtil.setFiled(instance, beanField,beanFieldInstance );
                            }
                        }
                    }
                }
            }
        }
    }
}
