package com.vma.push.business.dto.rsp.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by huxiangqiang on 2018/7/25.
 */
@Data
public class RspNorms {
    @ApiModelProperty(value = "规格名称")
    private String norms_name;
    @ApiModelProperty(value = "规格id")
    private String norms_id;
    @ApiModelProperty(value = "是否默认规格 1是 0不是")
    private Integer is_default;
    @ApiModelProperty(value = "库存量")
    private Integer offer_leave;
    @ApiModelProperty(value = "价格")
    private BigDecimal offer_price;
    @ApiModelProperty(value = "规格图片")
    private String norms_pic;
}
