package com.vma.push.business.dto.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by huxiangqiang on 2018/6/15.
 */
@Data
public class RspEnterInfo {
    @ApiModelProperty(value = "企业id")
    private String id;

    @ApiModelProperty(value = "企业id")
    private String id2;

    @ApiModelProperty(value = "logo")
    private String head_icon;

    @ApiModelProperty(value = "企业名称")
    private String name;

    @ApiModelProperty(value = "企业编号 显示用")
    private String enterprise_no;

    @ApiModelProperty(value = "账号")
    private String login;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "状态  1启用  0禁用")
    private Integer status;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "创建时间")
    private Date create_time;

    @ApiModelProperty(value = "到期时间")
    private Date expire_time;

    @ApiModelProperty(value = "创建人")
    private String create_staff_name;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "剩余迹点")
    private Integer money_leave;

    @ApiModelProperty(value = "迹点总数")
    private Integer money_sum;

    @ApiModelProperty(value = "初始迹点")
    private Integer money_init;

    @ApiModelProperty(value = "上级")
    private String parent_name;

    @ApiModelProperty(value = "是否部署")
    private Integer is_deploy;

    @ApiModelProperty(value = "模板id")
    private Integer template_id;

    @ApiModelProperty(value = "省编码")
    private String province_code;

    @ApiModelProperty(value = "区编码")
    private String area_code;

    @ApiModelProperty(value = "市编号")
    private String city_code;

    @ApiModelProperty(value = "省")
    private String province_name;

    @ApiModelProperty(value = "区")
    private String area_name;

    @ApiModelProperty(value = "市")
    private String city_name;


}
