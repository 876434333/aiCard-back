package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by ChenXiaoBin
 * 2018/5/15 21:09
 */
@Data
public class ReqImage {
    @ApiModelProperty(value = "详情图或封面图",required = true)
    int type;

    @ApiModelProperty(value = "图片路径",required = true)
    String img_url;

    @ApiModelProperty(value = "排序值",required = true)
    Integer order;
}
