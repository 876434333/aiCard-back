package com.vma.push.business.dto.req.enterprise;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by chenzui on 2018/5/11.
 */
@Data
public class ReqModifyPhone {

    @ApiModelProperty(value = "新手机号码")
    String phone;

    @ApiModelProperty(value = "新手机号接收到的验证码")
    String code;

//    @ApiModelProperty(value = "新密码")
//    String password;
}
