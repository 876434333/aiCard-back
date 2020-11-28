package com.vma.push.business.dao;

import com.vma.push.business.entity.UserLabelInfo;
import org.apache.ibatis.annotations.Param;

/**
 * Created by chenzui on 2018/5/15.
 */
public interface UserLabelInfoMapper extends BaseDao<UserLabelInfo,String> {
    void delete(@Param("userId") String userId,@Param("staffId") String staffId,@Param("labelId") String labelId);
}
