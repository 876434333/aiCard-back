package com.vma.push.business.dto.req.mini;

import com.vma.push.business.entity.Staff;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lql
 * @Description: 名片基本信息
 * @date 2018-10-23 16:56
 */
@Data
public class ReqCardBaseInfo {
	@ApiModelProperty(value = "openid", required = true)
	private String openid;
	@ApiModelProperty(value = "staffId", required = true)
	private String staffId;
	@ApiModelProperty(value = "姓名", required = true)
	private String name;
	@ApiModelProperty(value = "电话", required = true)
	private String phone;
	@ApiModelProperty(value = "公司", required = true)
	private String company_name;
	@ApiModelProperty(value = "职位", required = true)
	private String station;
	@ApiModelProperty(value = "微信号", required = true)
	private String weixin;
	@ApiModelProperty(value = "邮箱", required = true)
	private String mail;
	@ApiModelProperty(value = "地址", required = true)
	private String address;
	public Staff toStaff(){
		Staff staff=new Staff();
//		staff.setId(this.staff_id);
		staff.setName(this.name);
		staff.setPhone(this.phone);
		staff.setStation(this.station);
		staff.setWeixin(this.weixin);
		staff.setMail(this.mail);
		staff.setAddress(this.address);
		return staff;
	}
}
