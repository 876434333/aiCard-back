package com.vma.push.business.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.vma.push.business.common.ActionCode;
import com.vma.push.business.dao.*;
import com.vma.push.business.dto.req.ReqFunnel;
import com.vma.push.business.dto.req.ReqGetByRate;
import com.vma.push.business.dto.rsp.RspPage;
import com.vma.push.business.dto.rsp.RspUserByRate;
import com.vma.push.business.dto.rsp.boss.*;
import com.vma.push.business.dto.rsp.staff.RspAnalysis;
import com.vma.push.business.dto.rsp.staff.RspIndex;
import com.vma.push.business.dto.rsp.staff.RspStaffAnalysis;
import com.vma.push.business.dto.rsp.userInfo.DataItem;
import com.vma.push.business.service.IBossService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenzui on 2018/5/16.
 */
@Service
public class BossServiceImpl implements IBossService {
	@Autowired
	private UserStaffRelaMapper userStaffRelaMapper;

	@Autowired
	private EnterpriseMapper enterpriseMapper;

	@Autowired
	private BiActionLogMapper biActionLogMapper;

	@Autowired
	private BiUserLogMapper biUserLogMapper;

	@Autowired
	private StaffMapper staffInfoMapper;

	@Autowired
	private ClickActionLogMapper clickActionLogMapper;

	@Override
	public Long countUser(Map param) {
		return userStaffRelaMapper.countUser(param);
	}

