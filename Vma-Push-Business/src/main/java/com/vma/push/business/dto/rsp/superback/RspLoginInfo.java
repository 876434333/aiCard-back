package com.vma.push.business.dto.rsp.superback;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huxiangqiang on 2018/6/19.
 */
@Data
public class RspLoginInfo {

    @ApiModelProperty(value = "菜单列表")
    private List<RspAdminMenu> menu = new ArrayList<>();
    @ApiModelProperty(value = "用户id")
    private String id;
    @ApiModelProperty(value = "用户名称")
    private String name;
    @ApiModelProperty(value = "头像")
    private String head_icon;
    @ApiModelProperty(value = "手机")
    private String phone;
    private String oem_name;
    private String oem_icon;
}
