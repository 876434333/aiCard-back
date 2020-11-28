package com.vma.push.business.dto.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by JINzm
 * 2018/6/14
 */
@Data
public class RspPointNumber {
    @ApiModelProperty(value = "昨天")
    private Long yesterday;
    @ApiModelProperty(value = "今天")
    private Long today;
    @ApiModelProperty(value = "全部")
    private Long all;
}
