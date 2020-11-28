package com.vma.push.business.dto.req.staff;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by JINzm
 * 2018/7/17
 */
@Data
public class ReqStaffOrder {
    @ApiModelProperty(value = "按类型销售排行 1、按订单2、按客户人数3、按互动频率4、按成交率区间")
    private Integer query_type;
    @ApiModelProperty(value = "天数(1/7/15/30)")
    private Integer day_number;
    @ApiModelProperty(value = "1跟进客户总数2咨询客户总数")
    private Integer user_type;
    @ApiModelProperty(value = "成交率下限")
    private Float low_close_rate;
    @ApiModelProperty(value = "成交率上限")
    private Float hight_close_rate;
    @ApiModelProperty(value="部门ID",required =true)
    private String department_id;
    @ApiModelProperty(value="企业ID",hidden =true)
    private String enterprise_id;
}
