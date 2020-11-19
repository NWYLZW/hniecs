package com.hniecs.mainserver.tool.threadtool;
import com.hniecs.mainserver.model.FileModel;
import lombok.Data;

import javax.annotation.Resource;
import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author 陈桢梁
 * @desc ClearCacheTask.java
 * @date 2020-10-09 19:47
 * @logs[0] 2020-10-09 19:47 陈桢梁 创建了ClearCacheTask.java文件
 */
@Data
public class ClearCacheTask implements Runnable{
    private File file;
    private boolean isBeUse;
    private long time = 10;
    private int countTime = 0;
    private ScheduledExecutorService scheduledExecutorService;
    @Resource
    FileModel fileModel;
    @Override
    public void run() {
        if (isBeUse&&countTime<=60) {
            isBeUse = false;
            countTime +=10;
            scheduledExecutorService.schedule(this,time,TimeUnit.MINUTES);
        }else{
            fileModel.clearCache(file);
        }
    }
    public ClearCacheTask(File file,ScheduledExecutorService scheduledExecutorService){
        this.scheduledExecutorService = scheduledExecutorService;
        this.file = file;
        isBeUse = true;
    }

}
