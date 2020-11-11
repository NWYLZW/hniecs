package com.hniecs.mainserver.model;

import com.hniecs.mainserver.dao.FileDao;
import com.hniecs.mainserver.entity.FileEntity;
import com.hniecs.mainserver.tool.threadtool.ClearCacheTask;
import com.hniecs.mainserver.tool.threadtool.ThreadManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
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
     * @param id       图片id
     * @param fileList 用于传值的列表
     */
    public String getById(long id, ArrayList<HttpServletResponse> fileList) {
        try {
            FileEntity fileEntity = fileDao.getById(id);
            if (fileEntity == null) {
                return "图片不存在";
            }
            return "未完成";
        } catch (Exception e) {
            log.error(e.getMessage());
            return "服务器出错";
        }
    }

    /**
     * @param path 文件路径
     */
    public String getByPath(String path) {
        try {
            FileEntity fileEntity = fileDao.getByPath(path);
            if (fileEntity == null) {
                return "文件不存在";
            }
            return "0";
        } catch (Exception e) {
            log.error(e.getMessage());
            return "服务器出错";
        }
    }

    /**
     * @param bytes    要写入头部的二进制数组
     * @param suffix   文件名后缀
     * @param response 响应对象
     * @throws IOException io异常
     */
    public HttpServletResponse export(byte[] bytes, String suffix, HttpServletResponse response) throws IOException {
        response.setContentType("image/" + suffix);
        response.getOutputStream().write(bytes);
        response.addHeader("Content-Disposition", "attachment;filename=image." + suffix);
        return response;
    }

    /**
     * 通过路径从一个文件中读数据到byte数组里并缓存
     * @param file 文件对象
     * @return 文件数据
     * @throws IOException io异常
     */
    @Cacheable(value = "myCache", key = "#file.name", condition = "#file.length() <= 5 * 1024 * 1024")
    public byte[] readFile(File file) throws IOException {
        FileInputStream in = new FileInputStream(file);
        byte[] bytes = new byte[(int) file.length()];
        ThreadManager threadManager = ThreadManager.getInstance();
        threadManager.addTask(new ClearCacheTask(file,threadManager.getScheduledExecutorService()));
        in.read(bytes);
        return bytes;
    }

    @CachePut(value = "myCache", key = "#file.name")
    public byte[] updateCache(File file) throws IOException {
        FileInputStream in = new FileInputStream(file);
        byte[] bytes = new byte[(int) file.length()];
        in.read(bytes);
        return bytes;
    }
    @CacheEvict(value = "myCache", key = "#file.name")
    public String clearCache(File file){
        try{
            return "释放成功";
        }catch (Exception e){
            return "释放失败";
        }
    }
    /**
     * 更新文件
     * @param file          文件对象
     * @param multipartFile 文件数据流
     * @return
     */
    public String update(File file, MultipartFile multipartFile, long userId, ArrayList<String> temp) {
        try {
            multipartFile.transferTo(file);
            FileEntity fileEntity = new FileEntity();
            fileEntity.setPath(file.getCanonicalPath());
            fileEntity.setSize(multipartFile.getSize());
            fileEntity.setMtime(new Date());
            fileEntity.setId(fileDao.getByUserId(userId).getId());
            fileDao.update(fileEntity);
            temp.add(file.getCanonicalPath());
            return "0";
        } catch (Exception e) {
            log.error(e.getMessage());
            return "服务器出错";
        }
    }

    public String upload(MultipartFile file, File targetFile, long uploaderId) {
        try {
            String path = targetFile.getCanonicalPath().replace("\\", "/");
            if(!have(path)) {
                FileEntity fileEntity = new FileEntity();
                fileEntity.setPath(path);
                fileEntity.setSize(file.getSize());
                fileEntity.setCtime(new Date());
                fileEntity.setUploaderId(uploaderId);
                file.transferTo(targetFile);
                fileDao.insert(fileEntity);
                return "0";
            }else {
                FileEntity fileEntity = fileDao.getByPath(path);
                fileEntity.setMtime(new Date());
                fileEntity.setSize(file.getSize());
                System.gc();
                if(targetFile.delete()) {
                    file.transferTo(targetFile);
                    fileDao.update(fileEntity);
                    return "0";
                }else {
                    throw new Exception();
                }
            }
        } catch (IOException e) {
            log.error("读取文件路径失败或转换文件失败");
            if (targetFile.exists()) {
                targetFile.delete();
            }
            return "服务器出错";
        } catch (Exception e) {
            log.error(e.getMessage());
            return "服务器出错";
        }
    }

    /**
     * 通过id删除图片和数据库条目
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
        } catch (Exception e) {
            log.error(e.getMessage());
            return "服务器出错";
        }
    }

    public FileEntity getById(long id) {
        try {
            return fileDao.getById(id);
        } catch (Exception e) {
            return null;
        }
    }
    public boolean have(String path){
        return fileDao.getByPath(path) != null;
    }
}
