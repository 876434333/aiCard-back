package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by huxiangqiang on 2018/6/5.
 */
@Data
public class RspGetSig {
    @ApiModelProperty(value = "企业id")
    private String enterparise_id;
    @ApiModelProperty(value = "公司的CropID")
    private String staff_id;
    @ApiModelProperty(value = "open_id")
    private String open_id;
}
