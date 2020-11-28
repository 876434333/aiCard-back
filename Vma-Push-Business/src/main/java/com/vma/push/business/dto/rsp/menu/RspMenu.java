package com.vma.push.business.dto.rsp.menu;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by chenzui on 2018/5/8.
 */
@Data
public class RspMenu {
    @ApiModelProperty(value = "主键")
    String id;
    @ApiModelProperty(value = "名称")
    String title;
    @ApiModelProperty(value = "地址")
    String url;
    @ApiModelProperty(value = "唯一码")
    String code;
    @ApiModelProperty(value = "菜单图表")
    String menu_icon;

    List<RspMenu> child;
}
