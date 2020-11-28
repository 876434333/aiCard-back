package com.vma.push.business.dto.req.enterprise;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReqUpdateFileDirOrder {
    @ApiModelProperty(value = "目录id")
    private String id;
    @ApiModelProperty(value = "1上移、0下移")
    private int is_move_up;
}
