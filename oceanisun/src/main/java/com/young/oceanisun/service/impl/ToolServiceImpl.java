package com.young.oceanisun.service.impl;

import com.young.oceanisun.service.ToolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

@Slf4j
@Service("toolService")
public class ToolServiceImpl implements ToolService {

    /**
     * 加密/解密文件
     *
     * @param response
     * @param file
     */
    @Override
    public void enDecode(HttpServletResponse response, MultipartFile file) {
        String fileName = file.getOriginalFilename();
        try (OutputStream os = response.getOutputStream()) {
            byte[] bytes = file.getBytes();
            int len = bytes.length;
            for (int i = 0; i < len; i++) {
                bytes[i] = (byte) ~bytes[i];
            }
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName,"UTF-8"));
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            os.write(bytes);
        } catch (IOException e) {
            log.error("[文件加/解密失败]{}", fileName, e);
        }
    }


    /**
     * 图像识别
     *
     * @param file
     * @return
     */
    @Override
    public List<String> ocr(MultipartFile file) {
        return null;
    }
}
