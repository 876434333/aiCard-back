package com.vma.push.business.dto.rsp.customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Create By ChenXiAoBin
 * on 2018/6/14
 */
@Data
public class RspApplyEnterprise {
    @ApiModelProperty(value = "企业ID")
    private String id;
    @ApiModelProperty(value = "企业名字")
    private String enterprise_name;
    @ApiModelProperty(value = "父级代理商")
    private String parent_enterprise;//父级代理商
    @ApiModelProperty(value = "企业联系电话")
    private String enterprise_phone;//企业联系电话
    @ApiModelProperty(value = "编号")
    private String code;//编号
    @ApiModelProperty(value = "状态")
    private Integer status;//状态
    @ApiModelProperty(value = "企业地址")
    private String address;//企业地址
    @ApiModelProperty(value = "账号")
    private String manager_phone;//账号
    @ApiModelProperty(value = "创建时间")
    private Date create_time;//创建时间
    @ApiModelProperty(value = "初始名片数")
    private Integer saleCardNum;//初始名片
    @ApiModelProperty(value = "创建人名字")
    private  String create_name;//创建人名字
    @ApiModelProperty(value = "备注")
    private String remark;//备注
    @ApiModelProperty(value = "是否部署")
    private Integer is_deploy;
    @ApiModelProperty(value = "企业头像")
    private String head_icon;

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
    @ApiModelProperty(value = "留言回复通知")
    private String message_template;
    @ApiModelProperty(value = "支付回复通知")
    private String pay_template;


}
