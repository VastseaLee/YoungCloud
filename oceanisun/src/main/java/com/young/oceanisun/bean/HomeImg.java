package com.young.oceanisun.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class HomeImg {

    /**
     * 主键
     */
    @TableId
    private Integer id;

    /**
     * 路径
     */
    private String url;
}
