package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReqCircleByStaffAndEnterprise {
    @ApiModelProperty(value = "员工ID",hidden = true)
    String employee_id;
    @ApiModelProperty(value = "企业ID",hidden = true)
    String enterprise_id;
}
