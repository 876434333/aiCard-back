package com.vma.push.business.dao;

import com.vma.push.business.dto.*;
import com.vma.push.business.dto.req.ReqAddIntro;
import com.vma.push.business.dto.req.ReqStaffSelect;
import com.vma.push.business.dto.req.ReqSystemLogin;
import com.vma.push.business.dto.req.ReqUserAndStaff;
import com.vma.push.business.dto.req.staff.ReqCardNumber;
import com.vma.push.business.dto.req.staff.ReqCustomList;
import com.vma.push.business.dto.req.staff.ReqStaffOrder;
import com.vma.push.business.dto.req.store.ReqDiscount;
import com.vma.push.business.dto.req.store.ReqEnterDiscount;
import com.vma.push.business.dto.rsp.*;
import com.vma.push.business.dto.rsp.boss.RspUserInfo;
import com.vma.push.business.dto.rsp.mini.*;
import com.vma.push.business.dto.rsp.staff.*;
import com.vma.push.business.dto.rsp.staff.RspStaffOrder;
import com.vma.push.business.dto.rsp.staff.RspTemplate;
import com.vma.push.business.dto.rsp.store.RspDiscount;
import com.vma.push.business.dto.rsp.store.RspStaffDiscount;
import com.vma.push.business.dto.rsp.store.RspStaffDiscount2;
import com.vma.push.business.dto.rsp.userInfo.RspUserLabelInfo;
import com.vma.push.business.entity.RecommendCard;
import com.vma.push.business.entity.Staff;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by huxiangqiang on 2018/4/23.
 */
public interface StaffMapper extends BaseDao<Staff, String> {
	List<RspStaff> getAll(@Param("deptid") String deptid, @Param("enid") String enid, @Param("name") String name);

	int selectByUserNameAndPassword(ReqSystemLogin reqSystemLogin);

	String findEnterpriseId(String id); //通过staff id 查到到对应的enterpriseid

	List<RspStaff> radar();//权限列表

	List<RspStaff> findStaffSelect(ReqStaffSelect reqStaffSelect);//条件查询列

	/**
	 * 通过用户id获取对应销售人的信息
	 *
	 * @param id
	 * @return
	 */
	List<RspStaff> StaffListByUserId(String id);

	Staff selectStaff(ReqSystemLogin reqSystemLogin);//根据账号密码查询对象

	/**
	 * 根据staffId查询名片详情
	 *
	 * @param id
	 * @return
	 */
	RspCardInfo queryCardInfo(@Param("enterpriseId") String enterpriseId, @Param("id") String id, @Param("userId") String userId);


	/**
	 * 获取名片
	 *
	 * @param staffid 员工id
	 * @return
	 */
	RspCard getByStaffId(@Param("staffid") String staffid, @Param("enid") String enid);

	Integer queryUsedCardNum(@Param("enterpriseId") String enterpriseId);


	/**
	 * 通过部门id和企业id获取员工
	 *
	 * @param enterpriseid
	 * @return
	 */
	List<RspStaff> getStaffByDepart(@Param("enterpriseid") String enterpriseid, @Param("id") String id);

	RspEnStaff queryStaff(@Param("id") String id, @Param("enid") String enid);

	/**
	 * 编辑名片
	 *
	 * @param staffid
	 * @return
	 */
	editCard getCard(@Param("staffid") String staffid, @Param("enid") String enid);

	List<RspStaffIntro> intro(@Param("staffid") String staffid, @Param("enid") String enid);

	/**
	 * 通过员工信息查询客户数量
	 *
	 * @param param
	 * @return
	 */
	Long countUser(Map param);


	List<RspCarlist> findCarList(String user_id);//名片列表

	RspStaffLogin findStaffByOpenId(String id);

	List<RspUserinfo> userinfo(@Param("staffid") String staffid, @Param("enid") String enid);

	RspStaffinfo staffInfo(@Param("id") String id, @Param("enid") String enid);

	String departname(@Param("enid") String enid, @Param("id") String id);

	/**
	 * 获取销售人员的所有客户
	 *
	 * @param fromid
	 * @param enid
	 * @return
	 */
	List<RspHandover> userlist(@Param("fromid") String fromid, @Param("enid") String enid);

	/**
	 * 判断是否已经是客户
	 *
	 * @param toid
	 * @param enid
	 * @return
	 */
	Integer iscustomer(@Param("id") String id, @Param("toid") String toid, @Param("enid") String enid);

	/**
	 * 交接用户
	 *
	 * @param id
	 * @param fromid
	 * @param toid
	 * @param enid
	 */
	void handover(@Param("id") String id, @Param("fromid") String fromid, @Param("toid") String toid, @Param("enid") String enid);

	String staffName(String id);

	void delcustomer(@Param("id") String id, @Param("fromid") String fromid, @Param("enid") String enid);

	void delIntro(@Param("staffid") String staffid, @Param("enid") String enid);

	int insertIntro(ReqAddIntro reqAddIntro);

	public List<Staff> findCond(@Param("enterpriseId") String enterpriseId, @Param("departmentId") String departmentId);

