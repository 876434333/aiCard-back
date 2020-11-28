package com.vma.push.business.dto.req.store;

import com.vma.push.business.entity.UserAddress;
import com.vma.push.business.util.UuidUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Create By ChenXiAoBin
 * on 2018/7/24
 */
@Data
public class ReqUpdateAddress {
    @ApiModelProperty(value = "收货地址ID")
    private String id;

    @ApiModelProperty(value = "联系人")
    private String name;

    @ApiModelProperty(value = "联系人电话")
    private String phone;

    @ApiModelProperty(value = "省")
    private String province_code;

    @ApiModelProperty(value = "市")
    private String city_code;

    @ApiModelProperty(value = "区")
    private String area_code;

    @ApiModelProperty(value = "详细地址")
    private String address;

//    @ApiModelProperty(value = "是否为默认地址 1为默认")
//    private Integer is_default;



    public UserAddress toUserAddress(){
        UserAddress userAddress = new UserAddress();
        userAddress.setName(this.getName());
        userAddress.setPhone(this.getPhone());
        userAddress.setProvinceCode(this.getProvince_code());
        userAddress.setCityCode(this.getCity_code());
        userAddress.setAreaCode(this.getArea_code());
        userAddress.setAddress(this.getAddress());
      //  userAddress.setIsDefault(this.getIs_default());
//        userAddress.setIsDefault(0);
//        userAddress.setStatus(1);
        userAddress.setId(this.getId());
        return  userAddress;
    }
}