	@Override
	public List<RspViewData> viewdata(Integer type, String staffId, String departmentId, String enterpriseId) {
		RspViewData user = new RspViewData();
		RspViewData attach = new RspViewData();
		RspViewData view = new RspViewData();
		RspViewData turn = new RspViewData();
		RspViewData save = new RspViewData();
		RspViewData zan = new RspViewData();
		user.setDesc("客户总数");
		attach.setDesc("跟进总数");
		view.setDesc("浏览总数");
		turn.setDesc("被转发次数");
		save.setDesc("被保存总数");
		zan.setDesc("被点赞总数");
		Map param = new HashMap();
		param.put("departmentId", departmentId);
		param.put("enterpriseId", enterpriseId);
		param.put("staffId", staffId);

		if (type.equals(1)) {//汇总
			user.setNow_num(userStaffRelaMapper.countUser(param));
			param.put("attach", 1);
			attach.setNow_num(userStaffRelaMapper.attachcountUser(param));
			param.put("code", ActionCode.ACTION_CODE_1001);
			view.setNow_num(biActionLogMapper.countAction(param));

			param.put("code", ActionCode.ACTION_CODE_1004);
			turn.setNow_num(biActionLogMapper.countAction(param));

			param.put("code", ActionCode.ACTION_CODE_1010);
			save.setNow_num(biActionLogMapper.countAction(param));
			//今日点赞总数
			//Long zanToday = clickActionLogMapper.queryActionNumAll(enterpriseId,ActionCode.ACTION_CODE_1002)-clickActionLogMapper.queryActionNumAll(enterpriseId,ActionCode.ACTION_CODE_1003);
			param.put("code", ActionCode.ACTION_CODE_1002);
			zan.setNow_num(biActionLogMapper.countAction(param));

		} else if (type.equals(2)) {//昨日
			param.put("day", 1);
			long now = userStaffRelaMapper.countUser(param);
			param.put("day", 2);
			long old = userStaffRelaMapper.countUser(param);
			user.setNow_num(now);
			user.setLast_num(old - now);

			param.put("attach", 1);
			param.put("day", 1);
			now = userStaffRelaMapper.attachcountUser(param);
			param.put("day", 2);
			old = userStaffRelaMapper.attachcountUser(param);

			attach.setNow_num(now);
			attach.setLast_num(old - now);

			param.put("day", 1);
			param.put("code", ActionCode.ACTION_CODE_1001);
			now = biActionLogMapper.countAction(param);
			param.put("day", 2);
			old = biActionLogMapper.countAction(param);
			view.setNow_num(now);//73
			view.setLast_num(old - now);//79


			param.put("day", 1);
			param.put("code", ActionCode.ACTION_CODE_1004);
			now = biActionLogMapper.countAction(param);
			param.put("day", 2);
			old = biActionLogMapper.countAction(param);
			turn.setNow_num(now);
			turn.setLast_num(old - now);

			param.put("day", 1);
			param.put("code", ActionCode.ACTION_CODE_1010);
			now = biActionLogMapper.countAction(param);
			param.put("day", 2);
			old = biActionLogMapper.countAction(param);
			save.setNow_num(now);
			save.setLast_num(old - now);

			param.put("day", 1);
			param.put("code", ActionCode.ACTION_CODE_1002);
			now = biActionLogMapper.countAction(param);//9
			param.put("day", 2);
			old = biActionLogMapper.countAction(param);
			zan.setNow_num(now);
			zan.setLast_num(old - now);

		} else if (type.equals(3)) {//近七日

			param.put("day", 7);
			long now = userStaffRelaMapper.countUser(param);
			param.put("day", 14);
			long old = userStaffRelaMapper.countUser(param);
			user.setNow_num(now);
			user.setLast_num(old - now);

			param.put("attach", 1);
			param.put("day", 7);
			now = userStaffRelaMapper.attachcountUser(param);
			param.put("day", 14);
			old = userStaffRelaMapper.attachcountUser(param);

			attach.setNow_num(now);
			attach.setLast_num(old - now);

			param.put("day", 7);
			param.put("code", ActionCode.ACTION_CODE_1001);
			now = biActionLogMapper.countAction(param);
			param.put("day", 14);
			old = biActionLogMapper.countAction(param);
			view.setNow_num(now);
			view.setLast_num(old - now);


			param.put("day", 7);
			param.put("code", ActionCode.ACTION_CODE_1004);
			now = biActionLogMapper.countAction(param);
			param.put("day", 14);
			old = biActionLogMapper.countAction(param);
			turn.setNow_num(now);
			turn.setLast_num(old - now);

			param.put("day", 7);
			param.put("code", ActionCode.ACTION_CODE_1010);
			now = biActionLogMapper.countAction(param);
			param.put("day", 14);
			old = biActionLogMapper.countAction(param);
			save.setNow_num(now);
			save.setLast_num(old - now);

			param.put("day", 7);
			param.put("code", ActionCode.ACTION_CODE_1002);
			now = biActionLogMapper.countAction(param);
			param.put("day", 14);
			old = biActionLogMapper.countAction(param);
			zan.setNow_num(now);
			zan.setLast_num(old - now);

		} else if (type.equals(4)) {//近30日
			param.put("day", 30);
			long now = userStaffRelaMapper.countUser(param);
			param.put("day", 60);
			long old = userStaffRelaMapper.countUser(param);
			user.setNow_num(now);
			user.setLast_num(old - now);

			param.put("attach", 1);
			param.put("day", 30);
			now = userStaffRelaMapper.attachcountUser(param);
			param.put("day", 60);
			old = userStaffRelaMapper.attachcountUser(param);

			attach.setNow_num(now);
			attach.setLast_num(old - now);

			param.put("day", 30);
			param.put("code", ActionCode.ACTION_CODE_1001);
			now = biActionLogMapper.countAction(param);
			param.put("day", 60);
			old = biActionLogMapper.countAction(param);
			view.setNow_num(now);
			view.setLast_num(old - now);


			param.put("day", 30);
			param.put("code", ActionCode.ACTION_CODE_1004);
			now = biActionLogMapper.countAction(param);
			param.put("day", 60);
			old = biActionLogMapper.countAction(param);
			turn.setNow_num(now);
			turn.setLast_num(old - now);

			param.put("day", 30);
			param.put("code", ActionCode.ACTION_CODE_1010);
			now = biActionLogMapper.countAction(param);
			param.put("day", 60);
			old = biActionLogMapper.countAction(param);
			save.setNow_num(now);
			save.setLast_num(old - now);

			param.put("day", 30);
			param.put("code", ActionCode.ACTION_CODE_1002);
			now = biActionLogMapper.countAction(param);
			param.put("day", 60);
			old = biActionLogMapper.countAction(param);
			zan.setNow_num(now);
			zan.setLast_num(old - now);


		}
		List<RspViewData> list = new ArrayList<>();
		list.add(user);
		list.add(attach);
		list.add(view);
		list.add(turn);
		list.add(save);
		list.add(zan);
		return list;
	}

	@Override
	public List<DataItem> countUserAdd(Map param) {
		return biUserLogMapper.countUserAdd(param);
	}

