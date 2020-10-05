package com.hniecs.mainserver.model;

import com.hniecs.mainserver.dao.PhotoDao;
import com.hniecs.mainserver.entity.PhotoEntity;
import com.hniecs.mainserver.tool.security.SHA256;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author 陈桢梁
 * @desc PhotoModel.java
 * @date 2020-09-26 14:02
 * @logs[0] 2020-09-26 14:02 陈桢梁 创建了PhotoModel.java文件
 */
@Repository
@Slf4j
public class PhotoModel {
    @Resource
    PhotoDao photoDao;
    /**
     * 通过id返回图片实体
     * @param id 图片id
     * @param photoList 用于传值的列表
     */
    public String getById(long id, ArrayList<ResponseEntity> photoList){
        try {
            PhotoEntity photoEntity = photoDao.getById(id);
            if(photoEntity == null){
                return "图片不存在";
            }
            File file = new File(photoEntity.getPath());
            photoList.add(export(file));
            return "0";
        }catch (Exception e){
            log.error(e.getMessage());
            return "服务器出错";
        }
    }

    /**
     * 通过一个文件对象返回一个响应实体并缓存
     * @param file 文件对象
     */
    @Cacheable("CachingConfig")
    public ResponseEntity<FileSystemResource> export(File file) {
        if (file == null) {
            return null;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", "attachment; filename=" +file.getName());
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Last-Modified", new Date().toString());
        headers.add("ETag", String.valueOf(System.currentTimeMillis()));

        return ResponseEntity
            .ok()
            .headers(headers)
            .contentLength(file.length())
            .contentType(MediaType.parseMediaType("application/octet-stream"))
            .body(new FileSystemResource(file));
    }

    /**
     * 更新文件
     * @param file 文件对象
     * @param multipartFile 文件数据流
     * @return
     */
    public String update(File file, MultipartFile multipartFile, ArrayList<String> temp) {
        try {
            multipartFile.transferTo(file);
            PhotoEntity photoEntity = new PhotoEntity();
            photoEntity.setPath(file.getAbsolutePath());
            photoEntity.setMtime(new Date());
            photoDao.update(photoEntity);
            temp.add(file.getCanonicalPath());
            return "0";
        }catch (Exception e){
            log.error(e.getMessage());
            return "服务器出错";
        }
    }

    /**
     *通过id删除图片和数据库条目
     * @param id 文件id
     */
    public String delete(long id) {
        PhotoEntity mid = photoDao.getById(id);
        if (mid == null) {
            return "图片不存在";
        }
        try {
            if (id != 0) {
                photoDao.delete(id);
                File file = new File(mid.getPath());
                file.delete();
            }
            return "删除成功";
        }catch (Exception e){
            log.error(e.getMessage());
            return "服务器出错";
        }
    }

    public PhotoEntity getById(long id) {
        try{
            return photoDao.getById(id);
        }catch (Exception e){
            return null;
        }
    }
}
