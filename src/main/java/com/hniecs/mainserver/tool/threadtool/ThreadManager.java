package com.hniecs.mainserver.tool.threadtool;


import lombok.Data;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author 陈桢梁
 * @desc ThreadManager.java
 * @date 2020-10-09 19:48
 * @logs[0] 2020-10-09 19:48 陈桢梁 创建了ThreadManager.java文件
 */
@Data
public class ThreadManager {
    private int nowThreadCount = 0;
    private int threadCount;
    private ScheduledExecutorService scheduledExecutorService;
    private TaskList taskList;
    private ThreadManager(int threadCount){
        this.threadCount = threadCount;
        taskList = TaskList.getInstance();
        scheduledExecutorService = Executors.newScheduledThreadPool(threadCount);
    }
    public boolean addTask(ClearCacheTask clearCacheTask) {
        if (nowThreadCount<=threadCount) {
            nowThreadCount += 1;
            scheduledExecutorService.schedule(clearCacheTask, 10, TimeUnit.MINUTES);
            return true;
        }
        return false;
    }
    public static ThreadManager getInstance(){
        return inner.tm;
    }
    private static class inner{
        private static final ThreadManager tm=new ThreadManager(20);
    }

}
