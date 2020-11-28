package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * created by linzh on 2018/6/14
 */
@Data
public class ReqAdminMenu {
    @ApiModelProperty(value = "员工id",required = true)
    String admin_id;//员工id
    @ApiModelProperty(value = "菜单id",required = true)
    String menu_id;//菜单id
}
