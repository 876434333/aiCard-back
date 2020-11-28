package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by ChenXiaoBin
 * 2018/5/4 6:12
 */
@Data
public class ReqUpdateEnterprise {
    @ApiModelProperty(value = "企业id")
    String id;//企业id
    @ApiModelProperty(value = "企业名称")
    String name;//企业名称
    @ApiModelProperty(value = "企业地址")
    String address;//企业地址
    @ApiModelProperty(value = "企业电话")
    String phone;//企业电话
    @ApiModelProperty(value = "营业执照")
    String business_license_code;//营业执照
    @ApiModelProperty(value = "证件有效期")
    Date expire_time; //证件有效期（到期时间）
    @ApiModelProperty(value = "名片数量")
    Integer sale_card_num;//名片数量
    @ApiModelProperty(value = "状态")
    Integer status;
    @ApiModelProperty(value = "管理员姓名")
    String manager_name;//管理员姓名
    @ApiModelProperty(value = "管理员电话")
    String manager_phone;//管理员电话
    //String password;//密码
    @ApiModelProperty(value = "小程序")
    Integer auth_soft;
    @ApiModelProperty(value = "企业微信")
    Integer auth_wei;
    @ApiModelProperty(value = "企业头像")
    String head_icon;
}
