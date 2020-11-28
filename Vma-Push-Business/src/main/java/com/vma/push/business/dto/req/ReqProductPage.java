package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by huxiangqiang on 2018/5/16.
 */
@Data
public class ReqProductPage {

    @ApiModelProperty(value = "单前页",required = true)
    int page_num;
    @ApiModelProperty(value = "每页记录数",required = true)
    int page_size;

    @ApiModelProperty(value = "企业id",hidden = true)
    String  enterprise_id;

    @ApiModelProperty(value = "用户id",hidden = true)
    String  user_id;

}
