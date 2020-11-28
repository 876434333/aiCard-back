package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by JINzm
 * 2018/8/6
 */
@Data
public class ReqAddAdvNew {
    @ApiModelProperty(value = "广告名称",required = true)
    private String title;//广告名称
    @ApiModelProperty(value = "图片位置",required = true)
    private Integer location;//位置
    @ApiModelProperty(value = "上线/下线",required = true)
    private Integer status;//位置
    @ApiModelProperty(value = "图片链接",required = true)
    private String href;//链接
    @ApiModelProperty(value = "广告图片",required = true)
    private String img_url;//链接
    @ApiModelProperty(value = "展示顺序",required = true)
    private Integer a_order;//展示顺序
    @ApiModelProperty(value = "开始时间  新增广告")
    private Date begin_time ;
    @ApiModelProperty(value = "到期时间  新增广告")
    private Date finish_time ;
}
