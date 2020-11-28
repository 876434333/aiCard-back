package com.vma.push.business.dto.req.userInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by chenzui on 2018/5/2.
 */
@Data
public class RspLogin {

    @ApiModelProperty(value = "微信开放平台ID")
    String open_id;
    @ApiModelProperty(value = "头像href地址")
    String icon;
    @ApiModelProperty(value = "昵称")
    String nick_name;
    @ApiModelProperty(value = "性别1男0女")
    String sex;
}
