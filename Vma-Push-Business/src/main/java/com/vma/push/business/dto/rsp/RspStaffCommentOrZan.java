package com.vma.push.business.dto.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RspStaffCommentOrZan {
    @ApiModelProperty(value = "内容")
    private String content;
    @ApiModelProperty(value = "员工昵称")
    private String name;
    @ApiModelProperty(value = "评论/点赞记录ID")
    private String id;
}
