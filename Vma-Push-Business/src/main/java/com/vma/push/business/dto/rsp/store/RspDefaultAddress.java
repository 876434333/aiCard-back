package com.vma.push.business.dto.rsp.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Create By ChenXiAoBin
 * on 2018/7/30
 */
@Data
public class RspDefaultAddress {
    @ApiModelProperty(value = "收货地址ID")
    private String id;

    @ApiModelProperty(value = "是否为默认")
    private Integer is_default;

    @ApiModelProperty(value = "联系人姓名")
    private String name;

    @ApiModelProperty(value = "联系人电话")
    private String phone;

    @ApiModelProperty(value = "收货地址")
    private String address;

    @ApiModelProperty(value = "省份")
    private String province_name;

    @ApiModelProperty(value = "城市")
    private String city_name;

    @ApiModelProperty(value = "地区")
    private String area_name;
}
