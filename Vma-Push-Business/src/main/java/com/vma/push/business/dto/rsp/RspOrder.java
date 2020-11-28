package com.vma.push.business.dto.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.DecimalMax;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by ChenXiaoBin
 * 2018/5/2 21:47
 */
@Data
public class RspOrder {
    @ApiModelProperty(value = "ID")
    String id;
    @ApiModelProperty(value = "订单编号")
    String order_nbr;//订单编号
    @ApiModelProperty(value = "产品")
    String offer_name;//产品
    @ApiModelProperty(value = "客户名称")
    String nick_name;//购买人
    @ApiModelProperty(value = "联系电话")
    String link_phone;//联系电话
    @ApiModelProperty(value = "商品数量")
    int offer_num;//商品数量
    @ApiModelProperty(value = "总价")
    BigDecimal total_price;//总价
    @ApiModelProperty(value = "跟进销售")
    String name;//根据推销
    @ApiModelProperty(value = "下单时间")
    Date create_time;//下单时间
    @ApiModelProperty(value = "1支付0未支付")
    Integer status;//状态

    @ApiModelProperty(value = "快递公司")
    String express_name;
    @ApiModelProperty(value = "联系人")
    String link_man;
    @ApiModelProperty(value = "地址")
    String address;


}
