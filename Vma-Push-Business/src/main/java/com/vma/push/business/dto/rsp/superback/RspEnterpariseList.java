package com.vma.push.business.dto.rsp.superback;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by huxiangqiang on 2018/6/20.
 */
@Data
public class RspEnterpariseList {
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "模板名称")
    private String template_name;

    @ApiModelProperty(value = "是否禁用")
    private Integer status;

    @ApiModelProperty(value = "是否禁用")
    private Integer is_deploy;
}
