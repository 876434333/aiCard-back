package com.vma.push.business.dto.req.staff;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.omg.CORBA.PRIVATE_MEMBER;

/**
 * Created by ChenXiaoBin
 * 2018/5/21 0:00
 */
@Data
public class ReqCarAction {
    @ApiModelProperty(value = "员工id")
    private String staff_id;
    @ApiModelProperty(value = "open_id")
    private String open_id;
    @ApiModelProperty(value = "状态")
    private Integer status;
    @ApiModelProperty(value = "状态",hidden = true)
    private String user_id;
}
