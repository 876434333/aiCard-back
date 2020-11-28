package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by chenzui on 2018/6/20.
 */
@Data
public class ReqModifyPwd {

    @ApiModelProperty(value = "新密码",required = true)
    String new_pwd;
    @ApiModelProperty(value = "旧密码",required = true)
    String old_pwd;

}
