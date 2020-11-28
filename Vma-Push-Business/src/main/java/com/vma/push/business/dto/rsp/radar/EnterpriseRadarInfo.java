package com.vma.push.business.dto.rsp.radar;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by chenzui on 2018/5/11.
 */
@Data
public class EnterpriseRadarInfo {

    @ApiModelProperty(value = "总共")
    Integer total;
    @ApiModelProperty(value = "已用")
    Integer used;
}
