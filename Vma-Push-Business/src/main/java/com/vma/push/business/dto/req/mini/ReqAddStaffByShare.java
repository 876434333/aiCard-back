package com.vma.push.business.dto.req.mini;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lql
 * @Description: TODO
 * @date 2018-10-24 17:04
 */
@Data
public class ReqAddStaffByShare {
	@ApiModelProperty(value = "公司id", required = true)
	private String enterpriseId;
	@ApiModelProperty(value = "部门id", required = true)
	private String departmentId;
	@ApiModelProperty(value = "加入人的openid", required = true)
	private String openid;
	@ApiModelProperty(value = "头像Url", required = true)
	private String avatarUrl;
	@ApiModelProperty(value = "性别", required = true)
	private String sex;
	@ApiModelProperty(value = "昵称", required = true)
	private String nickName;
	@ApiModelProperty(value = "手机号", required = true)
	private String phone;
	@ApiModelProperty(value = "分享人的staffid", required = true)
	private String staffId;
	@ApiModelProperty(value = "被邀请人的staffid", required = true)
	private String InviteStaffId;

}
