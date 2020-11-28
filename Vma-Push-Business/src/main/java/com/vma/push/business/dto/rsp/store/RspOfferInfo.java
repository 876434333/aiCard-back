package com.vma.push.business.dto.rsp.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by huxiangqiang on 2018/7/25.
 */
@Data
public class RspOfferInfo {
    @ApiModelProperty(value="商品id")
    private String offer_id;
    @ApiModelProperty(value="商品名称")
    private String offer_name;
    @ApiModelProperty(value = "规格图片")
    private String norms_pic;
    @ApiModelProperty(value = "商品价格")
    private BigDecimal offer_price;
    @ApiModelProperty(value = "是否推荐 1推荐 0否")
    private Integer is_top;
}
