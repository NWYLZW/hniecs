package com.hniecs.mainserver.service;

import com.hniecs.mainserver.entity.FileEntity;
import com.hniecs.mainserver.entity.user.UserEntity;
import com.hniecs.mainserver.exception.CommonExceptions;
import com.hniecs.mainserver.model.FileModel;
import com.hniecs.mainserver.tool.security.SHA256;

import com.hniecs.mainserver.tool.threadtool.ThreadManager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author 陈桢梁
 * @desc fileBaseService.java
 * @date 2020-09-26 14:24
 * @logs[0] 2020-09-26 14:24 陈桢梁 创建了fileBaseService.java文件
 */
@Slf4j
@Service
public class FileBaseService{
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
     * 通过文件路径返回文件二进制数据
     * @param fileName 文件名
     * @param path 文件路径
     * @param suffix 文件后缀
     * @param dateList 文件数据
     */
    public String getPublic(String path, String fileName, String suffix,ArrayList<HttpServletResponse> dateList){
        path += SHA256.salt(fileName,saltCount)+"."+suffix;
        System.out.println(path);
        return get(path, suffix, dateList);
    }
    /**
     *通过文件路径返回文件二进制数据
     * @param path 文件路径
     * @param suffix 文件后缀
     * @param dateList 文件数据
     */
    public String get(String path, String suffix, ArrayList<HttpServletResponse> dateList){
       String msg = fileModel.getByPath(path);
       if(msg.equals("0")) {
           try {
               File file = new File(path);
               byte[] bytes = fileModel.readFile(file);
               dateList.add(fileModel.export(bytes,suffix,dateList.get(0)));
               ThreadManager.getInstance().getTaskList().Check(file);
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

    /**
     *上传单个文件
     * @param path 文件路径包括文件名
     * @param multipartFile 媒体对象
     * @param uploadId 上传者id
     */
    public String add(String path, MultipartFile multipartFile, long uploadId){
        File targetFile = new File(path);
        return fileModel.upload(multipartFile, targetFile, uploadId);
    }

    /**
     * 上传多个文件
     * @param path 文件路径不包括文件名
     * @param multipartFiles 媒体对象组
     * @param getPathList 获取上传后的路径的数组
     * @param uploadId 上传者id
     * @param isEncrypt 是否需要加密
     */
    public String add(String path, MultipartHttpServletRequest multipartFiles,
                      ArrayList<String> getPathList, long uploadId, boolean isEncrypt) {
        Iterator<String> fileNames = multipartFiles.getFileNames();
        while (fileNames.hasNext()) {
            String fileName = fileNames.next();
            List<MultipartFile> multipartFileList = multipartFiles.getFiles(fileName);
            if (multipartFileList.size() > 0) {
                for (MultipartFile multipartFile : multipartFileList) {
                    File file;
                    if(isEncrypt) {
                        String[] fileLocalNames = multipartFile.getOriginalFilename().split("\\.", 2);
                        file = transToFile(path, fileLocalNames[0], fileLocalNames[1]);
                    }else{
                        file = new File(path+"/"+multipartFile.getOriginalFilename());
                    }
                    if (file == null) {
                        throw CommonExceptions.BAD_FILE_ADDRESS_ERROR.exception;
                    }
                    String[] paths = path.split("/",20);
                    getPathList.add("http://hniecs.com/web/static/"+paths[paths.length-2]+"/"+paths[paths.length-1]+"/"
                        +file.getName());
                    String msg = fileModel.upload(multipartFile, file, uploadId);
                    if(!msg.equals("0")){
                        throw CommonExceptions.FILE_UPLOAD_FAIL.exception;
                    }
                }
            }
        }
        return "0";
    }

    /**
     * 通过路径和原名字返回一个文件(将文件名加密后缀不加密)
     * @param path 文件路径
     * @param fileName 文件名除去后缀
     * @param suffix 文件后缀
     */
    private File transToFile(String path, String fileName, String suffix){
         String name = SHA256.salt(fileName, saltCount);
           File dir = new File(path);
           if(!dir.exists()){
            return null;
          }
        return new File(path+"/"+name+"."+suffix);
    }


}
