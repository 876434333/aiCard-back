package com.vma.push.business.dto.shop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ShopOfferImg {

    @ApiModelProperty(value = "图片ID")
    private String id;

    @ApiModelProperty(value = "商品ID")
    private String offer_spec_id;

    @ApiModelProperty(value = "图片地址")
    private String img_url;

    @ApiModelProperty(value = "图片类型，1=封面图,2=详情图")
    private String type;

    @ApiModelProperty(value = "描述")
    private String descript;

    @ApiModelProperty(value = "是否有效，0=有效，1=无效")
    private String status;

    @ApiModelProperty(value = "排序")
    private String order;

}
