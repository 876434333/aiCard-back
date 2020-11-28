package com.vma.push.business.dto.rsp.mini;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lql
 * @Description: TODO
 * @date 2018-10-23 17:06
 */
@Data
public class RepEnterprise {
	@ApiModelProperty(value = "企业Id")
	private String enterpriseId;
	@ApiModelProperty(value = "部门Id")
	private String departmentId;
}
