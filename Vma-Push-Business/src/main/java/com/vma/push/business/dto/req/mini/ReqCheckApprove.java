package com.vma.push.business.dto.req.mini;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author lql
 * @Description: TODO
 * @date 2018-10-24 17:04
 */
@Data
public class ReqCheckApprove {
	@ApiModelProperty(value = "申请人staffId列表", required = true)
	List<String> staffIdList;
	@ApiModelProperty(value = "处理结果1同意、0拒绝", required = true)
	String result;
	@ApiModelProperty(value = "审核人StaffId", required = true)
	String handleStaffId;
}
