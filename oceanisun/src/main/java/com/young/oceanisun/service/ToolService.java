package com.young.oceanisun.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ToolService {

    /**
     * 加密/解密文件
     * @param response
     * @param file
     */
    void enDecode(HttpServletResponse response, MultipartFile file);

    /**
     * 图像识别
     * @param file
     * @return
     */
    List<String> ocr(MultipartFile file);
}
