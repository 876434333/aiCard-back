package com.vma.push.bi.dao;

import com.vma.push.bi.entity.Staff;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by chenzui on 2018/5/19.
 */
public interface StaffMapper extends BaseDao<Staff,String> {

    public List<Staff> findCond(@Param("enterpriseId") String enterpriseId,@Param("departmentId") String departmentId);
}
