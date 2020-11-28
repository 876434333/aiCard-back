package com.vma.push.business.dao;

import com.vma.push.business.entity.UserMenuRef;
import org.apache.ibatis.annotations.Param;

/**
 * created by linzh on 2018/6/14
 */
public interface UserMenuRefMapper extends BaseDao<UserMenuRef,String> {
    void deleRef(@Param("userId") String userId);
}
