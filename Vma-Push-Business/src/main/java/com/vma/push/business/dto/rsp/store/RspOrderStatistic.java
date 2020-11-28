package com.vma.push.business.dto.rsp.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Create By ChenXiAoBin
 * on 2018/7/26
 */
@Data
public class RspOrderStatistic {

    @ApiModelProperty(value = "头像")
    private String head_icon;

    @ApiModelProperty(value = "员工名字")
    private String name;

    @ApiModelProperty(value = "员工电话")
    private String phone;

    @ApiModelProperty(value = "部门名字")
    private String department_name;

    @ApiModelProperty(value = "成交订单数")
    private Integer order_num;

    @ApiModelProperty(value = "成交金额")
    private BigDecimal total_price;

    @ApiModelProperty(value = "员工ID")
    private String staff_id;
}
