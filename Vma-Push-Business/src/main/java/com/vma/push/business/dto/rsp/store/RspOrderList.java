package com.vma.push.business.dto.rsp.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by huxiangqiang on 2018/7/26.
 */
@Data
public class RspOrderList {
    @ApiModelProperty(value = "订单id")
    private String id;
    @ApiModelProperty(value = "订单编号")
    private String order_nbr;
    @ApiModelProperty(value = "下单时间")
    private Date create_time;
    @ApiModelProperty(value = "总价")
    private BigDecimal total_price;
    @ApiModelProperty(value = "应付金额")
    private BigDecimal total_price_discount;
    @ApiModelProperty(value = "优惠金额")
    private BigDecimal price_discount;
    @ApiModelProperty(value = "业务员")
    private String staff_name;
    @ApiModelProperty(value = "佣金")
    private BigDecimal extract_value;
    @ApiModelProperty(value = "配送费")
    private BigDecimal express_fee;
    @ApiModelProperty(value = "收货人")
    private String nick_name;
    @ApiModelProperty(value = "收货人电话")
    private String link_phone;
    @ApiModelProperty(value = "0未支付 1已支付 2用户取消 3超时取消 4未发货 5已发货 6完成")
    private Integer status;
    @ApiModelProperty(value = "详细详情")
    List<RspOrderListDetail> details;


    @ApiModelProperty(value = "快递公司")
    String express_name;
    @ApiModelProperty(value = "联系人")
    String link_man;
    @ApiModelProperty(value = "地址")
    String address;

}
