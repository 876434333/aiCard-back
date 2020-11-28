package com.vma.push.business.dao;

import com.vma.push.business.dto.req.ReqPointNumber;
import com.vma.push.business.dto.req.superback.ReqAdminPage;
import com.vma.push.business.dto.req.superback.ReqPiontLogPage;
import com.vma.push.business.dto.rsp.RspDaysPointNumber;
import com.vma.push.business.dto.rsp.RspPointNumber;
import com.vma.push.business.dto.rsp.superback.RspAdmin;
import com.vma.push.business.dto.rsp.superback.RspCustomCount;
import com.vma.push.business.dto.rsp.superback.RspCustomDetail;
import com.vma.push.business.dto.rsp.superback.RspPointLog;
import com.vma.push.business.entity.Enterprise;
import com.vma.push.business.entity.KjPointLog;

import java.util.List;

/**
 * created by linzh on 2018/6/14
 */
public interface KjPointLogMapper extends BaseDao<KjPointLog,String>{
    KjPointLog selectByCustomId(String customId);//条件查询迹点信息

    Long selectOperationPointByCustomId(ReqPointNumber req);

    List<RspDaysPointNumber> selectOperationPointByCustomIdAndDays(ReqPointNumber req);

    List<RspPointLog> findPage(ReqPiontLogPage reqPiontLogPage); //分页查询迹点日志信息

    RspCustomDetail getCustomDetail(String id);

    RspCustomCount getCustomCount(String id);
}
