package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by ChenXiaoBin
 * 2018/5/4 8:35
 */
@Data
public class ReqStaffPassword {
//    @ApiModelProperty(value = "职员ID",required = true)
//    String id;//staff 职员id
    @ApiModelProperty(value = "原密码",required = true)
    String pass_word;//原密码
    @ApiModelProperty(value = "新密码",required = true)
    String new_password;//新密码
}
