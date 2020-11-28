package com.vma.push.business.dto.rsp;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RspStaffId {
    //Staff.id
    private String staff_id;
    //Staff.wx_id
    private String wx_id;
}
