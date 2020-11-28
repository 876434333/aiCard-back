package com.vma.push.business.dto.req.mini;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lql
 * @Description: TODO
 * @date 2018-10-24 17:04
 */
@Data
public class ReqSearch {
	@ApiModelProperty(value = "搜索内容", required = true)
	private String searchComment;
}
