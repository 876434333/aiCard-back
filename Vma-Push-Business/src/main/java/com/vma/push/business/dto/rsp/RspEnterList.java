package com.vma.push.business.dto.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by huxiangqiang on 2018/6/14.
 */
@Data
public class RspEnterList {
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "剩余迹点")
    private Integer money_leave;

    @ApiModelProperty(value = "企业状态")
    private Integer status;

    private Integer is_power;

    private String parent_id;

}
