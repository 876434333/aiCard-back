package com.vma.push.business.dto.req.superback;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by huxiangqiang on 2018/6/15.
 */
@Data
public class ReqEditEnterprise {


    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "账号")
    private String login;

    @ApiModelProperty(value = "id2")
    private String id2;

    @ApiModelProperty(value = "企业名称")
    private String name;

    @ApiModelProperty(value = "LOGO")
    private String head_icon;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "迹点")
    private Integer money_init;

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

    @ApiModelProperty(value = "admin表权限",hidden = true)
    private Integer type ;

    @ApiModelProperty(value = "到期时间  新增企业需要")
    private Date expire_time ;
}
