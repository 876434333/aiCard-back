package com.vma.push.business.dto.shop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Category {

    @ApiModelProperty(value = "分类ID")
    private String id;

    @ApiModelProperty(value = "分类名称")
    private String name;

    @ApiModelProperty(value = "所属企业ID")
    private String enterprise_id;

    @ApiModelProperty(value = "创建员工ID")
    private String create_staff_id;
}
