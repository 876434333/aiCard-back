package com.vma.push.business.dto.req.mini;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lql
 * @Description: TODO
 * @date 2018-10-24 17:04
 */
@Data
public class ReqGetPhone {
	@ApiModelProperty(value = "openid", required = true)
	private String openid;
	@ApiModelProperty(value = "iv(向量)", required = true)
	private String iv;
	@ApiModelProperty(value = "电话号码密文", required = true)
	private String phone;
}
