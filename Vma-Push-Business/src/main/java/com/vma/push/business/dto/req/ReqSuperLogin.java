package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by ChenXiaoBin
 * 2018/5/13 15:57
 */
@Data
public class ReqSuperLogin {
    @ApiModelProperty(value = "登录账号",required = true)
    String phone;//账号
    @ApiModelProperty(value = "登录密码",required = true)
    String pass_word;//登录密码
}
