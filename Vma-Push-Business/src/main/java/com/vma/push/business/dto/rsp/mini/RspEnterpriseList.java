package com.vma.push.business.dto.rsp.mini;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lql
 * @Description: TODO
 * @date 2018-10-24 14:48
 */
@Data
public class RspEnterpriseList {
	@ApiModelProperty(value = "企业id")
	private String id;
	@ApiModelProperty(value = "企业名字")
	private String name;
	@ApiModelProperty(value = "企业初始名片数量")
	private Integer money_init;
	@ApiModelProperty(value = "企业剩余名片数量")
	private Integer sale_card_num;
	@ApiModelProperty(value = "是否当前企业")
	private String head_icon;
	@ApiModelProperty(value = "是否当前企业")
	private Integer is_curent;
	@ApiModelProperty(value = "用户在当前企业所在的身份")
	private Integer role;
}
