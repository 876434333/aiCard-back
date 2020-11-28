package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by huxiangqiang on 2018/5/30.
 */
@Data
public class ReqAddIntro {
    @ApiModelProperty(value = "类型",hidden = true)
    String type;

    @ApiModelProperty(value = "路径")
    String url;

    @ApiModelProperty(value = "员工id",hidden = true)
    String staff_id;
    @ApiModelProperty(value = "企业id",hidden = true)
    String enterprise_id;

    @ApiModelProperty(value = "id",hidden = true)
    String id;

    @ApiModelProperty(value = "order")
    Integer order;

    @ApiModelProperty(value = "时间",hidden = true)
    Date create_time;

}
