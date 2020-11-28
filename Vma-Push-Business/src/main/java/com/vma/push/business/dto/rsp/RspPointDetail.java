package com.vma.push.business.dto.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by JINzm on 2018/7/5.
 */
@Data
public class RspPointDetail {
    @ApiModelProperty(value = "初始迹点数")
    Long money_init;
    @ApiModelProperty(value = "累计迹点数")
    Long money_sum;
    @ApiModelProperty(value = "剩余迹点数")
    Long money_leave;
}
