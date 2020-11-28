package com.vma.push.business.dao;

import com.vma.push.business.dto.req.ReqFunnel;
import com.vma.push.business.dto.rsp.boss.RspFunnel;
import com.vma.push.business.dto.rsp.boss.RspKeyValue;
import com.vma.push.business.dto.rsp.staff.RspStaffAnalysis;
import com.vma.push.business.dto.rsp.userInfo.DataItem;
import com.vma.push.business.entity.BiActionLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by chenzui on 2018/5/8.
 */
public interface BiActionLogMapper extends BaseDao<BiActionLog,String> {

    public Long queryActionNum(@Param("staffId") String staffId, @Param("actionCode")String actionCode);

    public Long countAction(Map param);

    public List<DataItem> countEveryAction(@Param("enterpriseId") String enterpriseId,@Param("departmentId") String departmentId);

    public Long countActionsByStaff(Map param);

    public List<RspStaffAnalysis> countAllActionNum(Map param);

    //n天前的值
    public Integer funnel(ReqFunnel reqFunnel);
    //同期n天前的值
    public Integer lastfunnel(ReqFunnel reqFunnel);

    List<RspKeyValue> money(ReqFunnel reqFunnel);
    List<RspKeyValue> order(ReqFunnel reqFunnel);

    List<DataItem> cusView(Map param);

    List<DataItem> clickView(Map param);
}
