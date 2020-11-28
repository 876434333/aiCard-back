package com.vma.push.business.dto.req.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by huxiangqiang on 2018/7/26.
 */
@Data
public class ReqDiscount {
    @ApiModelProperty(value = "部门id")
    private String department_id;
    @ApiModelProperty(value = "员工名字")
    private String staff_name;
    @ApiModelProperty(value = "是否个人特权 1个人特权 0企业特权")
    private Integer discount_type;
    @ApiModelProperty(value = "企业id",hidden = true)
    private String enterprise_id;
    @ApiModelProperty(value = "页面大小")
    private Integer page_size;
    @ApiModelProperty(value = "当前页")
    private Integer page_num;


}
