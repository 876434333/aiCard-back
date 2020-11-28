package com.vma.push.business.dao;

import com.vma.push.business.dto.req.ReqUpdateDepartInfo;
import com.vma.push.business.dto.rsp.RspDepartInfo;
import com.vma.push.business.dto.rsp.RspDepartPage;
import com.vma.push.business.dto.rsp.RspDepartTree;
import com.vma.push.business.dto.rsp.RspDepartTreeNew;
import com.vma.push.business.dto.rsp.mini.RepDepartmentList;
import com.vma.push.business.entity.Department;
import com.vma.push.business.entity.Staff;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by huxiangqiang on 2018/4/20.
 */
public interface DepartmentMapper extends BaseDao<Department,String> {

    List<RspDepartPage> getAll(@Param("enid") String enid);

    /**
     * 获取科室树
     * @param enid
     * @param pid
     * @return
     */
    List<RspDepartTree> DepartTree(@Param("enid") String enid, @Param("pid") String pid);

    /**
     * 获取科室树
     * @param enid
     * @param pid
     * @return
     */
    List<RspDepartTreeNew> DepartTreeNew(@Param("enid") String enid, @Param("pid") String pid);

    /**
     * 获取部门列表
     * @param enid
     * @return
     */
    List<RspDepartInfo> departinfo(String enid);

    public List<Department> findCond(@Param("id") String id);
    void updateDeptName(ReqUpdateDepartInfo reqUpdateDepartInfo);
    void updateDeptOnlyName(ReqUpdateDepartInfo reqUpdateDepartInfo);
    void delDept(@Param("id") String id,@Param("enter") String enter);

    /**
    * 获取父id对应最大的id
    */
    Integer getMaxIdByParenId(@Param("enterpriseId") String enterpriseId);

    /**
     * 获取部门列表及人数
     */
    List<RepDepartmentList> getDeparentListAndPerson(@Param("enterpriseId") String enterpriseId,@Param("parentId") String parentId);

}
