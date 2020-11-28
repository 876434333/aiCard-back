package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Create By ChenXiAoBin
 * on 2018/7/17
 */
@Data
public class ReqGetByRate {
    @ApiModelProperty(value = "当前页")
    private Integer page_num;

    @ApiModelProperty(value = "每页记录数")
    private Integer page_size;

    @ApiModelProperty(value = "成交率")
    private BigDecimal rate;

    @ApiModelProperty(value = "n天前")
    private String day;

    @ApiModelProperty(value = "部门")
    private String department_id;

    @ApiModelProperty(value = "企业ID",hidden = true)
    private String enterprise_id;

    @ApiModelProperty(value = "员工ID")
    private String staff_id;
}
