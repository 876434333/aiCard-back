package com.vma.push.business.dto.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by huxiangqiang on 2018/5/14.
 */
@Data
public class RspStaffOrder {
    @ApiModelProperty(value = "ID")
    String id;

    @ApiModelProperty(value = "订单编号")
    String order_nbr;

    @ApiModelProperty(value = "产品名称")
    String offer_name;

    @ApiModelProperty(value = "总价")
    String total_price;

    @ApiModelProperty(value = "下单时间")
    Date create_time;

    @ApiModelProperty(value = "1支付0未支付")
    int status;//状态

    @ApiModelProperty(value = "商品图片")
    String offer_img;

    @ApiModelProperty(value = "规格名称")
    String norms_name;

    @ApiModelProperty(value = "折扣总价")
    String total_price_discount;

    @ApiModelProperty(value = "商详ID")
    String order_detail_id;
}
