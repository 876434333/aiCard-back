package com.vma.push.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AttachmentAudio {

    @ApiModelProperty(value = "id", required = true)
    private String id;

    @ApiModelProperty(value = "url", required = true)
    private String url;

    @ApiModelProperty(value = "duration", required = true)
    private Integer duration;

    @ApiModelProperty(value = "creator_id", required = true)
    private String creator_id;
}
