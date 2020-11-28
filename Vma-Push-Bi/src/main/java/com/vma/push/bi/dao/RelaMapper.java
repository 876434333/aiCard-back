package com.vma.push.bi.dao;

import com.vma.push.bi.dto.req.ReqAllId;
import com.vma.push.bi.dto.rsp.RspAllId;

import java.util.List;

/**
 * Created by ChenXiaoBin
 * 2018/5/16 14:36
 */
public interface RelaMapper {

    List<RspAllId> findAllId();

    int findCountByAllId(ReqAllId reqAllId);//查询总数

    void insertRate(ReqAllId reqAllId);//添加rate

}
