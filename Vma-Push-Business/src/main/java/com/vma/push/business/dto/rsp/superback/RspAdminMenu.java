package com.vma.push.business.dto.rsp.superback;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * created by linzh on 2018/6/15
 */
@Data
public class RspAdminMenu {
    @ApiModelProperty(value = "菜单id",required = true)
    String id;
    @ApiModelProperty(value = "菜单名称",required = true)
    String name;
    @ApiModelProperty(value = "菜单地址",required = true)
    String url;
    @ApiModelProperty(value = "父亲节点id",required = true)
    String parent_id;
    @ApiModelProperty(value = "节点层级",required = true)
    String rank;
    @ApiModelProperty(value = "是否叶子节点",notes = "是否叶子节点（0 否，1是）" ,required = true)
    String is_leaf;
    @ApiModelProperty(value = "顺序",required = true)
    int seq;
    @ApiModelProperty(value = "子节点")
    List<RspAdminMenu> child;

    @ApiModelProperty(value = "打开方式")
    Integer target;
}
