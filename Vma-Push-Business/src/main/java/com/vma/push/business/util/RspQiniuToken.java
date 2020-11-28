package com.vma.push.business.util;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class RspQiniuToken implements Serializable{
    @ApiModelProperty(value = "token")
    private String token;
    @ApiModelProperty(value = "地区")
    private QiniuZone qiniu_zone;
    @ApiModelProperty(value = "域名")
    private String domain_url;
}