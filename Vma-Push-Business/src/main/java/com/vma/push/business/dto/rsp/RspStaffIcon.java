package com.vma.push.business.dto.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by JINzm
 * 2018/7/8
 */
@Data
public class RspStaffIcon {
    @ApiModelProperty(value = "员工头像")
    private String head_icon;
    @ApiModelProperty(value = "员工昵称")
    private String name;
}
