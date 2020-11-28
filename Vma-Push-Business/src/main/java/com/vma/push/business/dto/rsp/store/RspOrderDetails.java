package com.vma.push.business.dto.rsp.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by huxiangqiang on 2018/7/24.
 */
@Data
public class RspOrderDetails {
    @ApiModelProperty(value = "联系人")
    private String link_man;
    @ApiModelProperty(value = "电话号码")
    private String link_phone;
    @ApiModelProperty(value = "订单金额")
    private BigDecimal total_price;
    @ApiModelProperty(value = "折扣后的总金额")
    private BigDecimal total_price_discount;
    @ApiModelProperty(value = "快递费")
    private BigDecimal express_fee;
    @ApiModelProperty(value = "支付状态 1支付 0为支付")
    private Integer status;
    @ApiModelProperty(value = "下单时间")
    private Date create_time;
    @ApiModelProperty(value="订单编号")
    private String order_nbr;
    @ApiModelProperty(value = "收货地址")
    private String address;
    @ApiModelProperty(value = "订单明细")
    private List<RspOrderInfo> order_list;
}
