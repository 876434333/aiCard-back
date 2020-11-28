package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Create By ChenXiAoBin
 * on 2018/7/24
 */
@Data
public class ReqAiUpdateTemp {

    @ApiModelProperty(value = "员工ID")
    private String staff_id;

    @ApiModelProperty(value = "模版ID")
    private Integer template_id;
}
