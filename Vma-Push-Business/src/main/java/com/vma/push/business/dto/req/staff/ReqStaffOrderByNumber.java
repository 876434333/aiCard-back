package com.vma.push.business.dto.req.staff;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * Created by JINzm
 * 2018/7/17
 */
@Data
public class ReqStaffOrderByNumber {
    @ApiModelProperty(value = "天数(1/7/15/30)")
    private Integer day_number;
    @ApiModelProperty(value = "当前页",required = true)
    int page_num;
    @ApiModelProperty(value = "每页记录数",required = true)
    int page_size;
    @ApiModelProperty(value="部门ID",required =true)
    String department_id;
    @ApiModelProperty(value="企业ID",hidden =true)
    String enterprise_id;
}
