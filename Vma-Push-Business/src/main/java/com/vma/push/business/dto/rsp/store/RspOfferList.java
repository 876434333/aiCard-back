package com.vma.push.business.dto.rsp.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by huxiangqiang on 2018/7/25.
 */
@Data
public class RspOfferList {
    @ApiModelProperty(value = "商品id")
    private String id;
    @ApiModelProperty(value = "商品名称")
    private String offer_name;
    @ApiModelProperty(value = "规格图片")
    private String norms_pic;
    @ApiModelProperty(value="商品单价")
    private BigDecimal offer_price;
    @ApiModelProperty(value = "销量")
    private Integer offer_sale;
    @ApiModelProperty(value = "库存")
    private Integer offer_leave;
    @ApiModelProperty(value = "创建时间")
    private Date create_time;
    @ApiModelProperty(value = "上架时间")
    private Date onsale_time;

    @ApiModelProperty(value="提成方式 1固定提成 0百分比")
    private Integer extract_type;

    @ApiModelProperty(value="提成金额")
    private BigDecimal extract_value;
    @ApiModelProperty(value="提成比例 后面加上百分号")
    private BigDecimal extract_per;
    @ApiModelProperty(value="规格类型",hidden = true)
    private Integer type;

}
