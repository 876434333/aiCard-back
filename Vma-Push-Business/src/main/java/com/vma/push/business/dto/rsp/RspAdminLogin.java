package com.vma.push.business.dto.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by JINzm on 2018/7/6.
 */
@Data
public class RspAdminLogin {
    @ApiModelProperty(value = "用户id")
    private String id;
    @ApiModelProperty(value = "企业id")
    private String customId;
}
