package com.vma.push.business.dto.rsp.ai;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class repChoseCompany {

	@ApiModelProperty(value = "wx_id")
	String wx_id;
	@ApiModelProperty(value = "公司列表")
	List<CompanyList> companyList;
}
