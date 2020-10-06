package com.hniecs.mainserver.model;

import com.hniecs.mainserver.dao.FileDao;
import com.hniecs.mainserver.entity.FileEntity;
import com.hniecs.mainserver.tool.security.SHA256;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author 陈桢梁
 * @desc fileModel.java
 * @date 2020-09-26 14:02
 * @logs[0] 2020-09-26 14:02 陈桢梁 创建了fileModel.java文件
 */
@Repository
@Slf4j
public class FileModel {
    @Resource
    FileDao fileDao;
    /**
     * 通过id返回图片实体
     * @param id 图片id
     * @param fileList 用于传值的列表
     */
    public String getById(long id, ArrayList<HttpServletResponse> fileList){
        try {
            FileEntity fileEntity = fileDao.getById(id);
            if(fileEntity == null){
                return "图片不存在";
            }
            fileList.add(export(fileEntity.getPath(), "jpg", fileList.get(0)));
            return "0";
        }catch (Exception e){
            log.error(e.getMessage());
            return "服务器出错";
        }
    }

    public String getByPath(String path, String suffix, ArrayList<HttpServletResponse> dateList){
        try {
            FileEntity fileEntity = fileDao.getByPath(path);
            if (fileEntity == null) {
                return "文件不存在";
            }
            dateList.add(export(path, suffix, dateList.get(0)));
            return "0";
        }catch (Exception e){
            log.error(e.getMessage());
            return "服务器出错";
        }
    }
    /**
     * 通过一个文件对象返回一个响应实体
     * @param filePath 文件名
     */
    public HttpServletResponse export(String filePath, String suffix, HttpServletResponse response) throws IOException {
        byte[] bytes = readFile(filePath);
        response.setContentType("image/"+suffix);
        response.getOutputStream().write(bytes);
        response.addHeader("Content-Disposition", "attachment;filename=image."+suffix);
        return response;
    }
    @Cacheable
    public byte[] readFile(String path) throws IOException {
        File file = new File(path);
        log.warn("fuck");
        FileInputStream in = new FileInputStream(file);
        byte[] bytes = new byte[5 * 1024 * 1024];
        in.read(bytes);
        return  bytes;
    }
    /**
     * 更新文件
     * @param file 文件对象
     * @param multipartFile 文件数据流
     * @return
     */
    public String update(File file, MultipartFile multipartFile, long  userId,ArrayList<String> temp) {
        try {
            multipartFile.transferTo(file);
            FileEntity fileEntity = new FileEntity();
            fileEntity.setPath(file.getCanonicalPath());
            fileEntity.setMtime(new Date());
            fileEntity.setId(fileDao.getByUserId(userId).getId());
            fileDao.update(fileEntity);
            temp.add(file.getCanonicalPath());
            return "0";
        }catch (Exception e){
            log.error("fuck");
            log.error(e.getMessage());
            return "服务器出错";
        }
    }

    /**
     *通过id删除图片和数据库条目
     * @param id 文件id
     */
    public String delete(long id) {
        FileEntity mid = fileDao.getById(id);
        if (mid == null) {
            return "图片不存在";
        }
        try {
            if (id != 0) {
                fileDao.delete(id);
                File file = new File(mid.getPath());
                file.delete();
            }
            return "删除成功";
        }catch (Exception e){
            log.error(e.getMessage());
            return "服务器出错";
        }
    }

    public FileEntity getById(long id) {
        try{
            return fileDao.getById(id);
        }catch (Exception e){
            return null;
        }
    }

}
