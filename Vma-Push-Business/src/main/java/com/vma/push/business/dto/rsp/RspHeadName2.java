package com.vma.push.business.dto.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by huxiangqiang on 2018/7/9.
 */
@Data
public class RspHeadName2 {
    @ApiModelProperty(value = "头像")
    private String head_icon;
    @ApiModelProperty(value = "姓名")
    private String nick_name;
    @ApiModelProperty(value = "id")
    private String id;

}
