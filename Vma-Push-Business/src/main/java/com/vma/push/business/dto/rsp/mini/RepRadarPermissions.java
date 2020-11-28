package com.vma.push.business.dto.rsp.mini;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lql
 * @Description: TODO
 * @date 2018-10-23 17:06
 */
@Data
public class RepRadarPermissions {
	@ApiModelProperty(value = "ai雷达权限")
	private Integer open_ai;
	@ApiModelProperty(value = "boss雷达权限")
	private Integer open_boss;
}
