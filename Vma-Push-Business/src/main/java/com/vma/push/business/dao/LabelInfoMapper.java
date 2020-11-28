package com.vma.push.business.dao;

import com.vma.push.business.dto.rsp.userInfo.RspUserLabelInfo;
import com.vma.push.business.entity.LabelInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by chenzui on 2018/5/14.
 */
public interface LabelInfoMapper extends BaseDao<LabelInfo,String>{

    List<RspUserLabelInfo> queryUserLabelInfo(@Param("userId") String userId,@Param("typeId") String typeId);

}
