package com.vma.push.business.dto.req.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by huxiangqiang on 2018/7/30.
 */
@Data
public class ReqOrderPay {
    @ApiModelProperty(value = "联系人")
    private String link_man;
    @ApiModelProperty(value = "联系电话")
    private String link_phone;
    @ApiModelProperty(value = "收货地址")
    private String address;
    @ApiModelProperty(value = "快递费用")
    private BigDecimal express_fee;
    @ApiModelProperty(value = "商品总价")
    private BigDecimal total_price;
    @ApiModelProperty(value = "实付金额")
    private BigDecimal total_price_discount;
    @ApiModelProperty(value = "用户id",hidden = true)
    private String user_id;
    @ApiModelProperty(value = "企业id",hidden = true)
    private String enterprise_id;
    @ApiModelProperty(value = "部门id",hidden = true)
    private String department_id;
    @ApiModelProperty(value = "员工id",hidden = true)
    private String staff_id;
    @ApiModelProperty(value = "订单明细")
    private List<ReqOrder2> order_detail;

}
