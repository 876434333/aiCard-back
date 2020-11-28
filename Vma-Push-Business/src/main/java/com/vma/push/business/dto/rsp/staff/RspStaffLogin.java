package com.vma.push.business.dto.rsp.staff;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by ChenXiaoBin
 * 2018/5/21 0:45
 */
@Data
public class RspStaffLogin {
    @ApiModelProperty(value = "员工登录名")
    private String name;

}
