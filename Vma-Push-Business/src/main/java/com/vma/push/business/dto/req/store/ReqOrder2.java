package com.vma.push.business.dto.req.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by huxiangqiang on 2018/7/30.
 */
@Data
public class ReqOrder2 {
    @ApiModelProperty(value = "商品id")
    private String offer_id;
    @ApiModelProperty(value = "规格id")
    private String norm_id;
    @ApiModelProperty(value = "数量")
    private Integer num;
    @ApiModelProperty(value = "原单价")
    private BigDecimal offer_price;
    @ApiModelProperty(value = "折扣单价")
    private BigDecimal offer_price_discount;

}
