package com.vma.push.business.dto.shop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ShopProduct {

    @ApiModelProperty(value = "产品ID", required = true)
    private String id;

    @ApiModelProperty(value = "产品名称", required = true)
    private String name;

    @ApiModelProperty(value = "产品价格", required = true)
    private double salePrice;

    @ApiModelProperty(value = "产品编码", required = true)
    private String code;

    @ApiModelProperty(value = "产品描述", required = true)
    private String descript;

    @ApiModelProperty(value = "产品语音介绍", required = true)
    private String audio_url;

    @ApiModelProperty(value = "产品视频介绍", required = true)
    private String vidio_url;

    @ApiModelProperty(value = "创建员工ID", required = true)
    private String create_staff_id;

    @ApiModelProperty(value = "所属企业ID", required = true)
    private String enterprise_id;

    @ApiModelProperty(value = "产品图片", required = true)
    private List<ShopProductImg> productImgs;

}
