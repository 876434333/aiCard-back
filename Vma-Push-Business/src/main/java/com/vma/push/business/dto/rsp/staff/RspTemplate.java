package com.vma.push.business.dto.rsp.staff;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RspTemplate {
    @ApiModelProperty(value = "名片模板ID")
    Integer temlate_id;
}
