package com.vma.push.business.dto.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by huxiangqiang on 2018/5/24.
 */
@Data
public class RspMiniInfo {
private String token;
private String user_id;
@ApiModelProperty(value = "员工id")
private String staff_id;
@ApiModelProperty(value = "企业id")
private String enterprise_id;
@ApiModelProperty(value = "部门id")
private String department_id;
@ApiModelProperty(value = "是否第一次 1是 0不是")
private Integer is_first;

}
