package com.vma.push.business.dto.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by JINzm on 2018/5/14.
 */
@Data
public class RspCircleCommentFromCompany {
    @ApiModelProperty(value = "销售人员姓名")
    String staff_name;
    @ApiModelProperty(value = "评论内容")
    String comment_content;
    @ApiModelProperty(value = "动态内容")
    String circle_content;
    @ApiModelProperty(value = "封面图")
    String cover;
    @ApiModelProperty(value = "用户ID")
    String user_id;
    @ApiModelProperty(value = "用户头像")
    String head_icon;
    @ApiModelProperty(value = "用户姓名")
    String user_name;
    @ApiModelProperty(value = "销售人员岗位")
    String station;
    @ApiModelProperty(value = "评论时间")
    Date create_time;
    @ApiModelProperty(value = "评论ID")
    String id;
}
