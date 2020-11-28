package com.vma.push.business.dto.rsp.superback;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * created by linzh on 2018/6/15
 */
@Data
public class RspAdmin {

    @ApiModelProperty(value = "Id")
    String id;

    @ApiModelProperty(value = "员工账号")
    private String login;

    @ApiModelProperty(value = "密码")
    private String pass_word;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "创建时间")
    private Date create_time;

    @ApiModelProperty(value = "最后登录时间")
    private Date login_time;

    @ApiModelProperty(value = "权限  1管理员 0员工")
    private String role;
}
