package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by huxiangqiang on 2018/5/31.
 */
@Data
public class RepOpenId {

    @ApiModelProperty(value = "企业id",required = true)
    private String enterprise_id;
    @ApiModelProperty(value = "微信code",required = true)
    private String code;
    @ApiModelProperty(value = "员工id",required = true)
    private String staff_id;
}
