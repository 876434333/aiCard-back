package com.vma.push.business.dto.rsp.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by huxiangqiang on 2018/7/25.
 */
@Data
public class RspNorms2 {
    @ApiModelProperty(value = "规格明细")
    private List<RspNorms> norms;
    @ApiModelProperty(value = "最大价格")
    private BigDecimal max_price;
    @ApiModelProperty(value = "最小价格")
    private BigDecimal min_price;
}
