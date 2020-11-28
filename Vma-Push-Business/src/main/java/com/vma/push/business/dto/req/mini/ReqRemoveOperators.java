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
public class ReqRemoveOperators {
	@ApiModelProperty(value = "运营者列表", required = true)
	List<String> operatorList;
}
