package com.vma.push.business.dto.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by ChenXiaoBin
 * 2018/5/16 20:39
 */
@Data
public class RspAccountOne {
    @ApiModelProperty(value = "ID",required = true)
    String id;
    @ApiModelProperty(value = "姓名",required = true)
    String name;
    @ApiModelProperty(value = "角色",required = true)
    Integer role;
    @ApiModelProperty(value = "电话",required = true)
    String phone;
    @ApiModelProperty(value = "密码",required = true)
    private String pass_word;
    @ApiModelProperty(value = "状态",required = true)
    Integer status;
    @ApiModelProperty(value = "代理商",required = true)
    String agent;

}
