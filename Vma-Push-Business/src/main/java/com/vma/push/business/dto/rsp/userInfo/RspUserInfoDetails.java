package com.vma.push.business.dto.rsp.userInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by huangjb on 2018/2/27.
 */
@Data
public class RspUserInfoDetails {
	
	
	//columns START
    /**
     * 主键       db_column: id 
     */ 	
	@ApiModelProperty(value = "主键 ")
	private String id;
	
    /**
     * 微信开放ID       db_column: open_id 
     */ 	
	@ApiModelProperty(value = "微信开放ID ")
	private String open_id;
	
    /**
     * 客户归属企业       db_column: enterprise_id 
     */ 	
	@ApiModelProperty(value = "客户归属企业 ")
	private String enterprise_id;
	
    /**
     * 用户对应企业的销售人员ID       db_column: employee_id 
     */ 	
	@ApiModelProperty(value = "用户对应企业的销售人员ID ")
	private String employee_id;
	
    /**
     * 创建时间       db_column: create_time 
     */ 	
	@ApiModelProperty(value = "创建时间 ")
	private java.util.Date create_time;
	
    /**
     * 修改时间       db_column: modify_time 
     */ 	
	@ApiModelProperty(value = "修改时间 ")
	private java.util.Date modify_time;
	
    /**
     * 微信小程序ID       db_column: wx_soft_id 
     */ 	
	@ApiModelProperty(value = "微信小程序ID ")
	private String wx_soft_id;
	
    /**
     * 头像       db_column: head_icon 
     */ 	
	@ApiModelProperty(value = "头像 ")
	private String head_icon;
	
    /**
     * 昵称       db_column: nick_name 
     */ 	
	@ApiModelProperty(value = "昵称 ")
	private String nick_name;
	
    /**
     * sex       db_column: sex 
     */ 	
	@ApiModelProperty(value = "sex ")
	private Integer sex;
	
	
	 
	//columns END

 
	 
}

 

 
