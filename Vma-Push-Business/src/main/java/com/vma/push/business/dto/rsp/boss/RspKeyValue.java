package com.vma.push.business.dto.rsp.boss;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by huxiangqiang on 2018/7/17.
 */
@Data
public class RspKeyValue {
    @ApiModelProperty(value = "name")
    private String name;
    @ApiModelProperty(value = "value")
    private String value;
}
