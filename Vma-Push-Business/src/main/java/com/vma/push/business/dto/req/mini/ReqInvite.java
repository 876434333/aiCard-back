package com.vma.push.business.dto.req.mini;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lql
 * @Description: TODO
 * @date 2018-10-24 17:04
 */
@Data
public class ReqInvite {
	@ApiModelProperty(value = "邀请人staffId", required = true)
	private String inviteId;
	@ApiModelProperty(value = "公司id", required = true)
	private String enterpriseId;
}
