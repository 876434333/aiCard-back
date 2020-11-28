package com.vma.push.business.service;

import com.vma.push.business.dto.req.superback.ReqAddRcdCard;
import com.vma.push.business.dto.req.superback.ReqGetRecommendCard;
import com.vma.push.business.dto.req.system.ReqAddPoinRate;
import com.vma.push.business.dto.req.system.ReqUpdatePoint;
import com.vma.push.business.dto.rsp.superback.RspGetRecommendCardList;
import com.vma.push.business.dto.rsp.system.RspPointRate;
import com.vma.push.business.entity.PointRate;

import java.util.List;

/**
 * Create By ChenXiAoBin
 * on 2018/6/14
 */
public interface ISystemService extends IBaseService<PointRate,String> {
    //void addPointRate(ReqAddPoinRate reqAddPoinRate);

    RspPointRate selectPoint();//

    void updatePoint(ReqUpdatePoint reqUpdatePoint);

    //void updatePoint(ReqUpdatePoint reqUpdatePoint);
    List<RspGetRecommendCardList> getRecommendStaffList(ReqGetRecommendCard reqGetRecommendCard);
    List<RspGetRecommendCardList> getRecommendCardList();
    void addRcdCard(ReqAddRcdCard reqAddRcdCard);
    void deleteRecommendCard(String staffId);
}
