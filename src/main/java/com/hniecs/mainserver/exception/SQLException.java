package com.hniecs.mainserver.exception;

import lombok.Data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author 陈桢梁
 * @desc SQLException.java
 * @date 2021-04-01 19:46
 * @logs[0] 2021-04-01 19:46 陈桢梁 创建了SQLException.java文件
 */
@Data
public class SQLException extends RuntimeException{

    private Method method;
    private Class targetClass;
    private Object[] parameter;

    public SQLException(int code,String message,Method method,Class targetClass,Object... parameter){
        super("["+code+"]{"+message+"}");
        this.method = method;
        this.parameter = parameter;
        this.targetClass = targetClass;
    }

    public Object doMethod() throws InvocationTargetException, IllegalAccessException {
        return method.invoke(targetClass,parameter);
    }
}
