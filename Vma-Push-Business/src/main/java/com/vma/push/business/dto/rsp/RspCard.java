package com.vma.push.business.dto.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by huxiangqiang on 2018/5/14.
 */
@Data
public class RspCard {
    @ApiModelProperty(value = "销售人员ID")
    String employee_id;
    @ApiModelProperty(value = "销售人员姓名")
    String name;
    @ApiModelProperty(value = "岗位")
    String station;
    @ApiModelProperty(value = "部门名称")
    String department_name;
    @ApiModelProperty(value = "手机号")
    String phone;
    @ApiModelProperty(value = "头像")
    String head_icon;
    @ApiModelProperty(value = "企业id")
    String enterprise_id;
    @ApiModelProperty(value = "企业名称")
    String enterprise_name;
    @ApiModelProperty(value = "邮箱")
    String mail;
    @ApiModelProperty(value = "二维码")
    String soft_img_url;
}
