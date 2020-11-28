package com.vma.push.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class WebsiteTemplate {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "text_content")
    private String textContent;

    @ApiModelProperty(value = "create_time")
    private Date create_time;

    @ApiModelProperty(value = "modify_time")
    private Date modify_time;

    @ApiModelProperty(value = "enterprise_id")
    private String enterprise_id;

    @ApiModelProperty(value = "type")
    private int type;

    @ApiModelProperty(value = "simple_desc")
    private String simple_desc;

    @ApiModelProperty(value = "order_num")
    private int order_num;

    @ApiModelProperty(value = "config")
    private String config;

    @ApiModelProperty(value = "website_id")
    private String website_id;
}
