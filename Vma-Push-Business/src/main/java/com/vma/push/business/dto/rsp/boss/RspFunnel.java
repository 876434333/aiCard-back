package com.vma.push.business.dto.rsp.boss;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by huxiangqiang on 2018/7/17.
 */
@Data
public class RspFunnel {
    @ApiModelProperty(value = "name")
    private String name;
    @ApiModelProperty(value = "值")
    private Integer value;
    @ApiModelProperty(value = "增长数")
    private Integer increase;
}
