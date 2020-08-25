package com.young.oceanisun.controller;

import com.young.common.bean.WebResult;
import com.young.oceanisun.service.ToolService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("tool")
public class ToolController {

    @Resource
    private ToolService toolService;

    /**
     * 加密/解密文件
     * @param file
     * @param response
     */
    @RequestMapping("enDecode")
    public void enDecode(MultipartFile file, HttpServletResponse response){
        toolService.enDecode(response,file);
    }


    /**
     * 图像ocr识别
     * @param file
     * @return
     */
    @PostMapping("ocr")
    public WebResult ocr(MultipartFile file){
        return WebResult.success(toolService.ocr(file));
    }
}
