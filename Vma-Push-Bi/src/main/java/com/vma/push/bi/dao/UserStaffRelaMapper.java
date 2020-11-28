package com.vma.push.bi.dao;

import com.vma.push.bi.entity.UserStaffRela;
import org.apache.ibatis.annotations.Param;

/**
 * Created by chenzui on 2018/5/19.
 */
public interface UserStaffRelaMapper extends BaseDao<UserStaffRela,String> {
    public Long countNum(@Param("staffId") String staffId,@Param("departmentId") String departmentId,@Param("enterpriseId") String enterpriseId,
                         @Param("date") String date);
}
