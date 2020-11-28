package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by ChenXiaoBin
 * 2018/5/13 0:37
 */
@Data
public class ReqAddAccount {
    @ApiModelProperty(value = "真实姓名",required = true)
    String name;
    @ApiModelProperty(value = "角色",required = true)
    Integer role;
    @ApiModelProperty(value = "电话",required = true)
    String phone;
    @ApiModelProperty(value = "密码",hidden = true)
    String pass_word;
    @ApiModelProperty(value = "代理商")
    String agent;

}
