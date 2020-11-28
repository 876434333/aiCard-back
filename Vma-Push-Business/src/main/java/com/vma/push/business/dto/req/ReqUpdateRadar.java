package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by ChenXiaoBin
 * 2018/5/4 15:15
 */
@Data
public class ReqUpdateRadar {
    @ApiModelProperty(value = "员工ID",required = true)
    private String id;
    @ApiModelProperty(value = "ai雷达开关,1开启，0关闭",required = true)
    private Integer open_ai;
    @ApiModelProperty(value = "boss雷达开关，1开启，0关闭",required = true)
    private Integer open_boss;
}
