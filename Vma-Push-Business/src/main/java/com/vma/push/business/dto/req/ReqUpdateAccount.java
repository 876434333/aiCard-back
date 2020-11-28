package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by ChenXiaoBin
 * 2018/5/14 7:48
 */
@Data
public class ReqUpdateAccount {
    @ApiModelProperty(value = "ID",required = true)
    String id;
    @ApiModelProperty(value = "姓名",required = true)
    String name;
    @ApiModelProperty(value = "角色",required = true)
    Integer role;
    @ApiModelProperty(value = "电话",required = true)
    String phone;
    @ApiModelProperty(value = "密码",required = true)
    String pass_word;
    @ApiModelProperty(value = "状态",required = true)
    Integer status;

}