	List<String> childDept(@Param("dept") String dept, @Param("enid") String enid);

	String getSig(@Param("staffid") String staffid, @Param("enid") String enid);

	void editSig(@Param("content") String content, @Param("staffid") String staffid, @Param("enid") String enid);

	String getSigVideo(@Param("staffid") String staffid, @Param("enid") String enid);

	void editSigVideo(@Param("content") String content, @Param("staffid") String staffid, @Param("enid") String enid);

	Integer getCallingCardNumber(ReqCardNumber req);

	List<RspDaysCardNumber> getCallingCardNumberByDayNumber(ReqCardNumber req);

	void delintro(String id);

	List<ReqUserAndStaff> AllUserAndStaff();

	Staff selectStaffById(@Param("userid") String userid, @Param("enid") String enid);

	Staff findstaffBywxidAndEnid(@Param("userid") String userid, @Param("enterpriseid") String enterpriseid);

	String findWxId(String staffId);

	void delStaff(String id);

	RspEnterIconAndWxLogo findEnterIconAndWxLogo(@Param("staffId") String staffId);

	/**
	 * 查找头像为http的用户
	 *
	 * @return
	 */
	List<Staff> getHttpUser();

	/**
	 * 通过部门id和企业id获取员工
	 *
	 * @param ReqStaffOrder
	 * @return
	 */
	List<RspStaffOrder> staffOrderByOrderForm(ReqStaffOrder req);

	/**
	 * 通过部门id和企业id获取员工
	 *
	 * @param ReqStaffOrder
	 * @return
	 */
	List<RspStaffOrder> staffOrderByUserNumber(ReqStaffOrder req);

	List<RspStaffOrder> staffOrderByInteract(ReqStaffOrder req);

	List<RspStaffOrder> staffOrderByClosedRate(ReqStaffOrder req);

	/**
	 * 获取企业默认员工
	 *
	 * @param enid
	 * @return
	 */
	String defaultStaff(String enid);

	/**
	 * 获取员工所在部门
	 *
	 * @param staffid
	 * @return
	 */
	String deptByStaff(String staffid);

	RspUserInfo bossUserInfo(@Param("id") String id, @Param("enterpriseId") String enterpriseId);

	//客户总数
	Integer bossCustomCount(@Param("id") String id, @Param("enterpriseId") String enterpriseId);

	Integer bossAttachCount(@Param("id") String id, @Param("enterpriseId") String enterpriseId);

	RspTemplate queryCardTemplateId(@Param("enterpriseId") String enterpriseId, @Param("id") String id);

	RspStaffDiscount staffEnterDiscount(@Param("enterpriseId") String eneterpriseId, @Param("staffId") String staffId);

	List<RspDiscount> discountList(ReqDiscount reqDiscount);

	void setEnterDiscount(ReqEnterDiscount reqEnterDiscount);

	void setStaffDiscount(ReqEnterDiscount reqEnterDiscount);

	BigDecimal findDiscount(String staffid);

	RspStaffDiscount2 staffDiscount(String staffid);

	Integer isRadar(@Param("id") String id);

	Integer customCount(String staffId);

	Integer repeatCount(@Param("fromid") String fromid, @Param("toid") String toid);

	Integer staffRadar(@Param("id") String id, @Param("enid") String enid);

	List<String> userIcons(String staffId);

	Integer viewNum(String staffId);

	Integer consult(@Param("id") String id, @Param("enid") String enid);

	//List<Staff> getRefreshRader(String enterpriseId);

	List<Staff> findAllStaff(String enterpriseId);

	Staff getStaffByEnterpriseIdAndOpenid(@Param("enterpriseId") String enterpriseId, @Param("openId") String openId, @Param("status") String status);

	/**
	 * Created by ljh on 2018/10/24.
	 * begin
	 */

	//查询员工信息
	Staff getStaffByIdAndOpenId(@Param("staffId") String staffId, @Param("openId") String openId);

	//根据人员ID，用户ID查询用户自己的标签，以及系统的参考标签
	List<RspUserLabelInfo> queryStaffLabelAndReferLabelInfo(@Param("staffId") String staffId, @Param("userId") String userId);

	//查询人员录音
	AttachmentAudio queryStaffAudio(@Param("staffId") String staffId);

	//查询人员相片
	List<AttachmentImage> queryStaffImages(@Param("staffId") String staffId);

	//更新我的个人简介
	Integer updateSignature(@Param("staffId") String staffId, @Param("signature") String signature);

	//插入我的录音
	Integer insertAudio(AttachmentAudio audio);

	//更新我的录音
	Integer updateAudio(AttachmentAudio audio);

	//更新我的录音
	Integer deleteAudio(AttachmentAudio audio);

	//更新我的录音ID
	Integer updateAudioId(@Param("staffId") String staffId, @Param("audioId") String audioId);

	//保存我的标签
	Integer insertLabelInfo(RspUserLabelInfo userLabelInfo);

	//保存我的标签关系映射
	Integer insertLabelUserReal(RspUserLabelInfo userLabelInfo);

	//删除我的标签
	Integer deleteLabelInfo(RspUserLabelInfo userLabelInfo);

