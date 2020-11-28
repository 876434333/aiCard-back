package com.vma.push.bi.dao;

import com.vma.push.bi.entity.Department;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by chenzui on 2018/5/19.
 */
public interface DepartmentMapper extends BaseDao<Department,String> {
    public List<Department> findCond(@Param("id") String id);
}
