package com.vma.push.business.dto.req.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Create By ChenXiAoBin
 * on 2018/7/24
 */
@Data
public class ReqDelAddress {
    @ApiModelProperty(value = "收货地址ID")
    private String id;

    @ApiModelProperty(value = "是否删除（0是删除，1是正常）")
    private Integer status;

}
