package com.vma.push.business.dto.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by huxiangqiang on 2018/5/14.
 */
@Data
public class RspOrderDetail {
    @ApiModelProperty(value = "ID")
    String id;

    @ApiModelProperty(value = "订单编号")
    String order_nbr;

    @ApiModelProperty(value = "产品名称")
    String offer_name;

    @ApiModelProperty(value = "产品id")
    String offer_id;

    @ApiModelProperty(value = "单价")
    String offer_price;

    @ApiModelProperty(value = "数量")
    String offer_num;

    @ApiModelProperty(value = "总价")
    String total_price;

    @ApiModelProperty(value = "联系电话")
    String link_phone;

    @ApiModelProperty(value = "联系人")
    String link_man;

    @ApiModelProperty(value = "下单时间")
    Date create_time;

    @ApiModelProperty(value = "1支付0未支付")
    int status;//状态

    @ApiModelProperty(value = "商品图片")
    String offer_img;

    @ApiModelProperty(value = "销售数量")
    private Integer sale_num ;

    @ApiModelProperty(value = "浏览次数")
    private Integer view_num ;



}
