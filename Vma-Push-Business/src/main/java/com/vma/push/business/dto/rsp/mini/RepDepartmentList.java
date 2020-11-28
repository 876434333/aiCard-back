package com.vma.push.business.dto.rsp.mini;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lql
 * @Description: TODO
 * @date 2018-10-23 17:06
 */
@Data
public class RepDepartmentList {
	@ApiModelProperty(value = "部门id")
	private Integer id;
	@ApiModelProperty(value = "部门名字")
	private String name;
	@ApiModelProperty(value = "部门人数")
	private String num;
}
