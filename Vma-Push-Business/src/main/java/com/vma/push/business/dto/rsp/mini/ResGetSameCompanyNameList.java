package com.vma.push.business.dto.rsp.mini;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lql
 * @Description: TODO
 * @date 2018-10-28 16:06
 */
@Data
public class ResGetSameCompanyNameList {
	@ApiModelProperty(value = "id")
	private String id;
	@ApiModelProperty(value = "公司名字")
	private String name;
	@ApiModelProperty(value = "创始人名字")
	private String managerName;
	@ApiModelProperty(value = "名片数量")
	private String saleCardNum;
}