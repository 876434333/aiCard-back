package com.vma.push.business.dto.req.staff;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by JINzm
 * 2018/7/17
 */
@Data
public class ReqStaffOrderByClosedRate {
    @ApiModelProperty(value = "成交率下限")
    private Float low_close_rate;
    @ApiModelProperty(value = "成交率上限")
    private Float hight_close_rate;
    @ApiModelProperty(value = "当前页",required = true)
    int page_num;
    @ApiModelProperty(value = "每页记录数",required = true)
    int page_size;
    @ApiModelProperty(value="部门ID",required =true)
    String department_id;
    @ApiModelProperty(value="企业ID",hidden =true)
    String enterprise_id;
}
