package com.vma.push.business.dto.req.userInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by huangjb on 2018/2/27.
 */
@Data
public class ReqUpdateUserInfo  {
	
	
	//columns START
    /**
     * 主键        
     */ 	
	@ApiModelProperty(value = "主键 ",required = true)
	private String id;
	
    /**
     * 微信开放ID        
     */ 	
	@ApiModelProperty(value = "微信开放ID ",required = true)
	private String open_id;
	
    /**
     * 客户归属企业        
     */ 	
	@ApiModelProperty(value = "客户归属企业 ",required = true)
	private String enterprise_id;
	
    /**
     * 用户对应企业的销售人员ID        
     */ 	
	@ApiModelProperty(value = "用户对应企业的销售人员ID ",required = true)
	private String employee_id;
	
    /**
     * 创建时间        
     */ 	
	@ApiModelProperty(value = "创建时间 ",required = true)
	private java.util.Date create_time;
	
    /**
     * 修改时间        
     */ 	
	@ApiModelProperty(value = "修改时间 ",required = true)
	private java.util.Date modify_time;
	
    /**
     * 微信小程序ID        
     */ 	
	@ApiModelProperty(value = "微信小程序ID ",required = true)
	private String wx_soft_id;
	
    /**
     * 头像        
     */ 	
	@ApiModelProperty(value = "头像 ",required = true)
	private String head_icon;
	
    /**
     * 昵称        
     */ 	
	@ApiModelProperty(value = "昵称 ",required = true)
	private String nick_name;
	
    /**
     * sex        
     */ 	
	@ApiModelProperty(value = "sex ",required = true)
	private Integer sex;
	
	
	 
	//columns END

 
	 
}

 

 
