package com.vma.push.bi.dao;

import com.vma.push.bi.entity.ClickAction;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * Created by chenzui on 2018/5/19.
 */
public interface ClickActionLogMapper extends BaseDao<ClickAction,String> {
    public Long count(@Param("actionCode") String actionCode,
                      @Param("staffId") String staffId,
                      @Param("departmentId") String departmentId,
                      @Param("enterpriseId") String enterpriseId,
                      @Param("date")String date);
}
