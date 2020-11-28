package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by JINzm
 * 2018/8:16
 */
@Data
public class ReqSendMsg {
    @ApiModelProperty(value = "销售人员ID",required = true)
    String staffId;
}
