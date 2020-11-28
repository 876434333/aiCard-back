package com.vma.push.business.dto.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by ChenXiaoBin
 * 2018/5/12 20:12
 */
@Data
public class RspAccount {
    @ApiModelProperty(value = "ID")
    private String id;//ID
    @ApiModelProperty(value = "真实姓名")
    private String name;//真实姓名
    @ApiModelProperty(value = "电话")
    private String phone;//电话
    @ApiModelProperty(value = "角色")
    private Integer role;//角色
    @ApiModelProperty(value = "创建时间")
    private Date create_time;//创建时间
    @ApiModelProperty(value = "创建人")
    private  String staff_name;//创建人
    @ApiModelProperty(value = "密码")
    private String pass_word;//密码
    @ApiModelProperty(value = "状态")
    private Integer status;//状态

}
