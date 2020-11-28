package com.vma.push.business.dto.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by huxiangqiang on 2018/5/17.
 */
@Data
public class RspUserOrder {
    @ApiModelProperty(value = "ID")
    String id;

    @ApiModelProperty(value = "订单编号")
    String order_nbr;

    @ApiModelProperty(value = "产品Id")
    String offer_id;

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

    @ApiModelProperty(value = "销售数量")
    private Integer sale_num ;

    @ApiModelProperty(value = "浏览次数")
    private Integer view_num ;
}
