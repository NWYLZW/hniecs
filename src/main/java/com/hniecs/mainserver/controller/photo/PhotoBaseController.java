package com.hniecs.mainserver.controller.photo;

import com.hniecs.mainserver.annotation.method.NotNeedLogin;
import com.hniecs.mainserver.entity.user.UserEntity;
import com.hniecs.mainserver.service.PhotoBaseService;
import com.hniecs.mainserver.tool.api.CommonResult;
import com.hniecs.mainserver.tool.security.SessionTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;

/**
 * @author 陈桢梁
 * @desc PhotoBaseController.java
 * @date 2020-09-25 22:07
 * @logs[0] 2020-09-25 22:07 陈桢梁 创建了PhotoBaseController.java文件
 */
@Slf4j
@RestController
public class PhotoBaseController {
    @Resource
    PhotoBaseService photoService;

    /**
     * 验证文件后缀
     * @param fileName 文件名
     */
    private String verifyName(String fileName){
        String[] suffix = fileName.split(".",2);
        String bigSuffix= suffix[1].toUpperCase();
        if(bigSuffix.equals("JPG") || bigSuffix.equals("PNG") || bigSuffix.equals("GIF") || bigSuffix.equals("JPEG")){
            return bigSuffix;
        }
        return null;
    }
    /**
     * 验证文件路径是否正确
     * @param path 文件路径
     * @return
     */
    private boolean verify(String path){
        String[] stringArr = path.split("/",5);
        if(!new File(path).isDirectory()){
            return false;
        }
        if(!stringArr[1].equals("public") && !stringArr[1].equals("private")){
            return false;
        }
        return true;
    }
    /**
     * 通过id获取图片
     * @param id 图片id
     * @return
     */
    @NotNeedLogin
    @PostMapping("/photo/base/get")
    public Object get(@RequestParam long id){
        ArrayList<ResponseEntity> photoDateList = new ArrayList<>();
        String msg = photoService.get(id,photoDateList);
        if(msg.equals("0")){
            return photoDateList.get(0);
        }
        return CommonResult.failed(msg);
    }

    /**
     * 上传文件进public文件夹目录
     * @param path 文件对应路径不包括文件名
     * @param multipartFile 文件流数据
     */
    @PostMapping("/photo/base/Update")
    public CommonResult add(@RequestParam String path, @RequestParam MultipartFile multipartFile){
       path=System.getProperty("user.dir") + "/./workPath" + path + "/";
        String name = multipartFile.getName();
        ArrayList<String> pathList = new ArrayList<>();
        if(multipartFile.getSize() >= 5*1024*1024){
            return CommonResult.failed("图片过大");
        }
        if(!verify(path)){
            return CommonResult.failed("路径错误");
        }
        if(verifyName(multipartFile.getName()) == null){
            return CommonResult.failed("文件类型有误");
        }
        UserEntity userEntity = SessionTool.curUser();
        String msg = photoService.update(path, name, multipartFile, userEntity, pathList);
        if(msg.equals("0")){
            return CommonResult.success(pathList,"文件上传成功");
        }
        return CommonResult.failed(msg);
    }
}
