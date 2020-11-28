package com.vma.push.business.dto.rsp;

import com.vma.push.business.dto.rsp.superback.RspAdminMenu;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by huxiangqiang on 2018/6/22.
 */
@Data
public class RspUserMenuRole {

    @ApiModelProperty(value = "菜单id",required = true)
    String menu_id;

    @ApiModelProperty(value = "菜单名称",required = true)
    String name;

    @ApiModelProperty(value = "是否有权限",required = true)
    Integer is_role;

    @ApiModelProperty(value = "子节点")
    List<RspUserMenuRole> child;
}
