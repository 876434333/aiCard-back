package com.vma.push.business.dto.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Create By ChenXiAoBin
 * on 2018/7/17
 */
@Data
public class RspUserByRate {
    @ApiModelProperty(value = "用户名称")
    private String nick_name;

    @ApiModelProperty(value = "预计成交时间")
    private Date clinch_time;

    @ApiModelProperty(value = "用户头像")
    private String head_icon;

    @ApiModelProperty(value = "来自")
    private String from_name;

    @ApiModelProperty(value = "Ai成交率")
    private BigDecimal rate;

    @ApiModelProperty(value = "用户ID")
    private String user_id;

    @ApiModelProperty(value = "对应销售人员ID")
    private String staff_id;

    @ApiModelProperty(value = "销售人员名称")
    private String staff_name;

}
