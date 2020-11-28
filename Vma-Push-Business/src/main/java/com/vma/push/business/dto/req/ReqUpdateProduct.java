package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by ChenXiaoBin
 * 2018/4/26 8:38
 */
@Data
public class ReqUpdateProduct {
    @ApiModelProperty(value = "产品ID",required = true)
    String  Id;
    @ApiModelProperty(value = "图片",required = true)
    List<ReqImage> req_images;
    @ApiModelProperty(value = "产品名称",required = true)
    String offer_name;
    @ApiModelProperty(value = "产品价格",required = true)
    BigDecimal offer_price;
    @ApiModelProperty(value = "产品描述",required = true)
    String offer_desc;

}
