package com.vma.push.business.dto.rsp.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Create By ChenXiAoBin
 * on 2018/6/14
 */
@Data
public class RspPointRate {
    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "贴牌商汇率(1迹点与元换算)")
    private Float oem_rate;
    @ApiModelProperty(value = "地区总代理")
    private Float regional_rate;
    @ApiModelProperty(value = "代理商")
    private Float agent_rate;
}
