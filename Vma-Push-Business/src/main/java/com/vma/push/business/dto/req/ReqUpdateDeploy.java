package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Create By ChenXiAoBin
 * on 2018/7/12
 */
@Data
public class ReqUpdateDeploy {
    @ApiModelProperty(value = "企业id" )
    private String enterprise_id;

    @ApiModelProperty(value = "是否部署")
    private Integer is_deploy;
}
