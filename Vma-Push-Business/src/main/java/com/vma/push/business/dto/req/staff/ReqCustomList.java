package com.vma.push.business.dto.req.staff;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by JINzm
 * 2018/7/17
 */
@Data
public class ReqCustomList {
    @ApiModelProperty(value = "按类型销售排行 1、按客户人数2、按互动频率3、按成交率区间")
    private Integer query_type;
    @ApiModelProperty(value = "天数(1/7/15/30)")
    private Integer day_number;
    @ApiModelProperty(value = "1跟进客户总数2咨询客户总数")
    private Integer user_type;
    @ApiModelProperty(value = "成交率下限")
    private Float low_close_rate;
    @ApiModelProperty(value = "成交率上限")
    private Float hight_close_rate;
    @ApiModelProperty(value="销售人员ID")
    private String staff_id;
    @ApiModelProperty(value="页码")
    private Integer page_num;
    @ApiModelProperty(value="条数")
    private Integer page_size;
}
