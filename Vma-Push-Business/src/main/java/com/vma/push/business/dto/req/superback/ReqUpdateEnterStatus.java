package com.vma.push.business.dto.req.superback;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Create By ChenXiAoBin
 * on 2018/6/21
 */
@Data
public class ReqUpdateEnterStatus {
    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "状态")
    private Integer status;
}
