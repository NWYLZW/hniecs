package com.hniecs.mainserver.service;

import com.hniecs.mainserver.entity.PhotoEntity;
import com.hniecs.mainserver.entity.user.UserEntity;
import com.hniecs.mainserver.model.PhotoModel;
import com.hniecs.mainserver.tool.security.SHA256;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;

/**
 * @author 陈桢梁
 * @desc PhotoBaseService.java
 * @date 2020-09-26 14:24
 * @logs[0] 2020-09-26 14:24 陈桢梁 创建了PhotoBaseService.java文件
 */
@Slf4j
@Service
public class PhotoBaseService {
    @Resource
    PhotoModel photoModel;

    /**
     * 通过id删除文件
     * @param id
     */
    private boolean deleteFile(long id){
        PhotoEntity photoEntity = photoModel.getById(id);
        File temp = new File(photoEntity.getPath());
        if (temp.exists()){
            temp.delete();
            return true;
        }
        return false;
    }
    /**
     * 通过id返回图片二进制集合
     * @param id 图片id
     * @param photoDateList 获取二进制数组的集合
     */
   public String get(long id, ArrayList<ResponseEntity> photoDateList){
        return photoModel.getById(id,photoDateList);
   }

   public String addPrivate(String filepath, String fileName, MultipartFile multipartFile, UserEntity userEntity){
        return  "0";
   }
   public String update(String filepath, String fileName, MultipartFile multipartFile, UserEntity userEntity, ArrayList<String> pathList)  {
       String[] suffix = fileName.split(".",2);
       String name = SHA256.salt(suffix[0]+userEntity.getId(),2);
       File mkdir = new File(filepath);
       if(!mkdir.exists()){
           return "文件路径错误";
       }
       deleteFile(userEntity.getId());
       File file = new File(filepath+"/"+userEntity.getUserName()+"/"+name+"."+suffix[1]);
       return photoModel.update(file, multipartFile, pathList);
   }

}
