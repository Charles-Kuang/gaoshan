package com.gaoshan.modules.wechat.common.controller;

import com.gaoshan.modules.wechat.util.ServerConfig;
import com.gaoshan.util.Result;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.file.FileUploadUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
@Api(tags = "工具类")
@RestController
public class ToolController {
    @Autowired
    private ServerConfig serverConfig;
    /**
     * 通用上传请求
     */
    @ApiOperation("通用上传请求,可用于上传图片等扥")
    @PostMapping("/common/upload")
    public Result uploadFile(@RequestParam("file") MultipartFile file){
        try {
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            File fileBase=new File(filePath);
            if(!fileBase.exists()){
                fileBase.mkdirs();
            }
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() +fileName;
            Map<String,String> data=new HashMap<>();
            data.put("fileName", fileName);
            data.put("url", url);
            return Result.SUCCESS(data);
        } catch (Exception e) {
            return Result.ERROR(e.getMessage(),500);
        }
    }
}