	//删除我的标签关系映射
	Integer deleteLabelUserReal(RspUserLabelInfo userLabelInfo);

	//插入我的录音
	Integer insertImage(AttachmentImage image);

	//删除我的录音
	Integer deleteImage(AttachmentImage image);

	//查询通讯录人员列表
	List<ContactStaff> queryContactStaffListByEntId(@Param("enterprise_id") String userId, @Param("openId") String openId);

	//查询通讯录名片夹
	List<ContactCard> queryContactCardListByUserId(@Param("userId") String userId, @Param("openId") String openId, @Param("status") Integer status);

	//查询看名片名片夹
	List<ContactCard> queryCardListByUserId(@Param("userId") String userId, @Param("openId") String openId, @Param("relaStatus") String relaStatus);

	String queryStaffIdByOpenId(String open_id);

	ContactCard getReceiverStaff(@Param("id") String id);

	//更新名片状态
	Integer updateRelaStatus(@Param("status") String status, @Param("relaId") String relaId);

	Staff getStaffByOpenidAndEntId(@Param("openid") String openid, @Param("enterpriseId") String enterpriseId);

	/**
	 * 根据企业id，部门id获取员工列表
	 *
	 * @param enterpriseId
	 * @param departmentId
	 * @return
	 */
	List<ResDepartmentPerson> getDepartmentPersionList(@Param("enterpriseId") String enterpriseId, @Param("departmentId") String departmentId, @Param("status") String status, @Param("name") String name);

	Map<String, Object> getVideo(String staffId);

	Integer addVideo(Map<String, Object> map);

	Integer delVideoByStaffId(String staffId);

	Integer updStaffVideo(Map<String, Object> map);

	Integer updCardStyle(Map<String, Object> map);

	Map<String, Object> getCardStyle(String staffId);

	Integer updShareSetup(Map<String, Object> map);

	List<Website> getWebsiteByEnterpriseId(@Param("id") String id);

	Integer addWebsite(Website website);

	Integer updEnterpriseWebsite(Website website);

	List<WebsiteTemplate> getWebsiteComponentById(@Param("id") String id);

	Integer insWebsiteComponent(List<WebsiteTemplate> list);

	Integer delWebsiteComponentById(@Param("id") String id);
	/**
	 * Created by ljh on 2018/10/24.
	 * end
	 */
	/**
	 * 根据openid获取员工
	 */
	String isExistCardByOpenId(@Param("open_id") String open_id);

	// Add by plh at 2018-11-01 for 查看当前企业微信下是否已经存在员工记录
	String isExistStaffByEntWxAndPhone(@Param("corpId") String corpId, @Param("phone") String phone);

	//Add by plh at 2018-10-30 for 小程序用户OPEND，可能存在多个企业，因此查询出随机的一个企业的名片
	public Staff getRandomStaffByOpenId(@Param("open_id") String open_id);

	//Add by plh at 2018-12-10 for 小程序用户user_id，可能存在多个销售员名片，因此查询出随机的一个销售员名片(有效)
	public Staff getRandomStaffByUserId(@Param("user_id") String user_id);

	List<Staff> getStaffListByOpenId(@Param("openId") String openId);

	//Add by PLH at 2018-11-28 for 一个企业微信用户有多个名片(多个部门)
	List<String> getWxIdsByPhone(@Param("corpId") String corpId, @Param("phone") String phone);

	List<String> getWxDeptIdsByPhone(@Param("corpId") String corpId, @Param("phone") String phone);

	List<ResDepartmentPerson> getManagerList(@Param("enterprise_id") String enterprise_id, @Param("role") String role);

	// 获取公司员工的头像
	List<String> getHeadIconListByEnterpriseId(String enterpriseId);

	// 根据手机号和企业id查询员工
	Staff getStaffByEnterpriseIdAndPhone(@Param("enterprise_id") String enterprise_id, @Param("phone") String phone);

	// 获取申请中的员工列表
	List<ResDepartmentPerson> getPendingStaffList(@Param("enterprise_id") String enterprise_id);

	// 获取已审批的员工列表
	List<ResDepartmentPerson> getApproveStaffList(@Param("enterprise_id") String enterprise_id);

	// 获取微信Id
	String getWxIdByStaffId(@Param("staffId") String staffId);

	List<RepMyselfCardList> getMyselfListByOpenId(String openId);

	RepMyCardInfo getMyselfInfo(String openid);

	List<String> getStaffListByEnterpriseId(String enterpriseId);

	List<String> getManagerPhoneByEnterpriseId(String enterpriseId);

	String getEnterpriseName(String staffId);

	int getPendingPerson(String enterpriseId);

	List<ContactCard> getCollectCardList(String userId);

	List<ContactCard> searchCard(@Param("userId") String userId, @Param("comment") String comment);

	Integer updataCardImgUrl(@Param("staffId") String staffId, @Param("cardImgUrl") String cardImgUrl);

	RepRadarPermissions getRadarPermission(@Param("staffId") String staffId);

	List<RepRecommendCard> getRecommendCardToMainPage(@Param("openId") String openId);

}
