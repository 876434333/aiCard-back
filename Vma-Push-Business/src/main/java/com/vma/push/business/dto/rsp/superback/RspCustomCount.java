package com.vma.push.business.dto.rsp.superback;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * created by linzh on 2018/6/15
 */
@Data
public class RspCustomCount {
    @ApiModelProperty(value = "下级地区总代理数")
    private int area_num;

    @ApiModelProperty(value = "下级代理商数")
    private int agent_num;
}
