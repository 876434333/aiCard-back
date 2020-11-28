package com.vma.push.business.dto.rsp.mini;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lql
 * @Description: TODO
 * @date 2018-10-23 17:06
 */
@Data
public class RepRecommendCard {
	@ApiModelProperty(value = "staffId")
	private String staffId;
	@ApiModelProperty(value = "enterpriseId")
	private String enterpriseId;
	@ApiModelProperty(value = "部门ID")
	private String departmentId;
	@ApiModelProperty(value = "头像")
	private String headIcon;
	@ApiModelProperty(value = "名字")
	private String name;
	@ApiModelProperty(value = "公司名字")
	private String enterpriseName;
}
