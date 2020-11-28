package com.vma.push.business.dto.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by huxiangqiang on 2018/6/22.
 */
@Data
public class RspTemplate {
    @ApiModelProperty(value = "id")
    private String code;
    @ApiModelProperty(value = "模板名称")
    private String template_name;
}
