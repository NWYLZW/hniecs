package com.hniecs.mainserver.service;

import com.hniecs.mainserver.entity.FileEntity;
import com.hniecs.mainserver.entity.user.UserEntity;
import com.hniecs.mainserver.model.FileModel;
import com.hniecs.mainserver.tool.security.SHA256;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
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
    final int saltCount = 2;
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
       String msg = fileModel.getByPath(path);
       if(msg.equals("0")) {
           try {
               byte[] bytes = fileModel.readFile(new File(path));
               dateList.add(fileModel.export(bytes,suffix,dateList.get(0)));
           }catch (IOException e){
               return "服务器出错";
           }
       }
       return msg;
   }

    /**
     * 更新图片地址
     * @param filepath 文件路径
     * @param fileName 文件名
     * @param multipartFile 上传的文件
     * @param userEntity 用户实体
     * @param pathList 获取路径数组
     */
   public String update(String filepath, String fileName, MultipartFile multipartFile, UserEntity userEntity, ArrayList<String> pathList)  {
       String[] suffix = fileName.split("\\.",2);
       File file = transToFile(filepath+"/"+userEntity.getId()+"/image",suffix[0],suffix[1]);
       if(file == null){
           return "文件路径错误";
       }
       return fileModel.update(file, multipartFile, userEntity.getId(), pathList);
   }

    public String addPrivate(String fileName, MultipartFile multipartFile
        , ArrayList<String> getPathList){
        return  "0";
    }
    /**
     * 上传文件到公共目录
     * @param suffix 文件后缀
     * @param path 文件路径
     * @param fileName 文件名
     * @param multipartFile 文件数据
     * @param getPathList 获取保存后数据数组
     * @param uploaderId 用户id
     */
    public String addPublic(String suffix, String path, String fileName, MultipartFile multipartFile
        , ArrayList<String> getPathList, long uploaderId) {
       File file = transToFile(path, fileName, suffix);
       if(file == null){
           return "文件路径错误";
       }
       try {
           getPathList.add(file.getCanonicalPath());
           if(file.exists()){
               fileModel.updateCache(file);
           }
       }catch (IOException e){
           log.error(e.getMessage());
           return "服务器出错";
       }
        return fileModel.upload(multipartFile, file, uploaderId);
    }


    /**
     * 通过路径和原名字返回一个文件
     * @param path 文件路径
     * @param fileName 文件名
     * @param suffix 文件后缀
     */
   private File transToFile(String path, String fileName, String suffix){
       String name = SHA256.salt(fileName, saltCount);
       File dir = new File(path);
       System.out.println(path);
       if(!dir.exists()){
           return null;
       }
       return new File(path+"/"+name+"."+suffix);
   }
}
