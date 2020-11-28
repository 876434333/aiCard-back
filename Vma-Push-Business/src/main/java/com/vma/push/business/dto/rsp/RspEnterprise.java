package com.vma.push.business.dto.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by ChenXiaoBin
 * 2018/5/4 9:20
 */
@Data
public class RspEnterprise {
    @ApiModelProperty(value = "企业ID")
    private String id;
    @ApiModelProperty(value = "企业地址")
    private  String address;//企业地址
    @ApiModelProperty(value = "企业电话")
    private  String phone;//企业电话
    @ApiModelProperty(value = "营业执照编码")
    private  String business_license_code;//营业执照编码
    @ApiModelProperty(value = "到期时间")
    private Date expire_time;//到期时间效期
    @ApiModelProperty(value = "名片数量")
    private  Integer sale_card_num;//名片数量
    @ApiModelProperty(value = "已用名片数量")
    private Integer used_car_num;//已用名片数量
    @ApiModelProperty(value = "管理员名字")
    private  String manager_name;//管理员名字
    @ApiModelProperty(value = "管理员电话")
    private  String manager_phone;//管理员电话
    @ApiModelProperty(value = "登录密码")
    private  String pass_word;//登录密码
    @ApiModelProperty(value = "企业名称")
    private  String name;
    @ApiModelProperty(value = "头像")
    private  String head_icon;
    @ApiModelProperty(value = "使用名片数量")
    private  Integer card_sum;
    @ApiModelProperty(value = "卡片总数")
    private  Integer money_init;
}
