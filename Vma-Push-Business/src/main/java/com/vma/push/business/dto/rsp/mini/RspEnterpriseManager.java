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
public class RspEnterpriseManager {
	@ApiModelProperty(value = "管理员列表")
	List<ResDepartmentPerson> managerList;
	@ApiModelProperty(value = "运营者列表")
	List<ResDepartmentPerson> operatorsList;

}
