package com.vma.push.business.dto.rsp.staff;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by ChenXiaoBin
 * 2018/5/20 14:02
 */
@Data
public class RspCarlist {
    @ApiModelProperty(value = "销售人员id")
    private String id;
    @ApiModelProperty(value = "销售人员姓名")
    private String name;
    @ApiModelProperty(value = "职称")
    private String station;
    @ApiModelProperty(value = "企业Id")
    private String enterprise_id;
    @ApiModelProperty(value = "公司姓名")
    private String enterprise_name;
    @ApiModelProperty(value = "部门id")
    private String department_id;
    @ApiModelProperty(value = "销售人员座机")
    private String mobile;
    @ApiModelProperty(value = "销售人员电话")
    private String phone;
    @ApiModelProperty(value = "来自")
    private Integer froms;
    @ApiModelProperty(value = "头像")
    private String head_icon;
    @ApiModelProperty(value = "时间")
    private Date create_time;
    @ApiModelProperty(value = "是否屏蔽")
    private Integer Status;
}
