package com.vma.push.business.dto.req.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Create By ChenXiAoBin
 * on 2018/7/24
 */
@Data
public class ReqUpdateDefault {

    @ApiModelProperty(value = "员工ID")
    private String user_id;

    @ApiModelProperty(value = "收货地址id")
    private String id;

    @ApiModelProperty(value = "是否默认")
    private Integer is_default;
}
