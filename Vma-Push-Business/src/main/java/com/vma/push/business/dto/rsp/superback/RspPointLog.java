package com.vma.push.business.dto.rsp.superback;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * created by linzh on 2018/6/15
 */
@Data
public class RspPointLog {
    @ApiModelProperty(value = "客户id")
    private String custom_id;

    @ApiModelProperty(value = "客户名称")
    private String custom_name;

    @ApiModelProperty(value = "客户编号")
    private String custom_code;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "事项")
    private String content;

    @ApiModelProperty(value = "操作")
    private String operation;

    @ApiModelProperty(value = "操作迹点数")
    private float operation_point;

    @ApiModelProperty(value = "剩余迹点")
    private float remain_point;

    @ApiModelProperty(value = "时间")
    private Date create_time;
}
