package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by huxiangqiang on 2018/6/5.
 */
@Data
public class RepImInfo {
    @ApiModelProperty(value = "用户id")
    private String id;
    @ApiModelProperty(value = "昵称")
    private String nick_name;
}
