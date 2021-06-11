package com.young.oceanisun.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class Blog {

    @TableId(type = IdType.AUTO)
//    @NotNull(message = "ID empty",groups = {Update.class})
    @Min(0)
    private Integer id;

    /**
     * 标题
     */
    @NotEmpty(message = "Title empty")
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
     * 创建时间
     */
    private String createTime;

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
