package com.vma.push.business.dto.rsp.customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Create By ChenXiAoBin
 * on 2018/6/14
 */
@Data
public class RspApplyList {
    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "企业名称")
    private String enterprise_name;
    @ApiModelProperty(value = "企业电话")
    private String enterprise_phone;
    @ApiModelProperty(value = "模版名称")
    private String template_name;
    @ApiModelProperty(value = "部署状态")
    private Integer is_deploy;
}
