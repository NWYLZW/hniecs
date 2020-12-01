package com.hniecs.mainserver.controller.file;

import com.hniecs.mainserver.entity.FileEntity;
import com.hniecs.mainserver.entity.user.UserEntity;
import com.hniecs.mainserver.exception.CommonExceptions;
import com.hniecs.mainserver.service.FileBaseService;
import com.hniecs.mainserver.tool.api.CommonResult;
import com.hniecs.mainserver.tool.enums.DirTypeEnum;
import com.hniecs.mainserver.tool.security.SHA256;
import com.hniecs.mainserver.tool.security.SessionTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.hniecs.mainserver.tool.enums.SuffixEnum;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author 陈桢梁
 * @desc FileBaseController.java
 * @date 2020-09-25 22:07
 * @logs[0] 2020-09-25 22:07 陈桢梁 创建了FileBaseController.java文件
 */
@Slf4j
@Controller
public class FileBaseController {
    @Resource
    FileBaseService fileService;

    /**
     * 验证文件后缀
     * @param suffixName 文件后缀名
     */
    private Boolean verifySuffix(String suffixName) {
        return verify(SuffixEnum.class, suffixName);
    }

    /**
     * 验证文件夹名
     * @param dirTypeName 文件夹名
     */
    private Boolean verifyDirType(String dirTypeName) {
        return verify(DirTypeEnum.class, dirTypeName);
    }

    /**
     * 通过反射验证名字是否包含在对应的枚举里
     * @param verifyClass 要验证的类
     * @param verifyName  要验证的名字
     */
    private boolean verify(Class verifyClass, String verifyName) {
        Field[] fields = verifyClass.getFields();
        for (Field x : fields) {
            if (x.getName().equals(verifyName)) {
                return true;
            }
        }
        return false;
    }

    @PostMapping("/static/public/{dirType}/{fileName}.{suffix}")
    public Object get(@PathVariable String dirType, @PathVariable String fileName
        , @PathVariable String suffix, HttpServletResponse response) {
        ArrayList<HttpServletResponse> fileDateList = new ArrayList<>();
        fileDateList.add(response);
        if (!verifySuffix(suffix) || !verifyDirType(dirType)) {
            return CommonResult.failed("url有误");
        }
        String basePath = System.getProperty("user.dir").replace("\\", "/") + "/workPlace/public" + "/"
            + dirType + "/" + fileName + "." + suffix;
        String msg = fileService.get(basePath, suffix, fileDateList);
        if (msg.equals("0")) {
            return fileDateList.get(0);
        }
        throw  CommonExceptions.INTERNAL_SERVER_ERROR.exception;
    }

    /**
     * 获取公共文件夹下某个分类的全部文件
     * @param dirType
     * @return
     */
    @GetMapping("file/base/get/{dirType}")
    public CommonResult getAll(@PathVariable String dirType){
        if(!verifyDirType(dirType)){
            throw CommonExceptions.BAD_FILE_ADDRESS_ERROR.exception;
        }
        ArrayList<FileEntity> fileEntities = new ArrayList<>();
        String msg = fileService.getByType(dirType,fileEntities);
        if(msg.equals("0")){
            return CommonResult.success(fileEntities, "获取成功");
        }
        throw CommonExceptions.INTERNAL_SERVER_ERROR.exception;
    }
    /**
     * 上传文件进public文件夹目录
     * @param path          文件对应路径不包括文件名
     * @param multipartFile 文件流数据
     */
    @ResponseBody
    @PostMapping("/file/base/update")
    public CommonResult update(@RequestParam("filePath") String path, @RequestParam MultipartFile multipartFile) {
        path = System.getProperty("user.dir").replace("\\", "/") + "/./workPlace/" + path;
        String name = multipartFile.getOriginalFilename();
        String suffix = name.split(".", 2)[1];
        ArrayList<String> pathList = new ArrayList<>();
        if (multipartFile.getSize() >= 5 * 1024 * 1024) {
            throw CommonExceptions.FILE_BIG.exception;
        }
        if (verifySuffix(suffix)) {
            throw CommonExceptions.BAD_FILE_TYPE.exception;
        }
        UserEntity userEntity = SessionTool.curUser();
        String msg = fileService.update(path, name, multipartFile, userEntity, pathList);
        if (msg.equals("0")) {
            return CommonResult.success(pathList, "文件上传成功");
        }
        return CommonResult.failed(msg);
    }

    /**
     * 上传头像
     */
    @ResponseBody
    @PostMapping("/file/base/upload/images")
    public CommonResult uploadImages(@RequestBody MultipartFile multipartFile){
        String[] fileNames = multipartFile.getOriginalFilename().split("\\.",2);
        String basePath = System.getProperty("user.dir").replace("\\", "/")
            +"/workPlace/public/image"+ SHA256.salt(fileNames[0],2)+"."+fileNames[1];
        if(!verifySuffix(fileNames[1])){
            throw CommonExceptions.BAD_FILE_TYPE.exception;
        }
        String msg = fileService.add(basePath,multipartFile,SessionTool.curUser().getId());
        if(msg.equals("0")){
            return CommonResult.success(basePath,"上传成功");
        }
        throw CommonExceptions.FILE_ERROR.exception;
    }
    /**
     * @param path 需要删除的图片路径
     */
    @PostMapping()
    public CommonResult delete(@RequestParam String path) {
        return CommonResult.failed("未完成");
    }
}
