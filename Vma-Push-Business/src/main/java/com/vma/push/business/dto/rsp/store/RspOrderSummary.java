package com.vma.push.business.dto.rsp.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by huxiangqiang on 2018/7/24.
 */
@Data
public class RspOrderSummary {
    @ApiModelProperty(value = "未付款")
    private Integer unpay;

    @ApiModelProperty(value = "待发货")
    private Integer unsend;

    @ApiModelProperty(value = "待收货")
    private Integer unreceive;

    @ApiModelProperty(value = "已完成")
    private Integer done;
}
