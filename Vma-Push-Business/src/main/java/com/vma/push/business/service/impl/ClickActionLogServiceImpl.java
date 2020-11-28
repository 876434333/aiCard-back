package com.vma.push.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.vma.push.business.common.ActionCode;
import com.vma.push.business.common.ErrCode;
import com.vma.push.business.common.exception.BusinessException;
import com.vma.push.business.dao.*;
import com.vma.push.business.dto.req.ReqClickInfo;
import com.vma.push.business.dto.rsp.RspActionLog;
import com.vma.push.business.dto.rsp.RspPage;
import com.vma.push.business.entity.*;
import com.vma.push.business.service.IClickActionLogService;
import com.vma.push.business.service.IMessageService;
import com.vma.push.business.util.UserDataUtil;
import com.vma.push.business.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by huxiangqiang on 2018/4/27.
 */
@Service
public class ClickActionLogServiceImpl extends AbstractServiceImpl<ClickActionLog, String> implements IClickActionLogService {
	@Autowired
	private ClickActionLogMapper clickActionLogMapper;

	@Autowired
	private UserInfoMapper userInfoMapper;

	@Autowired
	private IMessageService messageService;

	@Autowired
	private UserStaffRelaMapper userStaffRelaMapper;

	@Autowired
	private StaffMapper staffMapper;

	@Autowired
	private TotalsMapper totalMapper;
	@Autowired
	private EnterpriseMapper enterpriseMapper;

	public ClickActionLogServiceImpl(ClickActionLogMapper base) {
		super(base);
		this.clickActionLogMapper = base;
	}

