package org.example.framework.bean;

import java.lang.reflect.Member;
import java.lang.reflect.Method;

public class Handler {
    private Class<?> controllerClass;
    private Method controllerMethod;

    public Handler(Class<?> controllerClass, Method controllerMethod) {
        this.controllerClass = controllerClass;
        this.controllerMethod = controllerMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getControllerMethod() {
        return controllerMethod;
    }
}
