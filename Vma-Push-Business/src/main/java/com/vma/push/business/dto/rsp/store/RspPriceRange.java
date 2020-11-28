package com.vma.push.business.dto.rsp.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by huxiangqiang on 2018/7/25.
 */
@Data
public class RspPriceRange {
    @ApiModelProperty(value = "最大价格")
    private BigDecimal max_price;
    @ApiModelProperty(value = "最小价格")
    private BigDecimal min_price;
}
