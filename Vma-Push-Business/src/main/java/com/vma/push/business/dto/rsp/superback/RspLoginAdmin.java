package com.vma.push.business.dto.rsp.superback;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * created by linzh on 2018/6/19
 */
@Data
public class RspLoginAdmin {
    @ApiModelProperty(value = "菜单列表")
    private List<RspAdminMenu> menu;
    @ApiModelProperty(value = "用户id")
    private String id;
    @ApiModelProperty(value = "用户名称")
    private String name;

    @ApiModelProperty(value = "用户头像")
    private String head_icon;

    @ApiModelProperty(value = "sessionId")
    private String token;

    @ApiModelProperty(value = "后台名称")
    private String web_title;

    @ApiModelProperty(value = "后台logo")
    private String web_icon;

    private String oem_name;
    private String oem_icon;
}
