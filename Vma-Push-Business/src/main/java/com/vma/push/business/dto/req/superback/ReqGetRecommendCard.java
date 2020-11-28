package com.vma.push.business.dto.req.superback;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * created by linzh on 2018/6/15
 */
@Data
public class ReqGetRecommendCard {
    @ApiModelProperty(value = "公司名字")
    private String enterprise_name;

    @ApiModelProperty(value = "员工手机号")
    private String phone;
}
