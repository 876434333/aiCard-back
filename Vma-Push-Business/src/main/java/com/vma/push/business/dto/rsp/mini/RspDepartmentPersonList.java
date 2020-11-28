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
public class RspDepartmentPersonList {
	@ApiModelProperty(value = "离职人员列表")
	List<ResDepartmentPerson> departurePersonList;
	@ApiModelProperty(value = "在职人员列表")
	List<ResDepartmentPerson> onJobPersonList;
    @ApiModelProperty(value = "未认领人员列表")
	List<ResDepartmentPerson> temporaryPersonList;
}
