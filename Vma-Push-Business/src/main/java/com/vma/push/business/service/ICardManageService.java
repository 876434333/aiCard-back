package com.vma.push.business.service;

import com.vma.push.business.dto.Website;
import com.vma.push.business.dto.WebsiteTemplate;
import com.vma.push.business.dto.req.Page;
import com.vma.push.business.dto.req.mini.*;
import com.vma.push.business.dto.req.superback.ReqAddEnterprise;
import com.vma.push.business.dto.rsp.RspPage;
import com.vma.push.business.dto.rsp.mini.*;
import com.vma.push.business.entity.Enterprise;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import java.util.Map;

/**
 * @author lql
 * @Description: TODO
 * @date 2018-10-23 14:24
 */
public interface ICardManageService {
	// 发送短信验证码
	String sendMsg(String phone);

	// 验证短信验证码否正确
	String checkMsg(String phone, String msgCode);

	// 修改名片基本信息
	String updataCardBaseInfo(ReqCardBaseInfo reqCard);

	// 获取企业列表
	List<RspEnterpriseList> getEnterpriseList(String openid);

	// 解密手机号
	String getPhone(ReqGetPhone reqGetPhone,HttpServletRequest request);

	// 解散企业
	int dissolveCompany(String enterpriseId,HttpServletRequest request);

	// 获取当前企业
	Enterprise getCurrentEnterpriseInfo(String enterpriseId);

	// 获取部门列表
	List<RepDepartmentList> getDepartmentList(String openid, String parentId);

	// 切换企业
	void switchCompany(String enterpriseId, String openid, HttpServletRequest request);

	// 获取部门人员列表
	List<ResDepartmentPerson> getDepartmentPerson(String enterpriseId, String parentId, String status);
	List<ResDepartmentPerson> getDepartmentPersonByName(String enterpriseId, String name);
	// 新增部门
	String addDepartment(ReqAddDepartment reqAddDepartment);
	// 删除部门
	void deleteDepartment(ReqAddDepartment reqdeleteDepartment);
	// 更新部门'
	void updataDepartment(ReqAddDepartment reqUpdataDepartment);
	// 修改公司名字和logo
	void updataCompanyNameOrLogo(ReqCompanyNameLogo companyNameLogo);
	// 获取用户身份
	String getUserRelo(String enterpriseId,String openid);
	boolean addVideo(Map<String, Object> map);

	Map<String, Object> getVideo(String staffId);

	boolean updCardStyle(Map<String, Object> map);

	Map<String, Object> getCardStyle(String staffId);

	boolean updShareSetup(Map<String, Object> map);

	// 修改部门人员信息
	void updataDptPerson(ReqUpdataDptPerson reqUpdataDptPerson);
	// 工作交接
	void handover(ReqHandover reqHandover);
	// 复职
	void reentry(String staffId);
	// 获取管理者和运营者列表
	List<ResDepartmentPerson> getManager(String enterpriseId,String role);
	// 转让管理员
	void transferManager(ReqHandover reqHandover);
	// 增加运营者
	void addOperator(String staffId);
	// 移除运营者
	void removeOperator(ReqRemoveOperators operatorList);
	// 手动添加员工
	void addDptStaff(ReqUpdataDptPerson reqUpdataDptPerson);
	// 卡片启用
	Integer cardOn(String relaId);

	// 卡片禁用
	Integer cardOff(String relaId);
	//  通过分享添加员工
	void addStaffByShare(ReqAddStaffByShare reqAddStaffByShare);

	// 获取认领界面的信息
	ResClaimInfo getClaimInfo(String staffId);

	// 通过分享认领员工
	void claimStaffByShare(ReqAddStaffByShare reqAddStaffByShare);

	boolean addWebsite(Website website);

	List<Website> getWebsiteById(String enter);

	List<WebsiteTemplate> getWebsiteComponentById(String id);

	boolean updWebsiteComponent(List<WebsiteTemplate> list, String id);

	// 获取相同名字的公司列表
	List<ResGetSameCompanyNameList> getSameCompanyNameList(String enterpriseName);
	// 用户通过搜索公司名字添加员工
	void addStaffBySrearch(ReqAddEnterprise reqEnterList);

	// 获取申请和已申请的人员列表
	RspApproveList getApproveList(String enterpriseId);
	// 审核申请人是否加入企业
	void checkApprove(ReqCheckApprove approveList);
	// 员工退出企业获取staffId
	String getStaffId(String enterprise,String openid);

	List<RepMyselfCardList>  getMyselfCardList(String openid,HttpServletRequest request);
	RepMyCardInfo  getMyselfCardInfo(String openid);
    int getPendingPerson(String enterpriseId);
	boolean deleteResources(String resourceName);
	RepEnterprise getEnterprise(String staffId);
	Integer isExitMoreStaff(String enterpriseId);
	Integer updateCardImgUrl(String staffId, String url);
	RepRadarPermissions getRadarPermission(String staffId);
	RspPage<RepRecommendCard> getRecommendCardList(Page page, String staffId);
}
