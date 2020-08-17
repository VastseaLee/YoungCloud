package com.young.oceanisun.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Blog {

    @TableId
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String context;

    /**
     * 是否删除 0未删除 1删除
     */
    private Integer delFlag;

    /**
     * 每页展示条数
     */
    @TableField(exist = false)
    private Integer pageSize;

    /**
     * 页数
     */
    @TableField(exist = false)
    private Integer pageNum;
}
