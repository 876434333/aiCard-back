package com.vma.push.bi.dao;

import com.vma.push.bi.entity.BiActionLog;
import com.vma.push.bi.entity.BiUserLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by chenzui on 2018/5/19.
 */
public interface BiActionLogMapper extends BaseDao<BiActionLog,String>{

    public Long queryActionNum(@Param("staffId") String staffId, @Param("actionCode")String actionCode);

//    public Long countAction(Map param);

    //public List<DataItem> countEveryAction(@Param("enterpriseId") String enterpriseId, @Param("departmentId") String departmentId);

//    public Long countActionsByStaff(Map param);

    //public List<RspStaffAnalysis> countAllActionNum(Map param);
}
