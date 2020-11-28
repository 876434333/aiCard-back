package com.vma.push.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AttachmentImage {

    @ApiModelProperty(value = "id", required = true)
    private String id;

    @ApiModelProperty(value = "url", required = true)
    private String url;

    @ApiModelProperty(value = "state", required = true)
    private String state;

    @ApiModelProperty(value = "creator_id", required = true)
    private String creator_id;
}
