package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by ChenXiaoBin
 * 2018/5/12 20:26
 */
@Data
public class ReqAccountSelect {
    @ApiModelProperty(value = "当前页",required = true)
    int page_num;//当前页
    @ApiModelProperty(value = "每页记录数",required = true)
    int page_size; //每页的记录数
    @ApiModelProperty(value = "真实姓名")
    private String name;//真实姓名
    @ApiModelProperty(value = "电话")
    private String phone;//电话
    @ApiModelProperty(value = "角色")
    private Integer role;//角色
    @ApiModelProperty(value = "状态")
    private Integer status;//状态
    @ApiModelProperty(hidden = true ,value = "adminid")
    private String adminId;
}
