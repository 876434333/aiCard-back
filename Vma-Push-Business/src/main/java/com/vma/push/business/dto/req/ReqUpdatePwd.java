package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 找回密码
 * created by linzh on 2018/6/14
 */
@Data
public class ReqUpdatePwd {
    @ApiModelProperty(value = "手机号码",required = true)
    String phone;
    @ApiModelProperty(value = "短信验证码",required = true)
    String msg_code;
    @ApiModelProperty(value = "密码",required = true)
    String pass_word;
}
