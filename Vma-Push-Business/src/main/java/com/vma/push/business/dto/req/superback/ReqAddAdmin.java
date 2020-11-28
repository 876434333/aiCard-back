package com.vma.push.business.dto.req.superback;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * created by linzh on 2018/6/15
 */
@Data
public class ReqAddAdmin {
    @ApiModelProperty(value = "员工账号")
    private String login;

    @ApiModelProperty(value = "密码")
    private String pass_word;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建人id",hidden = true)
    private String create_id;

    @ApiModelProperty(value = "所属客户id",hidden = true)
    private String custom_id;

    @ApiModelProperty(value = "类型",hidden = true)
    private int type;
}
