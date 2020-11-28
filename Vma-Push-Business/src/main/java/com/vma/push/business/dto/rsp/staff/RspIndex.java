package com.vma.push.business.dto.rsp.staff;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.util.Date;

/**
 * Created by huxiangqiang on 2018/8/13.
 */
@Data
public class RspIndex {
    @ApiModelProperty(value = "员工数")
    private Integer staff_num;
    @ApiModelProperty(value = "名片总数")
    private Integer card_num;
    @ApiModelProperty(value = "已用名片数")
    private Integer card_used_num;
    @ApiModelProperty(value = "已用天数")
    private Integer used_days;
    @ApiModelProperty(value = "剩余天数")
    private Integer leave_days;
    @ApiModelProperty(value = "员工数")
    private Integer customer_num;
    @ApiModelProperty(value = "开通时间")
    private Date begin_date;
    @ApiModelProperty(value = "截至时间")
    private Date end_date;
}
