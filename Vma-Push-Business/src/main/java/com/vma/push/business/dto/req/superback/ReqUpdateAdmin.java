package com.vma.push.business.dto.req.superback;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * created by linzh on 2018/6/15
 */
@Data
public class ReqUpdateAdmin {
    @ApiModelProperty(value = "员工id")
    private String id;

    @ApiModelProperty(value = "员工账号")
    private String login;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "备注")
    private String remark;

}
