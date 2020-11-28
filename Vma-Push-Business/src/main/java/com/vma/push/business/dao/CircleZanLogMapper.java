package com.vma.push.business.dao;

import com.vma.push.business.dto.rsp.RspZanInfo;
import com.vma.push.business.entity.CircleZanLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by huxiangqiang on 2018/4/25.
 */
public interface CircleZanLogMapper extends BaseDao<CircleZanLog,String>{


    CircleZanLog isZan(CircleZanLog circleZanLog);

    /**
     * 获取点赞人的信息
     * @param id 朋友圈
     * @return
     */
    List<RspZanInfo> getZanInfo(String id);

    Integer isZanmini(@Param("circleId") String circleId, @Param("userId") String userId);

    Integer isZansale(@Param("circleId") String circleId, @Param("userId") String userId);
    /**
     * 通过朋友圈id删除点赞信息
     * @param id
     */
    void deleteZanByCircleId(String id);

    /**
     * 朋友圈表 赞+1
     * @param id
     */
    void addZan(String id);

    /**
     * 朋友圈表 赞-1
     * @param id
     */
    void delZan(String id);
}
