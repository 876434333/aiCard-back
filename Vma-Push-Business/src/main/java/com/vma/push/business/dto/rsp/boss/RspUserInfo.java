package com.vma.push.business.dto.rsp.boss;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by huxiangqiang on 2018/7/20.
 */
@Data
public class RspUserInfo {
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "企业")
    private String enterprise;
    @ApiModelProperty(value = "部门")
    private String department;
    @ApiModelProperty(value = "职称")
    private String station;
    @ApiModelProperty(value = "电话")
    private String phone;
    @ApiModelProperty(value = "名片")
    private String soft_img_url;
    @ApiModelProperty(value = "邮箱")
    private String mail;
    @ApiModelProperty(value = "头像")
    private String head_icon;
/*    @ApiModelProperty(value = "客户数")
    private Integer customer;
    @ApiModelProperty(value = "跟进用户数")
    private Integer attach;
    @ApiModelProperty(value = "咨询数")
    private Integer consult;*/
}
