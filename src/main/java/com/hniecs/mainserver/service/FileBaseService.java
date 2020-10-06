package com.hniecs.mainserver.service;

import com.hniecs.mainserver.entity.FileEntity;
import com.hniecs.mainserver.entity.user.UserEntity;
import com.hniecs.mainserver.model.FileModel;
import com.hniecs.mainserver.tool.security.SHA256;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;

/**
 * @author 陈桢梁
 * @desc fileBaseService.java
 * @date 2020-09-26 14:24
 * @logs[0] 2020-09-26 14:24 陈桢梁 创建了fileBaseService.java文件
 */
@Slf4j
@Service
public class FileBaseService {
    @Resource
    FileModel fileModel;

    /**
     * 通过id删除文件
     * @param id
     */
    private boolean deleteFile(long id){
        FileEntity fileEntity = fileModel.getById(id);
        if(fileEntity == null){
            return true;
        }
        File temp = new File(fileEntity.getPath());
        if (temp.exists()){
            temp.delete();
            return true;
        }
        return false;
    }

    /**
     *通过文件路径返回文件二进制数据
     * @param path 文件路径
     */
   public String get(String path, String suffix, ArrayList<HttpServletResponse> dateList){
       return fileModel.getByPath(path, suffix, dateList);
   }

   public String addPrivate(String filepath, String fileName, MultipartFile multipartFile, UserEntity userEntity){
        return  "0";
   }

    /**
     * 更新图片地址
     * @param filepath
     * @param fileName
     * @param multipartFile
     * @param userEntity
     * @param pathList
     */
   public String update(String filepath, String fileName, MultipartFile multipartFile, UserEntity userEntity, ArrayList<String> pathList)  {
       String[] suffix = fileName.split("\\.",2);
       log.error(suffix[0]+" "+suffix[1]);
       String name = SHA256.salt(suffix[0]+userEntity.getId(),2);
       File mkdir = new File(filepath+userEntity.getId()+"/image");
       if(!mkdir.exists()){
           return "文件路径错误";
       }
       //deleteFile(userEntity.getId());
       File file = new File(filepath+"/"+userEntity.getId()+"/image/"+name+"."+suffix[1]);
       return fileModel.update(file, multipartFile, userEntity.getId(), pathList);
   }

}
