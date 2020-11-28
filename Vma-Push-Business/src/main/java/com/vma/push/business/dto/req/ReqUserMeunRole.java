package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by huxiangqiang on 2018/6/22.
 */
@Data
public class ReqUserMeunRole {

    @ApiModelProperty(value = "用户id")
    private String user_id;
    @ApiModelProperty(value = "权限类型")
    private Integer role;
    @ApiModelProperty(value = "上级id",hidden = true)
    private String parent_id;
}
