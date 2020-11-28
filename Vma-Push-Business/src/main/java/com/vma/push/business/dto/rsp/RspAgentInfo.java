package com.vma.push.business.dto.rsp;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.util.Date;

/**
 * Created by huxiangqiang on 2018/6/14.
 */
@Data
public class RspAgentInfo {
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "id2")
    private String id2;

    @ApiModelProperty(value = "logo")
    private String head_icon;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "编号")
    private String enterprise_no;

    @ApiModelProperty(value = "账号")
    private String login;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "状态  1启用 0禁用")
    private Integer status;

    @ApiModelProperty(value = "地址")
    private String address;

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

    @ApiModelProperty(value = "创建时间")
    private Date create_time;

    @ApiModelProperty(value = "创建人")
    private String create_staff_name;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "剩余迹点")
    private Integer money_leave;

    @ApiModelProperty(value = "累计迹点")
    private Integer money_sum;

    @ApiModelProperty(value = "初始迹点")
    private Integer money_init;

    @ApiModelProperty(value = "下级企业数")
    private Integer enterprise_count;

    @ApiModelProperty(value = "上级")
    private String parent_name;

    @ApiModelProperty(value = "父节点",hidden = true)
    private String parent_id;

    @ApiModelProperty(value = "是否有权限操作")
    private Integer is_power;
}
