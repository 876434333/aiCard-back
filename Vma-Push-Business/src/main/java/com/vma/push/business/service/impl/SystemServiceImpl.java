package com.vma.push.business.service.impl;

import com.vma.push.business.dao.PointRateMapper;
import com.vma.push.business.dao.RecommendCardMapper;
import com.vma.push.business.dto.req.superback.ReqAddRcdCard;
import com.vma.push.business.dto.req.superback.ReqGetRecommendCard;
import com.vma.push.business.dto.req.system.ReqAddPoinRate;
import com.vma.push.business.dto.req.system.ReqUpdatePoint;
import com.vma.push.business.dto.rsp.superback.RspGetRecommendCardList;
import com.vma.push.business.dto.rsp.system.RspPointRate;
import com.vma.push.business.entity.PointRate;
import com.vma.push.business.entity.RecommendCard;
import com.vma.push.business.service.ISystemService;
import com.vma.push.business.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Create By ChenXiAoBin
 * on 2018/6/14
 */
@Service
public class SystemServiceImpl implements ISystemService {
	@Autowired
	private PointRateMapper pointRateMapper;
	@Autowired
	private RecommendCardMapper recommendCardMapper;


//    /**
//     * 设置迹点价格
//     * @param reqAddPoinRate
//     */
//    @Override
//    public void addPointRate(ReqAddPoinRate reqAddPoinRate) {
//        //KjPointRate kjPointRate = reqAddPoinRate.toKjpoinRate();
//        pointRateMapper.insertSelective(reqAddPoinRate.toPoinRate());
//    }

	/**
	 * 查看迹点价格
	 *
	 * @return
	 */
	@Override
	public RspPointRate selectPoint() {
		RspPointRate rspPointRate = pointRateMapper.selectPoint();
		if (rspPointRate == null) {
			PointRate pointRate = new PointRate();
			pointRate.setId(UuidUtil.getRandomUuid());
			pointRate.setRegionalRate(Float.valueOf(0));
			pointRate.setOemRate(Float.valueOf(0));
			pointRate.setAgentRate(Float.valueOf(0));
			pointRate.setCreateTime(new Date());
			pointRateMapper.insertSelective(pointRate);
			RspPointRate r = pointRateMapper.selectPoint();
			return r;
		} else {
			return rspPointRate;
		}

	}

	/**
	 * 修改迹点价格
	 *
	 * @param reqUpdatePoint
	 */
	@Override
	public void updatePoint(ReqUpdatePoint reqUpdatePoint) {
		pointRateMapper.updateByPrimaryKeySelective(reqUpdatePoint.toPointRate());

	}


	@Override
	public int deleteByPrimaryKey(String id) {
		return 0;
	}

	@Override
	public int insert(PointRate record) {
		return 0;
	}

	@Override
	public int insertSelective(PointRate record) {
		return 0;
	}

	@Override
	public PointRate selectByPrimaryKey(String id) {
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(PointRate record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKey(PointRate record) {
		return 0;
	}

	/**
	 * 获取待推荐的人员列表
	 *
	 * @param reqGetRecommendCard
	 * @return
	 */
	@Override
	public List<RspGetRecommendCardList> getRecommendStaffList(ReqGetRecommendCard reqGetRecommendCard) {
		String enterpriseName = reqGetRecommendCard.getEnterprise_name();
		String phone = reqGetRecommendCard.getPhone();
		List<RspGetRecommendCardList> list = recommendCardMapper.getRecommendStaffList(enterpriseName, phone);
		return list;
	}

	/**
	 * 获取推荐名片列表
	 *
	 * @return
	 */
	@Override
	public List<RspGetRecommendCardList> getRecommendCardList() {
		return recommendCardMapper.getRecommendCardList();
	}

	/**
	 * 增加推荐名片
	 *
	 * @param reqAddRcdCard
	 */
	@Override
	public void addRcdCard(ReqAddRcdCard reqAddRcdCard) {
		List<String> staffIdList = reqAddRcdCard.getStaff_id_list();
		RecommendCard recommendCard = null;
		for (String staffId : staffIdList) {
			recommendCard = new RecommendCard();
			recommendCard.setId(UuidUtil.getRandomUuidWithoutSeparator());
			recommendCard.setStaffId(staffId);
			recommendCard.setViewNumber(0);
			recommendCard.setViewOrder(1);
			recommendCard.setCreateTime(new Date());
			recommendCard.setNotifyTime(new Date());
			recommendCard.setStatus(1);
			recommendCardMapper.insert(recommendCard);
		}
	}
	/**
	 * 删除推荐的名片
	 */
	@Override
	public void deleteRecommendCard(String recommendId) {
		RecommendCard recommendCard = recommendCardMapper.selectByPrimaryKey(recommendId);
		recommendCard.setStatus(0);
		recommendCard.setNotifyTime(new Date());
		recommendCardMapper.updateByPrimaryKeySelective(recommendCard);
	}
}
