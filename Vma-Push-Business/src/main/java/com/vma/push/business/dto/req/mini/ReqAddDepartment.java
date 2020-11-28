package com.vma.push.business.dto.req.mini;

import com.vma.push.business.entity.Staff;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lql
 * @Description: 名片基本信息
 * @date 2018-10-23 16:56
 */
@Data
public class ReqAddDepartment {
	@ApiModelProperty(value = "企业id", required = true)
	private String enterpriseId;
	@ApiModelProperty(value = "部门名字", required = true)
	private String departmentName;
	@ApiModelProperty(value = "父部门id", required = true)
	private String parentId;
}
