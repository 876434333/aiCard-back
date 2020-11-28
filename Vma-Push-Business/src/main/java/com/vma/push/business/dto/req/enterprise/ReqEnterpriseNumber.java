package com.vma.push.business.dto.req.enterprise;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by JINzm
 * 2018/6/15
 */
@Data
public class ReqEnterpriseNumber {

    @ApiModelProperty(value = "角色")
    private Integer roleId;
    @ApiModelProperty(value = "企业id")
    private String id;
}
