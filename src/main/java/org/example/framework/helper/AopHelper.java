package org.example.framework.helper;

import org.example.framework.annotation.Aspect;
import org.example.framework.annotation.Service;
import org.example.framework.proxy.AspectProxy;
import org.example.framework.proxy.Proxy;
import org.example.framework.proxy.ProxyManager;
import org.example.framework.proxy.TransactionProxy;
import org.example.framework.util.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

public final class AopHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(AopHelper.class);

    static {
        try {
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
            for (Map.Entry<Class<?>, List<Proxy>> targetMapEntry : targetMap.entrySet()) {
                Class<?> targetClass = targetMapEntry.getKey();
                List<Proxy> proxyList = targetMapEntry.getValue();
                Object proxy = ProxyManager.createProxy(targetClass, proxyList);
                LOGGER.debug("new proxy for" +targetClass.getName());
                BeanHelper.setBeanMap(targetClass,proxy);
            }
        } catch (Exception e) {
            LOGGER.error("aop failure", e);
        }

    }

    private static Map<Class<?>, Set<Class<?>>> createProxyMap() throws Exception {
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<Class<?>, Set<Class<?>>>();
        addAspectProxy(proxyMap);
        addTransactionProxy(proxyMap);
        return proxyMap;
    }

    private static void addTransactionProxy(Map<Class<?>, Set<Class<?>>> proxyMap) {
        Set<Class<?>> classSetByAnnotation = ClassHelper.getClassSetByAnnotation(Service.class);
        proxyMap.put(TransactionProxy.class,classSetByAnnotation);
    }

    private static void addAspectProxy(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
        for (Class<?> proxyClass : proxyClassSet) {
            if(proxyClass.isAnnotationPresent(Aspect.class)){
                proxyClassSet = createTargetClassSet(proxyClass.getAnnotation(Aspect.class));
                proxyMap.put(proxyClass,proxyClassSet);
            }
        }
    }

    private static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception {
        Set<Class<?>> targetClassSet = new HashSet<Class<?>>();
        Class<? extends Annotation> annotation = aspect.value();
        if(annotation != null && !annotation.equals(Aspect.class)){
            targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
        }

        return targetClassSet;
    }

    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<Class<?>, List<Proxy>>();
        for (Map.Entry<Class<?>, Set<Class<?>>> proxyMapEntry : proxyMap.entrySet()) {
            Class<?> proxyClass = proxyMapEntry.getKey();
            Set<Class<?>> targetClassSet = proxyMapEntry.getValue();
            for (Class<?> targetClass : targetClassSet) {
                List<Proxy> proxyList;
                Proxy proxyInstance = (Proxy) ReflectionUtil.newInstance(proxyClass);
                if(targetMap.containsKey(targetClass)){
                    proxyList = targetMap.get(targetClass);
                    proxyList.add(proxyInstance);
                }else{
                    proxyList = new ArrayList<Proxy>();
                    proxyList.add(proxyInstance);
                    targetMap.put(targetClass,proxyList);
                }
            }
        }

        return targetMap;
    }
}
