package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by ChenXiaoBin
 * 2018/5/4 14:02
 */
@Data
public class ReqStaffSelect {
    @ApiModelProperty(value = "单前页",required = true)
    int page_num;
    @ApiModelProperty(value = "每页记录数",required = true)
    int page_size;
    @ApiModelProperty(value = "姓名",required = true)
    String name;

    @ApiModelProperty(value = "企业id",hidden = true)
    String enterprise_id;
}
