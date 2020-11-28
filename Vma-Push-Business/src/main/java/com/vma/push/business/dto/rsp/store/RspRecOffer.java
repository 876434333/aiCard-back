package com.vma.push.business.dto.rsp.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by huxiangqiang on 2018/8/13.
 */
@Data
public class RspRecOffer {
    @ApiModelProperty(value = "商品id")
    private  String id;
    @ApiModelProperty(value = "商品名称")
    private String offer_name;
    @ApiModelProperty(value = "商品图片")
    private String norms_pic;
    @ApiModelProperty(value = "商品价格")
    private BigDecimal offer_price;
}
