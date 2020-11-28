package com.vma.push.business.service;

import com.vma.push.business.dto.req.ReqGetByRate;
import com.vma.push.business.dto.rsp.RspPage;
import com.vma.push.business.dto.rsp.RspUserByRate;
import com.vma.push.business.dto.req.ReqFunnel;
import com.vma.push.business.dto.rsp.boss.*;
import com.vma.push.business.dto.rsp.staff.RspAnalysis;
import com.vma.push.business.dto.rsp.staff.RspIndex;
import com.vma.push.business.dto.rsp.userInfo.DataItem;

import java.util.List;
import java.util.Map;

/**
 * Created by chenzui on 2018/5/16.
 */
public interface IBossService {
    /**
     * 查询用户数 departmentId,enterpriseId,day
     * @param param
     * @return
     */
    public Long countUser(Map param);

    public List<RspViewData> viewdata(Integer type,String staffId,String departmentId,String enterpriseId);

    public List<DataItem> countUserAdd(Map param);

    public List<DataItem> countUserAttach(Map param);

    public List<DataItem> countUserIm(Map param);

    public List<DataItem> countOrder(Map param);

    /**
     * 获取能力分析数据
     * @param enterpriseId
     */
    public RspAnalysis analysis(String enterpriseId,String deparmentId);

    RspPage<RspUserByRate> getUserByRate(ReqGetByRate reqGetByRate);
    /**
     * 成交率漏斗图
     * @param reqFunnel
     * @return
     */
    List<RspFunnel> funnel(ReqFunnel reqFunnel);

    RspOrderAndMoney orderAndMoney(ReqFunnel reqFunnel);

    RspUserInfo userInfo(String id, String enterpriseId);

    RspIndex cardAndAcount(String enterpriseId);

    List<DataItem> kpiView(Map param);

    RspUserCusInfo userCusInfo(String id, String enterpriseId);
}
