package com.vma.push.business.dto.req.userInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by ChenXiaoBin
 * 2018/5/19 21:02
 */
@Data
public class ReqAddUser {
    @ApiModelProperty(value = "微信的open_id",required = true)
    private String open_id;
    @ApiModelProperty(value = "用户名",required = true)
    private String hx_im_login;
    @ApiModelProperty(value = "密码",required = true)
    private String hx_im_password;
}
