package com.vma.push.business.dto.rsp.userInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by chenzui on 2018/5/14.
 */
@Data
public class RspLabelType {

    @ApiModelProperty(value = "类别ID")
    String id;

    @ApiModelProperty(value = "类别名称")
    String type_name;

    @ApiModelProperty(value = "标签数组")
    List<RspUserLabelInfo> labelInfo;
}
