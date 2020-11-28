package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by ChenXiaoBin
 * 2018/5/2 16:49
 */
@Data
public class ReqSystemLogin {
    @ApiModelProperty(value = "登录账号",required = true)
    String phone;//账号
    @ApiModelProperty(value = "登录密码",required = true)
    String pass_word;//登录密码
    // String status;
    @ApiModelProperty(value = "验证码",required = false)
    String code;//验证码
    @ApiModelProperty(value = "账号类型（1超级后台2贴牌商3地区总代理4代理商）",notes = "1超级后台2贴牌商3地区总代理4代理商")
    String type;
}
