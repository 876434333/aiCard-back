package com.vma.push.business.dto.rsp.mini;

import com.vma.push.business.dto.rsp.staff.RspCardInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lql
 * @Description: TODO
 * @date 2018-10-23 17:06
 */
@Data
public class RepMyselfCardList {
	@ApiModelProperty(value = "staffId")
	private String id;
	@ApiModelProperty(value = "公司名字")
	private String enterprise_name;
	@ApiModelProperty(value = "名片头像")
	private String head_icon;
	@ApiModelProperty(value = "姓名")
	private String name;
	@ApiModelProperty(value = "手机号")
	private String phone;
	@ApiModelProperty(value = "创建时间")
	private String create_time;
	@ApiModelProperty(value = "创建方式")
	private Integer froms;
	@ApiModelProperty(value = "企业id")
	private String enterprise_id;
	@ApiModelProperty(value = "部门Id")
	private Integer department_id;
	@ApiModelProperty(value = "职位")
	private String station;
	@ApiModelProperty(value = "企业状态")
	private Integer enterprise_status;
	@ApiModelProperty(value = "员工状态")
	private Integer staff_status;
	@ApiModelProperty(value = "企业解散时间")
	private String enterprise_modify_time;
	@ApiModelProperty(value = "离职时间")
	private String dimission_time;

	@ApiModelProperty(value = "地址")
	private String address;
	@ApiModelProperty(value = "名片模板id")
	private Integer templateId;
	@ApiModelProperty(value = "邮箱")
	private String mail;
	@ApiModelProperty(value = "分享设置")
	private String share_setup;
//	@ApiModelProperty(value = "staffInfo")
//	private RspCardInfo staffInfo;
}
