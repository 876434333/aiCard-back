package com.vma.push.business.dto.req.userInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by huangjb on 2018/2/27.
 */
@Data
public class ReqQueryUserInfo  {
	
	
	@ApiModelProperty(value = "当前分页数")
    Integer page_num;
    @ApiModelProperty(value = "分页大小")
    Integer page_size;
    @ApiModelProperty(value = "1扫码2分享3工作交接4预计成交率5最后跟进时间6最后活动时间 ")
    Integer type;
    @ApiModelProperty(hidden = true)
    String staff_id;
	
	 
	//columns END

 
	 
}

 

 
