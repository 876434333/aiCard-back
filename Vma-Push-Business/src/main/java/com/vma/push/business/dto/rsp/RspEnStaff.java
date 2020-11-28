package com.vma.push.business.dto.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by huxiangqiang on 2018/5/14.
 */
@Data
public class RspEnStaff {
    @ApiModelProperty(value = "ID")
    private String id;
    @ApiModelProperty(value = "头像")
    private String head_icon;//头像
    @ApiModelProperty(value = "姓名")
    private String name;//姓名
    @ApiModelProperty(value = "岗位")
    private String station;//岗位
    @ApiModelProperty(value = "手机")
    private String phone;//手机
    @ApiModelProperty(value = "部门")
    private String department_name;//部门名字
    @ApiModelProperty(value = "部门ID")
    private String department_id;
    @ApiModelProperty(value = "邮箱")
    private String mail;
    @ApiModelProperty(value = "AI雷达开关")
    private Integer open_ai;
    @ApiModelProperty(value = "boss雷达开关")
    private Integer open_boss;
    @ApiModelProperty(value = "状态")
    private Integer status;
    @ApiModelProperty(value = "微信id")
    private String wx_id;
}
