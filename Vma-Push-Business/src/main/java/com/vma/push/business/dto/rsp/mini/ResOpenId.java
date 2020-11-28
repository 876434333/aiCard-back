package com.vma.push.business.dto.rsp.mini;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lql
 * @Description: TODO
 * @date 2018-10-28 16:06
 */
@Data
public class ResOpenId {
	@ApiModelProperty(value = "openid")
	private String openid;
	@ApiModelProperty(value = "是否是老用户")
	private String status;
	@ApiModelProperty(value = "昵称")
	private String nickName;
	@ApiModelProperty(value = "头像")
	private String headIcon;
	@ApiModelProperty(value = "企业名字")
	private String enterpriseName;

}