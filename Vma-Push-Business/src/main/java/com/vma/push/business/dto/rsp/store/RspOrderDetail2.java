package com.vma.push.business.dto.rsp.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by huxiangqiang on 2018/7/24.
 */
@Data
public class RspOrderDetail2 {

    @ApiModelProperty(value="商品名称")
    private String offer_name;

    @ApiModelProperty(value = "规格图片")
    private String norms_pic;

    @ApiModelProperty(value = "购买数量")
    private Integer offer_num;

    @ApiModelProperty(value = "购买单价")
    private BigDecimal offer_price;

    @ApiModelProperty(value = "购买单价")
    private BigDecimal offer_price_discount;

    @ApiModelProperty(value = "规格名字")
    private String norms_name;
}
