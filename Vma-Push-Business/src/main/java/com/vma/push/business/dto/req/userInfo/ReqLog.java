package com.vma.push.business.dto.req.userInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by chenzui on 2018/5/14.
 */
@Data
public class ReqLog {


    @ApiModelProperty(value = "用户id",required = true)
    String user_id;


    @ApiModelProperty(value = "销售人员id")
    String staff_id;

    @ApiModelProperty(value = "企业id",hidden = true)
    String enterprise_id;

    @ApiModelProperty(value = "单前页",required = true)
    Integer page_num;

    @ApiModelProperty(value = "数量",required = true)
    Integer page_size;

    @ApiModelProperty(value = "类型 1互动与跟进 0跟进",required = true)
    Integer type;
}
