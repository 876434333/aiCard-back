package com.vma.push.business.dto.rsp.ai;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CompanyList {

    
	@ApiModelProperty(value = "enterpriseId")
	String enterpriseId;
	@ApiModelProperty(value = "公司名字")
	String name;
	@ApiModelProperty(value = "公司logo")
	String logo;
}
