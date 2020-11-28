package com.vma.push.business.dto.shop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ShopOfferSpec {

    @ApiModelProperty(value = "商品ID", required = true)
    private String id;

    @ApiModelProperty(value = "规格ID", required = true)
    private String norms_id;

    @ApiModelProperty(value = "商品名称", required = true)
    private String offer_name;

    @ApiModelProperty(value = "商品价格", required = true)
    private double offer_price;

    @ApiModelProperty(value = "商品分类ID", required = true)
    private String category_id;

    @ApiModelProperty(value = "商品分类名称", required = true)
    private String category_name;

    @ApiModelProperty(value = "商品库存", required = true)
    private Integer offer_leave;

    @ApiModelProperty(value = "商品编码", required = true)
    private String code;

    @ApiModelProperty(value = "商品描述", required = true)
    private String descript;

    @ApiModelProperty(value = "状态", required = true)
    private Integer status;

    @ApiModelProperty(value = "所属企业ID", required = true)
    private String enterprise_id;

    @ApiModelProperty(value = "创建员工ID", required = true)
    private String create_staff_id;

    @ApiModelProperty(value = "商品图片", required = true)
    private List<ShopOfferImg> offerImgs;

}
