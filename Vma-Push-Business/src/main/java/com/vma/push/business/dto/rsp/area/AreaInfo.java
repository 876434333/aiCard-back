package com.vma.push.business.dto.rsp.area;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by chenzui on 2018/6/14.
 */
@Data
public class AreaInfo {
    @ApiModelProperty(value = "ID")
    String id;
    @ApiModelProperty(value = "地区名称")
    String name;
    @ApiModelProperty(value = "地区编码")
    String code;
}
