package com.vma.push.business.dto.rsp.mini;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lql
 * @Description: TODO
 * @date 2018-10-28 16:06
 */
@Data
public class ResDepartmentPerson{
	@ApiModelProperty(value = "员工ID")
	private String id;
	@ApiModelProperty(value = "名字")
	private String name;
	@ApiModelProperty(value = "职位")
	private String station;
	@ApiModelProperty(value = "头像")
	private String head_icon;
	@ApiModelProperty(value = "手机号")
	private String phone;
	@ApiModelProperty(value = "身份")
	private String role;
	@ApiModelProperty(value = "名字首字母")
	private String first_letter;
	@ApiModelProperty(value = "名片状态")
	private Integer status;
}