	@Override
	public void click(ReqClickInfo reqClickInfo, HttpServletRequest request) {
		// M lql 连续点击点赞和取消点赞出现负数的情况
		String lock = "lock";
		synchronized (lock) {
			String actionCode = reqClickInfo.getAction_code();//动作id
			String userId = UserDataUtil.getUserId(request);//用户id
			//String userId = "13ab196b-b9fa-487b-903d-839aee12e6a0";
			String staffId = reqClickInfo.getEmployee_id();//销售人员的id
			String departmentId = reqClickInfo.getDepartment_id();//部门id
			String enterpriseId = reqClickInfo.getEnterprise_id();//企业的id
			String wx_id = staffMapper.findWxId(staffId);

			Staff staff = staffMapper.selectByPrimaryKey(staffId);

			if (staff == null && staff.getStatus().equals(0)) {
				throw new BusinessException(ErrCode.NO_STAFF_FOUND, "名片已失效");
			}

			departmentId = staff.getDepartmentId();
			enterpriseId = staff.getEnterpriseId();

			String offerId = reqClickInfo.getOffer_id();//产品的id
			ClickActionLog actionLog = new ClickActionLog();
			Totals total = new Totals();
			Totals t = totalMapper.findTotal(staffId);//根据企业id来查询total
			total.setStaffId(staffId);
			UserStaffRela userStaffRela = userStaffRelaMapper.queryStaffInfoByUser(staffId, userId, enterpriseId);
			Enterprise enterprise = enterpriseMapper.selectByPrimaryKey(enterpriseId);
			if (actionCode.equals(ActionCode.ACTION_CODE_1001)) {//查看名片
				actionLog.setActionTarget("名片");
				actionLog.setDescription("查看了你的" + enterprise.getName() + "名片");
				actionLog.setDepartmentId(departmentId);
				Long num = clickActionLogMapper.queryActionNumByUser(staffId, ActionCode.ACTION_CODE_1001, userId);
				//int a = t.getViewToday();
				actionLog.setNum(num.intValue() + 1);
				if (t == null) {
					total.setViewToday(1);
				} else {
					total.setViewToday(t.getViewToday() + 1);//查看名片
				}
			} else if (actionCode.equals(ActionCode.ACTION_CODE_1002)) {//名片点赞
				actionLog.setActionTarget("名片");
				actionLog.setDescription("觉得" + enterprise.getName() + "非常靠谱");
				actionLog.setDepartmentId(departmentId);
				userStaffRela.setIsZan(1);
				userStaffRelaMapper.updateByPrimaryKeySelective(userStaffRela);
				if (t == null) {
					total.setZanToday(1);
				} else {
					total.setZanToday(t.getZanToday() + 1);//点赞
				}

			} else if (actionCode.equals(ActionCode.ACTION_CODE_1003)) {//名片取消点赞
				actionLog.setActionTarget("名片");
				actionLog.setDescription("开始觉得" + enterprise.getName() + "不靠谱了");
				actionLog.setDepartmentId(departmentId);
				userStaffRela.setIsZan(0);
				userStaffRelaMapper.updateByPrimaryKeySelective(userStaffRela);
				total.setZanToday(t.getZanToday() - 1);//取消点赞
			} else if (actionCode.equals(ActionCode.ACTION_CODE_1004)) {//转发名片
				actionLog.setActionTarget("名片");
				actionLog.setDescription("转发了你的" + enterprise.getName() + "名片");
				actionLog.setDepartmentId(departmentId);
				if (t == null) {
					total.setZhuanToday(1);//转发名片
				} else {
					total.setZhuanToday(t.getZhuanToday() + 1);
				}
			} else if (actionCode.equals(ActionCode.ACTION_CODE_1005)) {//呼叫座机
				actionLog.setActionTarget("座机");
				actionLog.setDescription("拨打你的座机");
				actionLog.setDepartmentId(departmentId);
			} else if (actionCode.equals(ActionCode.ACTION_CODE_1006)) {//呼叫手机
				actionLog.setActionTarget("手机");
				actionLog.setDescription("拨打你的手机");
				actionLog.setDepartmentId(departmentId);
			} else if (actionCode.equals(ActionCode.ACTION_CODE_1007)) {//复制邮箱
				actionLog.setActionTarget("邮箱");
				actionLog.setDescription("复制你的邮箱");
				actionLog.setDepartmentId(departmentId);
			} else if (actionCode.equals(ActionCode.ACTION_CODE_1008)) {//复制微信
				actionLog.setActionTarget("微信");
				actionLog.setDescription("复制你的微信");
				actionLog.setDepartmentId(departmentId);
			} else if (actionCode.equals(ActionCode.ACTION_CODE_1009)) {//复制地址
				actionLog.setActionTarget("地址");
				actionLog.setDescription("复制你的地址");
				actionLog.setDepartmentId(departmentId);
			} else if (actionCode.equals(ActionCode.ACTION_CODE_1010)) {//保存信息
				actionLog.setActionTarget("名片");
				actionLog.setDescription("保存了联系方式，可以考虑拜访");
				actionLog.setDepartmentId(departmentId);
			} else if (actionCode.equals(ActionCode.ACTION_CODE_1011)) {//查看朋友圈
				actionLog.setActionTarget("企业朋友圈");
				actionLog.setDescription("查看了" + enterprise.getName() + "企业朋友圈");
				actionLog.setDepartmentId(departmentId);
			} else if (actionCode.equals(ActionCode.ACTION_CODE_1012)) {//评论企业朋友圈
				actionLog.setActionTarget("企业朋友圈");
				actionLog.setDescription("评论" + enterprise.getName() + "企业朋友圈");
				actionLog.setDepartmentId(departmentId);
			} else if (actionCode.equals(ActionCode.ACTION_CODE_1013)) {//点赞企业朋友圈
				actionLog.setActionTarget("企业朋友圈");
				actionLog.setDescription("点赞" + enterprise.getName() + "企业朋友圈");
				actionLog.setDepartmentId(departmentId);
			} else if (actionCode.equals(ActionCode.ACTION_CODE_1014)) {//转发企业朋友圈
				actionLog.setActionTarget("企业朋友圈");
				actionLog.setDescription("转发" + enterprise.getName() + "企业朋友圈");
				actionLog.setDepartmentId(departmentId);
			} else if (actionCode.equals(ActionCode.ACTION_CODE_1015)) {//查看商品、商城
				actionLog.setActionTarget("商城");
				actionLog.setDescription("正在查看你的" + enterprise.getName() + "商城，尽快把握商机");
				actionLog.setDepartmentId(departmentId);
			} else if (actionCode.equals(ActionCode.ACTION_CODE_1016)) {//查看具体商品详情
				// TODO: 2018/5/9
				actionLog.setActionTarget(reqClickInfo.getOffer_name());
				actionLog.setDescription("查看了" + reqClickInfo.getOffer_name());
				actionLog.setOfferId(offerId);
				actionLog.setDepartmentId(departmentId);
				Long num = clickActionLogMapper.findNum(enterpriseId, ActionCode.ACTION_CODE_1016, offerId);
				actionLog.setNum(num.intValue() + 1);
			} else if (actionCode.equals(ActionCode.ACTION_CODE_1017)) {//转发商品
				actionLog.setActionTarget(reqClickInfo.getOffer_name());
				actionLog.setDescription("转发了商品" + reqClickInfo.getOffer_name());
				actionLog.setOfferId(offerId);
				actionLog.setDepartmentId(departmentId);
			} else if (actionCode.equals(ActionCode.ACTION_CODE_1018)) {//咨询商品
				actionLog.setActionTarget(reqClickInfo.getOffer_name());
				actionLog.setOfferId(offerId);
				actionLog.setDescription("对" + reqClickInfo.getOffer_name() + "感兴趣了");
				actionLog.setDepartmentId(departmentId);
			} else if (actionCode.equals(ActionCode.ACTION_CODE_1019)) {//下单商品
				actionLog.setActionTarget(reqClickInfo.getOffer_name());
				actionLog.setDescription("对你的" + reqClickInfo.getOffer_name() + "感兴趣并开始下单了");
				actionLog.setOfferId(reqClickInfo.getOffer_id());
				actionLog.setDepartmentId(departmentId);
			} else if (actionCode.equals(ActionCode.ACTION_CODE_1020)) {//支付商品
				actionLog.setActionTarget(reqClickInfo.getOffer_name());
				actionLog.setDescription("支付了商品:" + reqClickInfo.getOffer_name());
				actionLog.setOfferId(reqClickInfo.getOffer_id());
				actionLog.setDepartmentId(departmentId);
			} else if (actionCode.equals(ActionCode.ACTION_CODE_1021)) {//查看官网
				actionLog.setActionTarget(reqClickInfo.getOffer_name());
				actionLog.setDescription("查看看了" + enterprise.getName() + "官网");
				actionLog.setOfferId(reqClickInfo.getOffer_id());
				actionLog.setDepartmentId(departmentId);
			} else {
				throw new BusinessException(ErrCode.ERROR_ACTION_CODE, "无效的动作编码");
			}
			actionLog.setActionCode(actionCode);
			actionLog.setCreateTime(new Date());
			actionLog.setEmployeeId(staffId);
			actionLog.setEnterpriseId(enterpriseId);
			actionLog.setUserId(userId);
			actionLog.setId(UuidUtil.getRandomUuid());
			clickActionLogMapper.insertSelective(actionLog);

			//totalMapper.update

			String[] staffArr = {wx_id};

			//更新用户动作时间

			UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userId);

//        userInfo.setLastActionTime(new Date());
//
//        userInfoMapper.updateByPrimaryKeySelective(userInfo);

			userStaffRela.setLastActionTime(new Date());

			userStaffRelaMapper.updateByPrimaryKeySelective(userStaffRela);

			if (t == null) {
				total.setId(UuidUtil.getRandomUuid());
				totalMapper.insertSelective(total);
			} else {
				total.setId(t.getId());
				totalMapper.updateByPrimaryKeySelective(total);
			}
			String userName = userInfo.getNickName();

			if(StringUtils.isEmpty(userName)) {
				System.out.println("---------------发送ai没有昵称-------------user_id-------"+userInfo.getId());
			}
			//发送消息
			messageService.sendMsg_ai(staffArr, userName + actionLog.getDescription(), enterpriseId);
		}
	}

	@Override
	public RspPage<RspActionLog> getActionListByUser(String staffid, Integer pageNum, Integer pageSize) {
		RspPage<RspActionLog> rspPage = new RspPage<RspActionLog>();
		com.github.pagehelper.Page page = PageHelper.startPage(pageNum, pageSize, true);
		rspPage.setData_list(clickActionLogMapper.getActionListByUser(staffid));
		rspPage.setTotal_num(page.getTotal());
		rspPage.setPage_num(page.getPageNum());
		rspPage.setPage_size(page.getPageSize());
		return rspPage;
	}
}
