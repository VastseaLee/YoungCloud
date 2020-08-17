package com.young.oceanisun.service;

import com.young.oceanisun.bean.HomeImg;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HomeService {

    /**
     * 获取主页所有图片的路径
     *
     * @return
     */
    List<HomeImg> listImg();

    /**
     * 替换首页的图片
     * @param id
     * @param file
     */
    void replaceImg(Integer id, MultipartFile file);
}
