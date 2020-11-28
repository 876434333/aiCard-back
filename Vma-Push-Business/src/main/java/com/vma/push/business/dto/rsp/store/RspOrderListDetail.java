package com.vma.push.business.dto.rsp.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by huxiangqiang on 2018/7/26.
 */
@Data
public class RspOrderListDetail {
    @ApiModelProperty(value = "规格图片")
    private String norms_pic;
    @ApiModelProperty(value = "商品名称")
    private String offer_name;
    @ApiModelProperty(value = "购买数量")
    private Integer num;
    @ApiModelProperty(value = "单价")
    private BigDecimal offer_price;
}
