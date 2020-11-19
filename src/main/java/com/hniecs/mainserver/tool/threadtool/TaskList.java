package com.hniecs.mainserver.tool.threadtool;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author 陈桢梁
 * @desc TaskList.java
 * @date 2020-10-09 22:00
 * @logs[0] 2020-10-09 22:00 陈桢梁 创建了TaskList.java文件
 */
@Slf4j
public class TaskList {
    @Getter
    private ArrayList<ClearCacheTask> tasks;
    private TaskList(){
        tasks = new ArrayList<>();
    }
    public void setTasks(ClearCacheTask clearCacheTask){
        tasks.add(clearCacheTask);
    }
    public void Check(File file){
        for (ClearCacheTask x:tasks) {
            try {
                if(x.getFile().getCanonicalPath().equals(file.getCanonicalPath())){
                    x.setBeUse(true);
                }
            } catch (IOException e) {
                log.error("文件io异常 TaskList line29");
                e.printStackTrace();
            }
        }
    }
    public static TaskList getInstance(){
        return inner.instance;
    }
    private static class inner{
        private static final TaskList instance = new TaskList();
    }
}
