package com.vma.push.business.dto.req.superback;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * created by linzh on 2018/6/15
 */
@Data
public class ReqAddPoint {
    @ApiModelProperty(value = "贴牌商id")
    String custom_id;

    @ApiModelProperty(value = "事项")
    String content;

    @ApiModelProperty(value = "操作（1 增加/2 扣减）")
    String operation;

    @ApiModelProperty(value = "操作迹点数")
    Float operation_point;

    @ApiModelProperty(value = "操作人",hidden = true)
    String create_by;

    @ApiModelProperty(value = "企业ID",hidden = true)
    String Enterprise_id;

}
