package com.vma.push.business.dto.shop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Shop {

    @ApiModelProperty(value = "产品ID")
    private String id;

    @ApiModelProperty(value = "企业ID")
    private String enterprise_id;

    @ApiModelProperty(value = "商城名称")
    private String name;

    @ApiModelProperty(value = "商城类型")
    private Integer type;
}
