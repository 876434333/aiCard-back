package com.vma.push.business.dto.req.mini;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lql
 * @Description: TODO
 * @date 2018-10-24 17:04
 */
@Data
public class ReqUpdataDptPerson {
	@ApiModelProperty(value = "用户id", required = true)
	private String id;
	@ApiModelProperty(value = "员工名字", required = true)
	private String name;
	@ApiModelProperty(value = "员工电话", required = true)
	private String phone;
	@ApiModelProperty(value = "员工职位", required = true)
	private String station;
	@ApiModelProperty(value = "员工部门", required = true)
	private String department_id;
	@ApiModelProperty(value = "企业id", required = true)
	private String enterprise_id;
	@ApiModelProperty(value = "角色", required = true)
	private String role;
}
