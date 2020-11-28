package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by huxiangqiang on 2018/6/13.
 */
@Data
public class ReqfollowUser {
    @ApiModelProperty(value = "用户id",required = true)
    private String user_id;
    @ApiModelProperty(value = "内容",required = true)
    private String content;
}
