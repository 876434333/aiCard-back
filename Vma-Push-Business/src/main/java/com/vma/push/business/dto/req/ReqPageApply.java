package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by chenzui on 2018/6/4.
 */
@Data
public class ReqPageApply {
    @ApiModelProperty(value = "单前页",required = true)
    Integer page_num;
    @ApiModelProperty(value = "每页记录数",required = true)
    Integer page_size;

    Integer type;
}
