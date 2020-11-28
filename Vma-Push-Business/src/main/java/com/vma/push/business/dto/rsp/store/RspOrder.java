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
public class RspOrder {
    @ApiModelProperty(value = "0未支付 1已支付 2用户取消 3超时取消 4未发货 5已发货 6完成")
    private String status;


    @ApiModelProperty(value = "下单时间")
    private Date create_time;

    @ApiModelProperty(value = "订单id")
    private String id;

    @ApiModelProperty(value = "付款金额")
    private BigDecimal total_price_discount;

    @ApiModelProperty(value = "原价")
    private BigDecimal total_price;

    private List<RspOrderDetail2> orderDetails;
}
