package com.vma.push.business.dto.rsp.mini;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author lql
 * @Description: TODO
 * @date 2018-10-28 16:06
 */
@Data
public class ResInvite {
	@ApiModelProperty(value = "邀请人名字")
	private String inviteName;
	@ApiModelProperty(value = "邀请人名字")
	private String inviteHeadIcon;
	@ApiModelProperty(value = "公司名字")
	private String enterpriseName;
	@ApiModelProperty(value = "公司创建人名字")
	private String createName;
	@ApiModelProperty(value = "公司员工头像")
	private List<String> headIconList;
}