package com.vma.push.business.dto.req.superback;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Create By ChenXiAoBin
 * on 2018/6/21
 */
@Data
public class ReqUpdatePassword {

    @ApiModelProperty(value = "重置密码")
    private String password;
    @ApiModelProperty(value = "id")
    private String id;
}