	@Override
	public List<DataItem> countUserAttach(Map param) {
		return biUserLogMapper.countUserAttach(param);
	}

	@Override
	public List<DataItem> countUserIm(Map param) {
		return biUserLogMapper.countUserIm(param);
	}

	@Override
	public List<DataItem> countOrder(Map param) {
		return biUserLogMapper.countOrder(param);
	}

	@Override
	public RspAnalysis analysis(String enterpriseId, String departmentId) {
		//List<DataItem> items = biActionLogMapper.countEveryAction(enterpriseId,departmentId);
		List<DataItem> items = biActionLogMapper.countEveryAction(enterpriseId, null);
		//查询企业该项最大值
		int v1 = 0, v2 = 0, v3 = 0, v4 = 0, v5 = 0, v6 = 0;
		for (DataItem item : items) {
			if (ActionCode.LIST_V1.contains(item.getName())) {
				v1 += Integer.valueOf(item.getValue());
			}
			if (ActionCode.LIST_V2.contains(item.getName())) {
				v2 += Integer.valueOf(item.getValue());
			}
			if (ActionCode.LIST_V3.contains(item.getName())) {
				v3 += Integer.valueOf(item.getValue());
			}
			if (ActionCode.LIST_V4.contains(item.getName())) {
				v4 += Integer.valueOf(item.getValue());
			}
			if (ActionCode.LIST_V5.contains(item.getName())) {
				v5 += Integer.valueOf(item.getValue());
			}
			if (ActionCode.LIST_V6.contains(item.getName())) {
				v6 += Integer.valueOf(item.getValue());
			}
		}

		RspAnalysis rsp = new RspAnalysis();
		rsp.setMax_v1(v1);
		rsp.setMax_v2(v2);
		rsp.setMax_v3(v3);
		rsp.setMax_v4(v4);
		rsp.setMax_v5(v5);
		rsp.setMax_v6(v6);
		Map param = new HashMap();
		param.put("enterpriseId", enterpriseId);
		param.put("departmentId", departmentId);
		List<RspStaffAnalysis> staffs = biActionLogMapper.countAllActionNum(param);

		for (RspStaffAnalysis analysis : staffs) {


			param.put("staffId", analysis.getId());
			param.put("departmentId", analysis.getDepartment_id());
			param.put("codes", ActionCode.LIST_V1);
			analysis.setV1(biActionLogMapper.countActionsByStaff(param).intValue());

			param.put("codes", ActionCode.LIST_V2);
			analysis.setV2(biActionLogMapper.countActionsByStaff(param).intValue());

			param.put("codes", ActionCode.LIST_V3);
			analysis.setV3(biActionLogMapper.countActionsByStaff(param).intValue());

			param.put("codes", ActionCode.LIST_V4);
			analysis.setV4(biActionLogMapper.countActionsByStaff(param).intValue());

			param.put("codes", ActionCode.LIST_V5);
			analysis.setV5(biActionLogMapper.countActionsByStaff(param).intValue());

			param.put("codes", ActionCode.LIST_V6);
			analysis.setV6(biActionLogMapper.countActionsByStaff(param).intValue());


		}
		rsp.setStaffs(staffs);

		return rsp;

	}

	/**
	 * 根据成交率查看员工
	 *
	 * @param reqGetByRate
	 * @return
	 */
	@Override
	public RspPage<RspUserByRate> getUserByRate(ReqGetByRate reqGetByRate) {
		enterpriseMapper.proChildDepartmentId(reqGetByRate.getDepartment_id(), reqGetByRate.getEnterprise_id());
		RspPage<RspUserByRate> rspPage = new RspPage<>();
		Page page = PageHelper.startPage(reqGetByRate.getPage_num(), reqGetByRate.getPage_size(), true);
		List<RspUserByRate> rspUserByRates = biUserLogMapper.getUserByRate(reqGetByRate);
		rspPage.setData_list(rspUserByRates);
		rspPage.setTotal_num(page.getTotal());
		rspPage.setPage_num(page.getPageNum());
		rspPage.setPage_size(page.getPageSize());
		return rspPage;
	}

