package com.vma.push.business.dao;

import com.vma.push.business.dto.req.ReqGetByRate;
import com.vma.push.business.dto.rsp.RspUserByRate;
import com.vma.push.business.dto.rsp.userInfo.DataItem;
import com.vma.push.business.entity.BiUserLog;

import java.util.List;
import java.util.Map;

/**
 * Created by chenzui on 2018/5/16.
 */
public interface BiUserLogMapper extends BaseDao<BiUserLog,String> {

    public List<DataItem> countUserAdd(Map param);

    public List<DataItem> countUserAttach(Map param);

    public List<DataItem> countUserIm(Map param);

    public List<DataItem> countOrder(Map param);

    List<RspUserByRate> getUserByRate(ReqGetByRate reqGetByRate);//根据rate查询用户（boss雷达）
}
