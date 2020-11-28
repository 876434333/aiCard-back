package com.vma.push.business.dto.req.mini;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lql
 * @Description: TODO
 * @date 2018-10-24 17:04
 */
@Data
public class ReqCompanyNameLogo {
	@ApiModelProperty(value = "企业Id", required = true)
	private String enterpriseId;
	@ApiModelProperty(value = "公司名字", required = true)
	private String enterpriseName;
	@ApiModelProperty(value = "图标Url", required = true)
	private String iconUrl;
}
