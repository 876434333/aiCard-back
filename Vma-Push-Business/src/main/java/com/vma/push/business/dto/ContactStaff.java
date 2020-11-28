package com.vma.push.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ContactStaff {

    @ApiModelProperty(value = "id", required = true)
    private String id;

    @ApiModelProperty(value = "name", required = true)
    private String name;

    @ApiModelProperty(value = "phone", required = true)
    private String phone;

    @ApiModelProperty(value = "head_icon", required = true)
    private String head_icon;

    @ApiModelProperty(value = "department_id", required = true)
    private String department_id;

    @ApiModelProperty(value = "enterprise_id", required = true)
    private String enterprise_id;

    @ApiModelProperty(value = "department_name", required = true)
    private String department_name;

    @ApiModelProperty(value = "first_letter", required = true)
    private String first_letter;

    @ApiModelProperty(value = "station", required = true)
    private String station;

    @ApiModelProperty(value = "mail")
    private String mail;

    @ApiModelProperty(value = "status")
    private Integer status;

}
