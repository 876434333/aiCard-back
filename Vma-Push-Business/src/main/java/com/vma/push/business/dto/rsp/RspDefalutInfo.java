package com.vma.push.business.dto.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by huxiangqiang on 2018/7/17.
 */
@Data
public class RspDefalutInfo {
    @ApiModelProperty(value = "openid")
    private String open_id;
    @ApiModelProperty(value = "默认员工")
    private String staff_id;
    @ApiModelProperty(value = "默认部门")
    private String department_id;
    @ApiModelProperty(value = "默认日期")
    private String enterprise_id;
}
