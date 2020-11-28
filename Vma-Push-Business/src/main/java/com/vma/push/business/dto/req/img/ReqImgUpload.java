package com.vma.push.business.dto.req.img;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by chenzui on 2018/5/15.
 */
@Data
public class ReqImgUpload {

    @ApiModelProperty(value = "base64字符流")
    String imgdata;
}
