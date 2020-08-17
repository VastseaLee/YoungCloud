package com.young.oceanisun.service.impl;

import com.young.common.util.SnowFlake;
import com.young.oceanisun.bean.HomeImg;
import com.young.oceanisun.dao.HomeDao;
import com.young.oceanisun.service.HomeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

@Service("homeService")
@Slf4j
public class HomeServiceImpl implements HomeService {

    SnowFlake snowFlake = new SnowFlake(2, 2);

    @Value("${home.img.path}")
    private String imgPath;

    @Value("${home.img.url}")
    private String url;

    @Resource
    private HomeDao homeDao;

    /**
     * 获取主页所有图片的路径
     *
     * @return
     */
    @Override
    public List<HomeImg> listImg() {
        List<HomeImg> list = homeDao.selectList(null);
        list.forEach(homeImg -> homeImg.setUrl(url + homeImg.getUrl()));
        return list;
    }

    /**
     * 替换首页的图片
     *
     * @param id
     * @param file
     */
    @Override
    public void replaceImg(Integer id, MultipartFile file) {
        //首先查出原先的文件名称
        HomeImg lastHomeImg = homeDao.selectById(id);

        //将图片存到本地，并删除原文件
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf('.') + 1);
        String newName = snowFlake.nextId() + "." + suffix;
        try (OutputStream os = new FileOutputStream(imgPath + newName)) {
            //删除原先的文件
            File lastFile = new File(imgPath + lastHomeImg.getUrl());
            lastFile.delete();

            //保存新文件
            os.write(file.getBytes());
        } catch (Exception e) {
            log.error("首页图片保存失败");
        }

        //更新数据库中的图片路径
        HomeImg homeImg = new HomeImg();
        homeImg.setId(id);
        homeImg.setUrl(newName);
        homeDao.updateById(homeImg);
    }
}
