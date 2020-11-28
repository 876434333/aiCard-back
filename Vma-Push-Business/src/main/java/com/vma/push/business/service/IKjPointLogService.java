package com.vma.push.business.service;

import com.vma.push.business.dto.req.ReqPointNumber;
import com.vma.push.business.dto.req.superback.ReqAddPoint;
import com.vma.push.business.dto.req.superback.ReqAdminPage;
import com.vma.push.business.dto.req.superback.ReqPiontLogPage;
import com.vma.push.business.dto.rsp.RspDaysPointNumber;
import com.vma.push.business.dto.rsp.RspPointNumber;
import com.vma.push.business.dto.rsp.superback.RspAdmin;
import com.vma.push.business.dto.rsp.superback.RspCustomDetail;
import com.vma.push.business.dto.rsp.superback.RspPage;
import com.vma.push.business.dto.rsp.superback.RspPointLog;
import com.vma.push.business.entity.KjPointLog;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * created by linzh on 2018/6/14
 */
public interface IKjPointLogService extends IBaseService<KjPointLog,String>{
    /**
     * 添加迹点
     */
    public void addPoint(ReqAddPoint reqAddPoint);

    RspPointNumber getPointNumber(ReqPointNumber req);

    List<RspDaysPointNumber> getPointNumberByDayNumber(ReqPointNumber req);

    public RspPage<RspPointLog> findPointLogPage(ReqPiontLogPage reqPiontLogPage, HttpServletRequest request);//条件查询

    public RspPage<RspPointLog> findPointLogPage(ReqPiontLogPage reqPiontLogPage);//条件查询

    public RspCustomDetail getCustomDetail(String customId); //获得客户详细信息
}
