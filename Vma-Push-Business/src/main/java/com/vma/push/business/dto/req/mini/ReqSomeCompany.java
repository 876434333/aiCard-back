package com.vma.push.business.dto.req.mini;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lql
 * @Description: 名片基本信息
 * @date 2018-10-23 16:56
 */
@Data
public class ReqSomeCompany {
	@ApiModelProperty(value = "企业名字", required = true)
	private String enterpriseName;
}
