package com.vma.push.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ContactCard {

    @ApiModelProperty(value = "id", required = true)
    private String id;

    @ApiModelProperty(value = "name", required = true)
    private String name;

    @ApiModelProperty(value = "phone", required = true)
    private String phone;

    @ApiModelProperty(value = "head_icon", required = true)
    private String head_icon;

    @ApiModelProperty(value = "enterprise_name", required = true)
    private String enterprise_name;

    @ApiModelProperty(value = "first_letter", required = true)
    private String first_letter;

    @ApiModelProperty(value = "create_time", required = true)
    private String create_time;

    @ApiModelProperty(value = "department_id", required = true)
    private String department_id;

    @ApiModelProperty(value = "enterprise_id", required = true)
    private String enterprise_id;

    @ApiModelProperty(value = "enterprise_status", required = true)
    private Integer enterprise_status;

    @ApiModelProperty(value = "enterprise_modify_time", required = true)
    private String enterprise_modify_time;

    @ApiModelProperty(value = "station", required = true)
    private String station;

    @ApiModelProperty(value = "position", required = true)
    private String position;

    @ApiModelProperty(value = "relaStatus", required = true)
    private Integer relaStatus;

    @ApiModelProperty(value = "status", required = true)
    private Integer status;

    @ApiModelProperty(value = "froms", required = true)
    private Integer froms;

    @ApiModelProperty(value = "from_user_id", required = true)
    private String from_user_id;

    @ApiModelProperty(value = "relaId", required = true)
    private String relaId;

    @ApiModelProperty(value = "dimission_time", required = true)
    private String dimission_time;

    @ApiModelProperty(value = "is_collect", required = true)
    private Integer isCollect;
}
