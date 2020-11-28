package com.vma.push.business.service;

import com.vma.push.business.dto.req.ReqClickInfo;
import com.vma.push.business.dto.rsp.RspActionLog;
import com.vma.push.business.dto.rsp.RspPage;
import com.vma.push.business.entity.ClickActionLog;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by huxiangqiang on 2018/4/27.
 */
public interface IClickActionLogService extends IBaseService<ClickActionLog,String> {

    /**
     * 记录用户点击事件，并发送消息通知销售人员
     * @param reqClickInfo,request
     */
    public void  click(ReqClickInfo reqClickInfo, HttpServletRequest request);


    /**
     * 通过staffId获取名片被查看记录
     * @param staffid
     * @return
     */
    public RspPage<RspActionLog> getActionListByUser(String staffid,Integer pageNum, Integer pageSize);
}
