package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by huxiangqiang on 2018/6/5.
 */
@Data
public class ReqUserFormAdd {
    @ApiModelProperty(value = "id",hidden = true)
    private String id;

    @ApiModelProperty(value = "userId")
    private String userId;

    @ApiModelProperty(value = "formid")
    private String formId;

    @ApiModelProperty(value = "times")
    private Date times;
}
