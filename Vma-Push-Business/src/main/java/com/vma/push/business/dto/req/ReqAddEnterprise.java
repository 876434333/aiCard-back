package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by ChenXiaoBin
 * 2018/5/7 15:32
 */
@Data
public class ReqAddEnterprise {
   // String id;//企业id
   @ApiModelProperty(value = "企业名称",required = true)
    String name;//企业名称
   @ApiModelProperty(value = "企业地址",required = true)
    String address;//企业地址
   @ApiModelProperty(value = "企业电话",required = true)
    String phone;//企业电话
   @ApiModelProperty(value = "营业执照",required = true)
    String business_license_code;//营业执照
   @ApiModelProperty(value = "营业执照图片地址",required = true)
    String business_license_url;
   @ApiModelProperty(value = "到期时间",required = true)
    Date expire_time; //（到期时间）
   @ApiModelProperty(value = "名片数量",required = true)
    Integer sale_card_num;//名片数量
   @ApiModelProperty(value = "管理员姓名",required = true)
    String manager_name;//管理员姓名
   @ApiModelProperty(value = "管理员电话",required = true)
    String manager_phone;//管理员电话
    @ApiModelProperty(value = "企业头像")
    String icon;
}
