package com.vma.push.business.dto.req.enterprise;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@Data
public class ReqEnterpriseFileDir {

    @ApiModelProperty(value = "目录名字")
    private String dir_name;
    @ApiModelProperty(value = "目录id")
    private String id;
    @ApiModelProperty(value = "目录密码")
    private String dir_password;
}
