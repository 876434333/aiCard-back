package com.vma.push.business.dto.rsp.mini;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author lql
 * @Description: TODO
 * @date 2018-10-24 14:48
 */
@Data
public class RspApproveList {
	@ApiModelProperty(value = "已批准人员列表")
	List<ResDepartmentPerson> approveStaffList;
	@ApiModelProperty(value = "待批准人员列表")
	List<ResDepartmentPerson> pendingStaffList;
}
