package com.vma.push.business.dto.rsp.mini;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lql
 * @Description: TODO
 * @date 2018-10-23 17:06
 */
@Data
public class RepMyCardInfo {
	@ApiModelProperty(value = "微信头像")
	private String headIcon;
	@ApiModelProperty(value = "微信昵称")
	private String nickName;
	@ApiModelProperty(value = "有效名片数量")
	private Integer effectiveCardNum;
	@ApiModelProperty(value = "总名片数量")
	private Integer totalCardNum;
}
