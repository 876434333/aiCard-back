package com.vma.push.business.service.impl;

import com.vma.push.business.dao.DeployMapper;
import com.vma.push.business.dao.EnterpriseMapper;
import com.vma.push.business.dto.req.RepParentEnter;
import com.vma.push.business.dto.req.enterprise.ReqEnterpriseHistoryNumber;
import com.vma.push.business.dto.req.enterprise.ReqEnterpriseNumber;
import com.vma.push.business.dto.rsp.RspEnterpriseHistoryNumber;
import com.vma.push.business.dto.rsp.RspEnterpriseNumber;
import com.vma.push.business.dto.rsp.RspMiniOem;
import com.vma.push.business.dto.rsp.RspPointDetail;
import com.vma.push.business.entity.Deploy;
import com.vma.push.business.entity.Enterprise;
import com.vma.push.business.service.IEnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by JINzm on 2018/6/14.
 */
@Service
public class EnterpriseServiceImpl extends AbstractServiceImpl<Enterprise, String> implements IEnterpriseService {
	@Autowired
	private EnterpriseMapper mapper;
	@Autowired
	private DeployMapper deployMapper;

	public EnterpriseServiceImpl(EnterpriseMapper mapper) {
		super(mapper);
		this.mapper = mapper;
	}

	@Override
	public RspPointDetail pointDetail(String id) {
		return mapper.pointDetail(id);
	}

	@Override
	public RspEnterpriseNumber getEnterpriseNumber(Integer roleId, String id) {
		RspEnterpriseNumber rspEnterpriseNumber = new RspEnterpriseNumber();
		ReqEnterpriseNumber req = new ReqEnterpriseNumber();
		switch (roleId) {
			case 4: {
				rspEnterpriseNumber.setFirst_number(mapper.findFirstAgentNumber());
				req.setRoleId(roleId);
				req.setId("0");
				rspEnterpriseNumber.setSecond_number(mapper.findSecondAgentNumber(req));
				rspEnterpriseNumber.setThird_number(mapper.findThirdAgentNumber(req));
				rspEnterpriseNumber.setEnterprise_number(mapper.findEnterpriseNumber(req));
				break;
			}
			case 3: {
				req.setRoleId(roleId);
				req.setId(id);
				rspEnterpriseNumber.setSecond_number(mapper.findSecondAgentNumber(req));
				rspEnterpriseNumber.setThird_number(mapper.findThirdAgentNumber(req));
				rspEnterpriseNumber.setEnterprise_number(mapper.findEnterpriseNumber(req));
				break;
			}
			case 2: {
				req.setRoleId(roleId);
				req.setId(id);
				rspEnterpriseNumber.setThird_number(mapper.findThirdAgentNumber(req));
				rspEnterpriseNumber.setEnterprise_number(mapper.findEnterpriseNumber(req));
				break;
			}
			case 1: {
				req.setRoleId(roleId);
				req.setId(id);
				rspEnterpriseNumber.setEnterprise_number(mapper.findEnterpriseNumber(req));
				break;
			}
		}
		return rspEnterpriseNumber;
	}

	@Override
	public RspEnterpriseHistoryNumber getEnterpriseHistoryNumber(Integer roleId, String id) {
		RspEnterpriseHistoryNumber rsp = new RspEnterpriseHistoryNumber();
		ReqEnterpriseHistoryNumber req = new ReqEnterpriseHistoryNumber();
		req.setId(id);
		req.setDayNumber(0);
		System.out.println(req);
		rsp.setToday(mapper.findEnterpriseHistoryNumber(req));
		req.setDayNumber(1);
		rsp.setYesterday(mapper.findEnterpriseHistoryNumber(req));
		req.setDayNumber(null);
		rsp.setAll(mapper.findEnterpriseHistoryNumber(req));
		return rsp;
	}

	@Override
	public void enterRela(String enid) {
		List<String> childids = mapper.findChildids(enid);
		mapper.addRela(enid, enid);
		for (String id : childids) {
			childenter(id, enid);
		}

	}

	public void childenter(String id, String enid) {
		List<String> childids = mapper.findChildids(id);
		mapper.addRela(id, enid);
		for (String id1 : childids) {
			childenter(id1, enid);
		}
	}

	@Override
	public void deptRela(String enid, String dept) {
		List<String> childids = mapper.findDeptChildids(enid, dept);
		mapper.addDeptRela(dept, dept, enid);
		for (String id : childids) {
			childdept(id, dept, enid);
		}
	}

	public void childdept(String id, String dept, String enid) {
		List<String> childids = mapper.findDeptChildids(enid, id);
		mapper.addDeptRela(id, dept, enid);
		for (String id1 : childids) {
			childdept(id1, dept, enid);
		}
	}

	@Override
	public RspMiniOem oemInfo(String id) {
		RepParentEnter enter = mapper.parentEnter(id);
		if (enter == null) {
			return null;// mapper.oemInfos("44e7ce2e-570c-4524-a033-460acb6980b3");//默认
		} else if (enter.getRole() == 4) {
			return mapper.oemInfos(enter.getId());
		} else {
			return oemInfo(enter.getId());
		}
	}

	@Override
	public String findCropId(String enterpriseId) {
		Deploy deploy = deployMapper.selectAll(enterpriseId);
		return deploy.getCorpid();
	}

	@Override
	public List<Enterprise> getEnterpriseListByWxId(String wxId) {
		List<Enterprise> enterprisesList = mapper.selectEnterprisetListByWxId(wxId);
		return enterprisesList;
	}

	/**
	 * @param enterpriseId
	 * @param num 需要修改的人数 正数代表加人，负数代表减人
	 */
	@Override
	public void updataEnterpriseStaffNum(String enterpriseId, int num) {
		mapper.updateEnterpriseStaffNum(enterpriseId, num);
	}
}
