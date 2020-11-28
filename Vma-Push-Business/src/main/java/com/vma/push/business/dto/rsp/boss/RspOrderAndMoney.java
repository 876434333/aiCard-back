package com.vma.push.business.dto.rsp.boss;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by huxiangqiang on 2018/7/17.
 */
@Data
public class RspOrderAndMoney {
    @ApiModelProperty(value = "订单量")
    private List<RspKeyValue> order;

    @ApiModelProperty(value = "交易金额")
    private List<RspKeyValue> money;
}
