package com.vma.push.business.dto.req.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Create By ChenXiAoBin
 * on 2018/7/26
 */
@Data
public class ReqOrderStatistic {

    @ApiModelProperty(value = "当前页",required = true)
    int page_num;//当前页

    @ApiModelProperty(value = "每页记录数",required = true)
    int page_size; //每页的记录数

    @ApiModelProperty(value = "开始时间")
    private Date begin_time;

    @ApiModelProperty(value = "结束时间")
    private Date end_time;

    @ApiModelProperty(value = "员工姓名")
    private  String staff_name;

    @ApiModelProperty(value = "部门ID")
    private String department_id;

    @ApiModelProperty(value = "企业ID",hidden = true)
    private String enterprise_id;
}
