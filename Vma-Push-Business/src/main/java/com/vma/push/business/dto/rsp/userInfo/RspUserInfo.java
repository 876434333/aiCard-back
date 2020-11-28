package com.vma.push.business.dto.rsp.userInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by ChenXiaoBin
 * 2018/5/19 13:41
 */
@Data
public class RspUserInfo {
    @ApiModelProperty(value = "用户id")
    private String id;
    @ApiModelProperty(value = "sig")
    private String sig;
    private String sdk_id;
    private String acount_type;
}
