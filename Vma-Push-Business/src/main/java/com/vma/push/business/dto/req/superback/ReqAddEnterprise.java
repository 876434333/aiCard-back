package com.vma.push.business.dto.req.superback;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by huxiangqiang on 2018/6/14.
 */
@Data
public class ReqAddEnterprise {
    @ApiModelProperty(value = "账号")
    private String login;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "编号",hidden = true)
    private String enterprise_no;

    @ApiModelProperty(value = "企业名称")
    private String name;

    @ApiModelProperty(value = "LOGO")
    private String head_icon;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "迹点")
    private Integer money_init;

    @ApiModelProperty(value = "模板")
    private String template_id;

    @ApiModelProperty(value = "省")
    private String province_code;

    @ApiModelProperty(value = "市")
    private String city_code;

    @ApiModelProperty(value = "区")
    private String area_code;

    @ApiModelProperty(value = "详细地址")
    private String  address;

    @ApiModelProperty(value = "备注")
    private String remark ;

    @ApiModelProperty(value = "上级单位",hidden = true )
    private String parent_id ;

    @ApiModelProperty(value = "创建人",hidden = true )
    private String create_by ;

    @ApiModelProperty(value = "1超级后台2贴牌商3地区总代理4代理商 admin表",hidden = true)
    private Integer type ;

    @ApiModelProperty(value = "1企业2代理商3地区总代理4贴牌商 企业表",hidden = true)
    private Integer role ;

    @ApiModelProperty(value = "企业id",hidden = true)
    private String id;

    @ApiModelProperty(value = "到期时间  新增企业需要")
    private Date expire_time ;

    // 以下为V2.0新增字段
    @ApiModelProperty(value = "0:PC端创建1:微信用户创建")
    private Integer create_scene = 0 ;

    @ApiModelProperty(value = "微信号")
    private String weixin ;

    @ApiModelProperty(value = "邮箱")
    private String email ;

    @ApiModelProperty(value = "个人简介")
    private String self_introduction ;

    @ApiModelProperty(value = "用户openid")
    private String openid ;
    @ApiModelProperty(value = "职务")
    private String position ;
    @ApiModelProperty(value = "人名")
    private String people_name ;
    @ApiModelProperty(value = "公司名")
    private String enterprise_name ;

    private String staffId;

}
