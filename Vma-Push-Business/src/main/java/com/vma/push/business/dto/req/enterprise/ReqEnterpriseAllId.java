package com.vma.push.business.dto.req.enterprise;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by ChenXiaoBin
 * 2018/5/25 14:51
 */
@Data
public class ReqEnterpriseAllId {
    @ApiModelProperty(value = "企业id")
    private String id;
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
    @ApiModelProperty(value = "支付成功通知")
    private String pay_template;
    // V2新增
    @ApiModelProperty(value = "平台标志")
    private boolean isPlatform;
    @ApiModelProperty(value = "部门id")
    private int department_id;
}
