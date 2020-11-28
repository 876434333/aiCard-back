package com.vma.push.business.dto.req.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by huxiangqiang on 2018/7/25.
 */
@Data
public class RspOfferNorms {
    @ApiModelProperty(value="商品名字")
    private String name;
    @ApiModelProperty(value="单价")
    private BigDecimal price;
    @ApiModelProperty(value="库存数")
    private Integer num;
    @ApiModelProperty(value="图片路劲")
    private String url;
    @ApiModelProperty(value="是否默认 1默认 0否")
    private Integer is_default;
    @ApiModelProperty(value="规格类型 0单规格 1多规格")
    private Integer type;
}
