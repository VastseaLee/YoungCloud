package com.young.oceanisun.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.young.oceanisun.dao.ToolDao;
import com.young.oceanisun.service.ToolService;
import com.young.oceanisun.util.BaiduUtil;
import com.young.oceanisun.util.Base64Util;
import com.young.oceanisun.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.time.LocalDateTime;

@Slf4j
@Service("toolService")
public class ToolServiceImpl implements ToolService {

    @Value("${baidu.ak}")
    private String ak;

    @Value("${baidu.sk}")
    private String sk;

    @Resource
    private ToolDao toolDao;

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
            //对er进制编码进行加解密
            for (int i = 0; i < len; i++) {
                bytes[i] = (byte) ~bytes[i];
            }
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
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
    public String[] ocr(MultipartFile file) {
        //从数据库中获取百度的token
        String token = toolDao.selectBaiduToken();

        //判定token时效
        if (!isValid(token)) {
            //超时则重新获取token并存入数据库中
            token = BaiduUtil.getAuth(ak, sk);
            toolDao.updateBaiduToken(token);
        }

        String[] strings = null;
        try {
            //图片base64编码
            String imgStr = Base64Util.encode(file.getBytes());
            //调用百度ocr接口
            String result = BaiduUtil.accurateBasic(imgStr, token);
            //解析返回结果
            JSONObject jsonObject = JSONObject.parseObject(result);
            int total = jsonObject.getInteger("words_result_num");
            if (total > 0) {
                JSONArray jsonArray = jsonObject.getJSONArray("words_result");
                strings = new String[total];
                for (int i = 0; i < total; i++) {
                    strings[i] = jsonArray.getJSONObject(i).getString("words");
                }
            }
        } catch (IOException e) {
            log.error("图像识别上传的图片有问题", e);
        }

        return strings;
    }

    private boolean isValid(String token) {
        return LocalDateTime.now().toEpochSecond(DateUtil.CST) < getTimestamp(token);
    }

    /**
     * 从百度token中获取时间戳（秒）
     *
     * @param token
     * @return
     */
    private long getTimestamp(String token) {
        char[] chars = token.toCharArray();
        int left = 0;
        boolean rightFlag = true;
        int right = 0;
        for (int i = chars.length - 1; i > -1; i--) {
            if (chars[i] == '.') {
                if (rightFlag) {
                    right = i;
                    rightFlag = false;
                } else {
                    left = i + 1;
                    break;
                }
            }
        }
        return Long.valueOf(token.substring(left, right));
    }
}
