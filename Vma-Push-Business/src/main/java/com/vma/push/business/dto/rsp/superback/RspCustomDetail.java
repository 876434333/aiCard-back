package com.vma.push.business.dto.rsp.superback;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 * created by linzh on 2018/6/15
 */
@Data
public class RspCustomDetail {
    @ApiModelProperty(value = "客户名称")
    private String name;

    @ApiModelProperty(value = "客户编号")
    private String enterprise_no;

    @ApiModelProperty(value = "客户账号")
    private String login_account;

    @ApiModelProperty(value = "初始迹点")
    private int money_init;

    @ApiModelProperty(value = "累计迹点")
    private int money_sum;

    @ApiModelProperty(value = "剩余迹点")
    private int money_leave;

    @ApiModelProperty(value = "下级地区总代理数")
    private int area_num;

    @ApiModelProperty(value = "下级代理商数")
    private int agent_num;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "创建时间")
    private Date create_time;

    @ApiModelProperty(value = "创建人")
    private String create_by;

    @ApiModelProperty(value = "备注")
    private String remark;
}
