package com.vma.push.business.dto.req.store;

import com.vma.push.business.entity.UserAddress;
import com.vma.push.business.util.UuidUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Create By ChenXiAoBin
 * on 2018/7/24
 */
@Data
public class ReqAddAddress {
    @ApiModelProperty(value = "用户ID")
    private String user_id;

    @ApiModelProperty(value = "联系人")
    private String name;

    @ApiModelProperty(value = "联系人电话")
    private String phone;

    @ApiModelProperty(value = "省编号",hidden = true)
    private String province_code;

    @ApiModelProperty(value = "市编号",hidden = true)
    private String city_code;

    @ApiModelProperty(value = "区编号",hidden = true)
    private String area_code;

    @ApiModelProperty(value = "详细地址")
    private String address;

    @ApiModelProperty(value = "省名字")
    private String province_name;

    @ApiModelProperty(value = "城市名字")
    private String city_name;

    @ApiModelProperty(value = "地区名字")
    private String area_name;



    public UserAddress toUserAddress(){
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(this.getUser_id());
        userAddress.setName(this.getName());
        userAddress.setPhone(this.getPhone());
//        userAddress.setProvinceCode(this.getProvince_code());
//        userAddress.setCityCode(this.getCity_code());
//        userAddress.setAreaCode(this.getArea_code());
        userAddress.setAddress(this.getAddress());
        userAddress.setCreateTime(new Date());
        userAddress.setIsDefault(0);
        userAddress.setStatus(1);
        userAddress.setId(UuidUtil.getRandomUuid());
        return  userAddress;
    }
}
