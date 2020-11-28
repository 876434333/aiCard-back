package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by huxiangqiang on 2018/7/17.
 */
@Data
public class ReqFunnel {
    @ApiModelProperty(value = "部门")
    private String department_id;

    @ApiModelProperty(value = "员工id")
    private String staff_id;

    @ApiModelProperty(value = "n天前")
    private String day;

    @ApiModelProperty(value = "企业",hidden = true)
    private  String enterprise_id;

    @ApiModelProperty(value = "类型",hidden = true)
    private Integer type;

}
