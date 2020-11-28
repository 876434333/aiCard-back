package com.vma.push.business.dto.rsp.userInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by chenzui on 2018/5/14.
 */
@Data
public class RspUserLabelInfo {

    @ApiModelProperty(value = "员工ID")
    String staffId;
    @ApiModelProperty(value = "用户ID")
    String userId;
    @ApiModelProperty(value = "标签ID")
    String realId;
    @ApiModelProperty(value = "用户标签映射ID")
    String label_id;
    @ApiModelProperty(value = "标签名字")
    String label_name;
    @ApiModelProperty(value = "1选中0为选中")
    String is_target;
    @ApiModelProperty(value = "是否为参考标签，显示参考标签列表时需要")
    String is_refer;
    @ApiModelProperty(value = "标签类型")
    Integer type;
    @ApiModelProperty(value = "增量状态")
    String state;
}
