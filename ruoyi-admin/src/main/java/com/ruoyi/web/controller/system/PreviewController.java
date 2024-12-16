package com.ruoyi.web.controller.system;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.DmsFileInfo;
import com.ruoyi.system.service.IDmsFileInfoService;

/**
 * 获取文件信息详细信息并生成预览链接
 * 
 * @author HYZ
 * @date 2024-11-09
 */
@RestController
@RequestMapping("/system/filepreview")
public class PreviewController {

    @Autowired
    private IDmsFileInfoService dmsFileInfoService;
    
    // 从 application.yml 中读取 KKFileView 服务地址
    @Value("${kkfileview.server-url}")
    private String kkfileViewServerUrl1;
    
    @GetMapping(value = "/{fileId}")
    public AjaxResult getInfo(@PathVariable("fileId") String fileId) {
	    // 获取文件信息
	    DmsFileInfo fileInfo = dmsFileInfoService.selectDmsFileInfoByFileId(fileId);
	    if (fileInfo == null) {
	        return AjaxResult.error("文件不存在");
	    }
	    // 从文件信息中获取文件路径
	    String filePath = fileInfo.getFilePath(); // 假设 filePath 是文件路径字段
	    // 对文件路径进行 URL 编码
	    String encodedFilePath = Base64.getEncoder().encodeToString(filePath.getBytes(StandardCharsets.UTF_8));
	    // 拼接预览链接
	    String previewUrl = kkfileViewServerUrl1 + "/onlinePreview?url=" + encodedFilePath;
	    System.out.println("Generated preview URL: " + previewUrl);  // 在控制台打印链接
	    // 将预览链接添加到返回的文件信息中
	    AjaxResult result = AjaxResult.success(fileInfo);
	    result.put("previewUrl", previewUrl);
	    return result;
	}
}

