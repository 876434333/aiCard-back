package com.vma.push.business.dto.rsp.userInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by chenzui on 2018/5/14.
 */
@Data
public class RspAnalysisData {

    @ApiModelProperty(value = "饼图数据")
    List<DataItem> pie;
    @ApiModelProperty(value = "柱状图数据")
    List<DataItem> bar;
    @ApiModelProperty(value = "折线图数据")
    List<DataItem> line;
}
