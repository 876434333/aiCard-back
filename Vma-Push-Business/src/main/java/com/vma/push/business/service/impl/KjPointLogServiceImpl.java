package com.vma.push.business.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.vma.push.business.common.ErrCode;
import com.vma.push.business.common.exception.BusinessException;
import com.vma.push.business.dao.AdminMapper;
import com.vma.push.business.dao.EnterpriseMapper;
import com.vma.push.business.dao.KjPointLogMapper;
import com.vma.push.business.dto.req.ReqPointNumber;
import com.vma.push.business.dto.req.superback.ReqAddPoint;
import com.vma.push.business.dto.req.superback.ReqPiontLogPage;
import com.vma.push.business.dto.rsp.RepCardNumber;
import com.vma.push.business.dto.rsp.RspDaysPointNumber;
import com.vma.push.business.dto.rsp.RspPointNumber;
import com.vma.push.business.dto.rsp.superback.*;
import com.vma.push.business.entity.Enterprise;
import com.vma.push.business.entity.KjPointLog;
import com.vma.push.business.service.IKjPointLogService;
import com.vma.push.business.util.UserDataUtil;
import com.vma.push.business.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qiniu.happydns.Record;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
/**
 * created by linzh on 2018/6/14
 */
@Service
public class KjPointLogServiceImpl extends AbstractServiceImpl<KjPointLog,String> implements IKjPointLogService {
    @Autowired
    private KjPointLogMapper kjPointLogMapper;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private EnterpriseMapper enterpriseMapper;

    public KjPointLogServiceImpl(KjPointLogMapper mapper) {
        super(mapper);
        this.kjPointLogMapper = mapper;
    }
    @Transactional
    @Override
    public void addPoint(ReqAddPoint reqAddPoint){
        KjPointLog kjPointLog = new KjPointLog();//接收方获取的迹点——记录
        KjPointLog topKjPointLog = new KjPointLog();//我支出的迹点——记录
        if(reqAddPoint != null){
            kjPointLog.setCreateBy(reqAddPoint.getCreate_by());//操作人
            kjPointLog.setOperation(reqAddPoint.getOperation());//操作绩点加、减
            kjPointLog.setOperationPoint(reqAddPoint.getOperation_point());//操作绩点数
            kjPointLog.setCreateTime(new Date());//操作时间3
            kjPointLog.setCustomId(reqAddPoint.getCustom_id());//企业id
            kjPointLog.setId(UuidUtil.getRandomUuid());//ID
            kjPointLog.setTargetId(reqAddPoint.getEnterprise_id());
            //企业信息
            Enterprise enterprise = enterpriseMapper.selectByPrimaryKey(reqAddPoint.getCustom_id());
            if(enterprise != null){
                if(enterprise.getMoneyLeave() == null){
                    enterprise.setMoneyLeave(0);
                }
                //剩余迹点
                Float remainPoint = enterprise.getMoneyLeave().floatValue();
                if(kjPointLog.getOperation() != null){
                    Float newRemainPoint = 0F;
                    //1 增加迹点
                    if("1".equals(kjPointLog.getOperation())){
                        kjPointLog.setContent("平台增加迹点" + reqAddPoint.getContent());
                        newRemainPoint = remainPoint + kjPointLog.getOperationPoint();
                        topKjPointLog.setCreateBy(reqAddPoint.getCreate_by());//操作人
                        topKjPointLog.setOperation("2");//操作绩点加、减
                        topKjPointLog.setOperationPoint(reqAddPoint.getOperation_point());//操作绩点数
                        topKjPointLog.setCreateTime(new Date());//操作时间3
                        topKjPointLog.setId(UuidUtil.getRandomUuid());//ID
                        topKjPointLog.setContent("平台扣迹点(分配迹点)");
                        topKjPointLog.setTargetId(reqAddPoint.getCustom_id());
                        if (!enterprise.getParentId().equals("0")){
                            Enterprise e = enterpriseMapper.selectByPrimaryKey(reqAddPoint.getEnterprise_id());
                            topKjPointLog.setCustomId(e.getId());//企业id
                            Float point = e.getMoneyLeave().floatValue();//上级企业剩余绩点
                            Float newpoint = point- kjPointLog.getOperationPoint();//扣除绩点数后剩余的绩点数
                            topKjPointLog.setRemainPoint(newpoint);
                            kjPointLogMapper.insertSelective(topKjPointLog);
                            if (newpoint<0){
                                throw new BusinessException(ErrCode.NOT_ENOUGH_POINT,"迹点不足");
                            }
                            e.setMoneyLeave(newpoint.intValue());
                            enterpriseMapper.updateByPrimaryKeySelective(e);
                        }else{
                            topKjPointLog.setTargetId(reqAddPoint.getCustom_id());
                            topKjPointLog.setCustomId("0");//企业id
                            kjPointLogMapper.insertSelective(topKjPointLog);
                        }
                        enterprise.setMoneySum(reqAddPoint.getOperation_point().intValue()+enterprise.getMoneySum());
                    }else if("2".equals(kjPointLog.getOperation())){
                        kjPointLog.setContent("平台扣迹点"+reqAddPoint.getContent());
                        //扣减迹点
                        newRemainPoint = remainPoint - kjPointLog.getOperationPoint();
                        if(newRemainPoint<0){
                            throw new BusinessException(ErrCode.NOT_ENOUGH_POINT,"迹点不足");
                        }
                    }
                    kjPointLog.setRemainPoint(newRemainPoint.floatValue());
                    enterprise.setMoneyLeave(newRemainPoint.intValue()); //更新客户剩余迹点
                    enterpriseMapper.updateByPrimaryKeySelective(enterprise);
                }
            }
            kjPointLogMapper.insertSelective(kjPointLog);
        }
    }

