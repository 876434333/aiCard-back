package com.vma.push.business.dto;

import com.vma.push.business.dto.rsp.userInfo.RspUserLabelInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CardResumeInfo {

    @ApiModelProperty(value = "人员ID",required = true)
    private String staffId;

    @ApiModelProperty(value = "用户ID",required = true)
    private String userId;

    @ApiModelProperty(value = "我的简介",required = true)
    private String signature;

    @ApiModelProperty(value = "我的标签",required = true)
    private List<RspUserLabelInfo> labels;

    @ApiModelProperty(value = "我的照片",required = true)
    private List<AttachmentImage> images;

    @ApiModelProperty(value = "我的录音",required = true)
    private AttachmentAudio audio;
}
