package com.vma.push.business.dto.rsp.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by huxiangqiang on 2018/7/24.
 */
@Data
public class RspUserCartList {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "商品id")
    private String offer_id;

    @ApiModelProperty(value = "商品名称")
    private String offer_name;

    @ApiModelProperty(value = "规格id")
    private String norms_id;

    @ApiModelProperty(value = "规格名称")
    private String norms_name;

    @ApiModelProperty(value = "规格图片")
    private String norms_pic;

    @ApiModelProperty(value = "购买数量")
    private Integer num;

    @ApiModelProperty(value = "库存数量")
    private Integer offer_leave;

    @ApiModelProperty(value = "原单价")
    private BigDecimal offer_price;

    @ApiModelProperty(value = "快递费")
    BigDecimal express_fee;


}
