package com.vma.push.business.dto.rsp.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by huxiangqiang on 2018/7/26.
 */
@Data
public class RspDiscount {
    @ApiModelProperty(value = "用户")
    private String id;
    @ApiModelProperty(value = "用户名字")
    private String name;
    @ApiModelProperty(value = "用户名字")
    private String head_icon;
    @ApiModelProperty(value = "职称")
    private String station;
    @ApiModelProperty(value = "部门名称")
    private String department_name;
    @ApiModelProperty(value = "电话")
    private String phone;
    @ApiModelProperty(value = "折扣类型 1个人折扣 0企业折扣")
    private Integer discount_type;
    @ApiModelProperty(value = "折扣")
    private BigDecimal discount;
}