	@Override
	public List<RspFunnel> funnel(ReqFunnel reqFunnel) {
		//近n天各个范围的值
		//List<RspFunnel> funnels=biActionLogMapper.funnel(reqFunnel);
		List<RspFunnel> funnels = new ArrayList<>();
		//≤50
		RspFunnel funnel1 = new RspFunnel();
		reqFunnel.setType(1);
		funnel1.setName("≤50");
		funnel1.setValue(biActionLogMapper.funnel(reqFunnel));
		funnel1.setIncrease(funnel1.getValue() - biActionLogMapper.lastfunnel(reqFunnel));
		funnels.add(funnel1);

		//≤80
		RspFunnel funnel2 = new RspFunnel();
		reqFunnel.setType(2);
		funnel2.setName("≤80");
		funnel2.setValue(biActionLogMapper.funnel(reqFunnel));
		funnel2.setIncrease(funnel2.getValue() - biActionLogMapper.lastfunnel(reqFunnel));
		funnels.add(funnel2);

		//≤90
		RspFunnel funnel3 = new RspFunnel();
		reqFunnel.setType(3);
		funnel3.setName("≤90");
		funnel3.setValue(biActionLogMapper.funnel(reqFunnel));
		funnel3.setIncrease(funnel3.getValue() - biActionLogMapper.lastfunnel(reqFunnel));
		funnels.add(funnel3);

		//≤100
		RspFunnel funnel4 = new RspFunnel();
		reqFunnel.setType(4);
		funnel4.setName("≤100");
		funnel4.setValue(biActionLogMapper.funnel(reqFunnel));
		funnel4.setIncrease(funnel4.getValue() - biActionLogMapper.lastfunnel(reqFunnel));
		funnels.add(funnel4);

		return funnels;
	}

	@Override
	public RspOrderAndMoney orderAndMoney(ReqFunnel reqFunnel) {
		RspOrderAndMoney rspOrderAndMoney = new RspOrderAndMoney();
		rspOrderAndMoney.setMoney(biActionLogMapper.money(reqFunnel));
		rspOrderAndMoney.setOrder(biActionLogMapper.order(reqFunnel));
		return rspOrderAndMoney;
	}

	@Override
	public RspUserInfo userInfo(String id, String enterpriseId) {
		RspUserInfo rspUserInfo = staffInfoMapper.bossUserInfo(id, enterpriseId);
   /*     rspUserInfo.setCustomer(staffInfoMapper.bossCustomCount(id,enterpriseId));//客户总数
        rspUserInfo.setAttach(staffInfoMapper.bossAttachCount(id,enterpriseId));//跟进用户数
        rspUserInfo.setConsult(0);//咨询用户数*/
		return rspUserInfo;
	}

	@Override
	public RspUserCusInfo userCusInfo(String id, String enterpriseId) {
		RspUserCusInfo rspUserCusInfo = new RspUserCusInfo();
		rspUserCusInfo.setCustomer(staffInfoMapper.bossCustomCount(id, enterpriseId));//客户总数
		rspUserCusInfo.setAttach(staffInfoMapper.bossAttachCount(id, enterpriseId));//跟进用户数
		rspUserCusInfo.setConsult(staffInfoMapper.consult(id, enterpriseId));//咨询用户数
		return rspUserCusInfo;
	}

	@Override
	public RspIndex cardAndAcount(String enterpriseId) {
		//开通时间  到期时间  已用天数 剩余天数  名片总数
		RspIndex index = enterpriseMapper.cardAndAcount(enterpriseId);
		if (index != null) {
			index.setCustomer_num(enterpriseMapper.findCusNum(enterpriseId));//客户数
			index.setStaff_num(enterpriseMapper.findStaffNun(enterpriseId));//员工数
			index.setCard_used_num(enterpriseMapper.findCardUsedNum(enterpriseId));//已用名片数
		}
		return index;
	}

	@Override
	public List<DataItem> kpiView(Map param) {
		Integer i = (Integer) param.get("type");
		if (i == 1) {
			return biActionLogMapper.cusView(param);
		} else if (i == 2) {
			param.put("code", 1000);//跟进
			return biActionLogMapper.clickView(param);
		} else if (i == 3) {
			param.put("code", 1001);//浏览
			return biActionLogMapper.clickView(param);
		} else if (i == 4) {
			param.put("code", 1004);//转发
			return biActionLogMapper.clickView(param);
		} else if (i == 5) {
			param.put("code", 1010);
			return biActionLogMapper.clickView(param);
		} else if (i == 6) {
			param.put("code", 1002);
			return biActionLogMapper.clickView(param);
		} else {
			return null;
		}
	}
}
