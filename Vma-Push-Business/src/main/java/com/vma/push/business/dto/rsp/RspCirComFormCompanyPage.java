package com.vma.push.business.dto.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
public class RspCirComFormCompanyPage {
    @ApiModelProperty(value = "数据")
    List<RspCircleCommentFromCompany> data_list;
    @ApiModelProperty(value = "总记录数")
    Long total_num;
    @ApiModelProperty(value = "当前分页数")
    Integer page_num;
    @ApiModelProperty(value = "分页大小")
    Integer page_size;
}
