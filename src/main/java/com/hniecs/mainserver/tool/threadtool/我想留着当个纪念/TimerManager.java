package com.hniecs.mainserver.tool.threadtool.我想留着当个纪念;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author 陈桢梁
 * @desc TimerManager.java
 * @date 2020-10-09 17:43
 * @logs[0] 2020-10-09 17:43 陈桢梁 创建了TimerManager.java文件
 */
public class TimerManager {
    ScheduledExecutorService scheduledExecutorService;
    public TimerManager(int threadNumber){
        scheduledExecutorService = Executors.newScheduledThreadPool(threadNumber);
    }

    /**
     * 向调度器里添加任务
     * @param time 时间以分钟为单位
     * @param task 任务对象
     * @return
     */
    public boolean addTask(long time, Task task) {
        try {
            scheduledExecutorService.schedule(task, time, TimeUnit.SECONDS);
            return true;
        }catch (Exception e){
            return false;
        }
    }


}
