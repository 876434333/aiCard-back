package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by ChenXiaoBin
 * 2018/4/25 14:36
 */
@Data
public class ReqSpecAndImg {
@ApiModelProperty(value = "图片",required = true)
    List<ReqImage> req_images;
    @ApiModelProperty(value = "产品名称")
    String offer_name;
    @ApiModelProperty(value = "员工id",required = true)
    String staff_id;
    @ApiModelProperty(value = "产品价格",required = true)
    BigDecimal offer_price;
    @ApiModelProperty(value = "产品描述",required = true)
    String offer_desc;

}
