package com.vma.push.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class Website {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "name", required = true)
    private String name;

    @ApiModelProperty(value = "enterprise_id", required = true)
    private String enterprise_id;

    @ApiModelProperty(value = "create_time")
    private Date create_time;

    @ApiModelProperty(value = "modify_time")
    private Date modify_time;
}
