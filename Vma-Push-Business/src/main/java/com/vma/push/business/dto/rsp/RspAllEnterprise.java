package com.vma.push.business.dto.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by ChenXiaoBin
 * 2018/5/6 21:11
 */
@Data
public class RspAllEnterprise {
    @ApiModelProperty(value = "ID")
    private  String id;
    @ApiModelProperty(value = "企业名称")
    private  String name;
    @ApiModelProperty(value = "名片数量")
    private  Integer sale_card_num;//名片数量
    @ApiModelProperty(value = "注册时间")
    private  Date create_time;//注册时间
//    @ApiModelProperty(value = "证件有效期")
//    private  String expiry_time;//证件有效期
    @ApiModelProperty(value = "创建人")
    private  String admin_name;//创建人
    @ApiModelProperty(value = "企业地址")
    private  String address;//企业地址
    @ApiModelProperty(value = "营业执照编码")
    private  String business_license_code;//营业执照编码
    @ApiModelProperty(value = "营业执照图片地址",required = true)
    String business_license_url;
    @ApiModelProperty(value = "管理员姓名")
    private  String manager_name;//管理员姓名
    @ApiModelProperty(value = "管理员电话")
    private  String manager_phone;//管理员电话
    @ApiModelProperty(value = "企业电话")
    private  String phone;//企业电话
    @ApiModelProperty(value = "状态")
    private  Integer status;//
    @ApiModelProperty(value = "过期时间")
    private Date expire_time;
    @ApiModelProperty(value = "小程序")
    private Integer auth_soft;
    @ApiModelProperty(value = "企业微信")
    private Integer auth_wei;

    @ApiModelProperty(value = "公司的CropID")
    private String corp_id;
    @ApiModelProperty(value = "通讯录密钥")
    private String contacts_secret;
    @ApiModelProperty(value = "boss雷达密钥")
    private  String boss_secret;
    @ApiModelProperty(value = "ai雷达密钥")
    private String ai_secret;
    @ApiModelProperty(value = "boss雷达应用id")
    private  String boss_agent_id;
    @ApiModelProperty(value = "ai雷达应用id")
    private String ai_agent_id;
    @ApiModelProperty(value = "小程序id")
    private  String app_id;
    @ApiModelProperty(value = "小程序密钥")
    private String secret;
    @ApiModelProperty(value = "用户商id")
    private String mch_id;
    @ApiModelProperty(value = "支付密钥")
    private String pay_key;
    @ApiModelProperty(value = "企业头像")
    private String head_icon;
}