    @Override
    public RspPointNumber getPointNumber(ReqPointNumber req){
        RspPointNumber rep = new RspPointNumber();
        req.setDayNumber(0);
        rep.setToday(kjPointLogMapper.selectOperationPointByCustomId(req));
        req.setDayNumber(1);
        rep.setYesterday(kjPointLogMapper.selectOperationPointByCustomId(req));
        req.setDayNumber(null);
        rep.setAll(kjPointLogMapper.selectOperationPointByCustomId(req));
        return rep;
    }
    @Override
    public List<RspDaysPointNumber> getPointNumberByDayNumber(ReqPointNumber req){
        return kjPointLogMapper.selectOperationPointByCustomIdAndDays(req);
    }

    @Override
    public RspPage<RspPointLog> findPointLogPage(ReqPiontLogPage reqPiontLogPage, HttpServletRequest request){
        enterpriseMapper.proChildEnterpriseId(reqPiontLogPage.getEnterprise_id());
        reqPiontLogPage.setEnterprise_id(UserDataUtil.getCustomId(request));
        //查询我支出的迹点or我对下级的操作
        RspPage<RspPointLog> rspPage = new RspPage<>();
        Page page = PageHelper.startPage(reqPiontLogPage.getPage_num(),reqPiontLogPage.getPage_size(),true);
        List<RspPointLog> rspPointLog = kjPointLogMapper.findPage(reqPiontLogPage);
        rspPage.setData_list(rspPointLog);
        rspPage.setTotal_num(page.getTotal());
        rspPage.setPage_num(page.getPageNum());
        rspPage.setPage_size(page.getPageSize());
        return rspPage;
    }

    @Override
    public RspPage<RspPointLog> findPointLogPage(ReqPiontLogPage reqPiontLogPage) {
        enterpriseMapper.proChildEnterpriseId(reqPiontLogPage.getEnterprise_id());
        RspPage<RspPointLog> rspPage = new RspPage<>();
        Page page = PageHelper.startPage(reqPiontLogPage.getPage_num(),reqPiontLogPage.getPage_size(),true);
        List<RspPointLog> rspPointLog = kjPointLogMapper.findPage(reqPiontLogPage);
        rspPage.setData_list(rspPointLog);
        rspPage.setTotal_num(page.getTotal());
        rspPage.setPage_num(page.getPageNum());
        rspPage.setPage_size(page.getPageSize());
        return rspPage;
    }

    @Override
    public RspCustomDetail getCustomDetail(String customId){
        RspCustomDetail customDetail = kjPointLogMapper.getCustomDetail(customId);
        RspCustomCount customCount = kjPointLogMapper.getCustomCount(customId);
        customDetail.setAgent_num(customCount.getAgent_num());
        customDetail.setArea_num(customCount.getArea_num());
        return customDetail;
    }
}
