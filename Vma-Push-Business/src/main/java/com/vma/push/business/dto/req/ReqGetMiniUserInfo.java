package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by huxiangqiang on 2018/6/28.
 */
@Data
public class ReqGetMiniUserInfo {
    @ApiModelProperty(value = "open_id",required = true)
    private String open_id;

    @ApiModelProperty(value = "encrypted_data",required = true)
    private String encrypted_data;

    @ApiModelProperty(value = "iv",required = true)
    private String  iv;
}
