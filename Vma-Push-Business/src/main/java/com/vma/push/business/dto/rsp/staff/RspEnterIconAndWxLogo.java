package com.vma.push.business.dto.rsp.staff;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by JINzm
 * 2018/7/12
 */
@Data
public class RspEnterIconAndWxLogo {
    @ApiModelProperty(value = "soft_img_url 微信二维码")
    private String soft_img_url;
    @ApiModelProperty(value = "head_icon 企业头像")
    private String head_icon;
    @ApiModelProperty(value = "企业名")
    private String name;//头像
    @ApiModelProperty(value = "手机图标")
    private String mobile_logo;
    @ApiModelProperty(value = "微信图标")
    private String weichat_logo;
    @ApiModelProperty(value="地址图标")
    private String location_logo;
}
