package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by ChenXiaoBin
 *
 * 2018/5/3 16:56
 */
@Data
public class ReqUpdateAdvNew {@ApiModelProperty(value = "广告id",required = true)
    String id;   //广告id
    @ApiModelProperty(value = "标题",required = true)
    String title;//标题
    @ApiModelProperty(value = "位置",required = true)
    Integer location;//位置
    @ApiModelProperty(value = "链接",required = true)
    String href;//链接
    @ApiModelProperty(value = "广告图片",required = true)
    private String img_url;//链接
    @ApiModelProperty(value = "展示顺序",required = true)
    Integer a_order;//展示顺序
    @ApiModelProperty(value = "状态",required = true)
    Integer status;//状态
    @ApiModelProperty(value = "开始时间  修改广告")
    private Date begin_time ;
    @ApiModelProperty(value = "到期时间  修改广告")
    private Date finish_time ;
}
