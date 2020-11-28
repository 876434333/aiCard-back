package com.vma.push.business.dto.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by ChenXiaoBin
 * 2018/5/17 12:42
 */
@Data
public class RspAgent {
    @ApiModelProperty(value = "代理商")
    String agent;//代理商
}
