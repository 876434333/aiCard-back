package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by ChenXiaoBin
 * 2018/5/3 16:08
 */
@Data
public class ReqAddAdv {
    @ApiModelProperty(value = "广告名称",required = true)
    private String title;//广告名称
    @ApiModelProperty(value = "图片位置",required = true)
    private Integer location;//位置
    @ApiModelProperty(value = "图片链接",required = true)
    private String href;//链接
    @ApiModelProperty(value = "广告图片",required = true)
    private String img_url;//链接
    @ApiModelProperty(value = "展示顺序",required = true)
    private Integer a_order;//展示顺序
//    @ApiModelProperty(value = "创建人ID",required = true)
//    private String staff_id;//创建人id
//    @ApiModelProperty(value = "企业ID",required = true)
//    private String enterprise_id; //企业id
}
