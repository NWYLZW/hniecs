package com.hniecs.mainserver.tool.threadtool.我想留着当个纪念;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author 陈桢梁
 * @desc Task.java
 * @date 2020-10-09 17:43
 * @logs[0] 2020-10-09 17:43 陈桢梁 创建了Task.java文件
 */
public class Task implements Runnable{
    Method myMethod;
    Object Class;
    Object[] params;
    public Task(Method method, Object Class, Object[] params){
        this.myMethod = method;
        this.Class = Class;
        this.params = params;
    }

    @Override
    public void run() {
        try {
            myMethod.invoke(Class,params);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
