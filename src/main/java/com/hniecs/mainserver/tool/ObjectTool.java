package com.hniecs.mainserver.tool;

import com.hniecs.mainserver.entity.ResourceEntity;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * @desc     ObjectTool.java
 * @author  陈桢梁
 * @date    2020-09-20 17:03
 * @logs[0] 2020-09-20 17:03 陈桢梁 创建了ObjectTool.java文件
 */
@Slf4j
public class ObjectTool{
    public static <T> T combineEntity(T sourceEntity, T targetEntity) {
        Field[] sourceFields = sourceEntity.getClass().getDeclaredFields();
        Field[] targetFields = targetEntity.getClass().getDeclaredFields();
        try {
            for (int i = 0; i < sourceFields.length; i++) {
                Field sourceField = sourceFields[i];
                Field targetField = targetFields[i];
                sourceField.setAccessible(true);
                targetField.setAccessible(true);
                Object obj;
                if ((obj=targetField.get(targetEntity)) == null) {
                    continue;
                }
                sourceField.set(sourceEntity,obj);
            }
            return sourceEntity;
        }catch (IllegalAccessException e){
            log.warn(e.getMessage());
            return null;
        }
    }


}
