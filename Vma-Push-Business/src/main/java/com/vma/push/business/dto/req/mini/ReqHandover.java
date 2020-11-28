package com.vma.push.business.dto.req.mini;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lql
 * @Description: TODO
 * @date 2018-10-24 17:04
 */
@Data
public class ReqHandover {
	@ApiModelProperty(value = "交接人staffId", required = true)
	private String handoverStaffId;
	@ApiModelProperty(value = "离职人staffId", required = true)
	private String leaveStaffId;
}
