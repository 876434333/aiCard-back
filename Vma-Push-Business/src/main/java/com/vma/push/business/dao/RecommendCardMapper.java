package com.vma.push.business.dao;

import com.vma.push.business.dto.rsp.mini.RepRecommendCard;
import com.vma.push.business.dto.rsp.superback.RspGetRecommendCardList;
import com.vma.push.business.dto.rsp.system.RspPointRate;
import com.vma.push.business.entity.PointRate;
import com.vma.push.business.entity.RecommendCard;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 推荐名片
 */
public interface RecommendCardMapper extends BaseDao<RecommendCard,String>{
    List<RspGetRecommendCardList> getRecommendStaffList(@Param("enterpriseName")String enterpriseName,@Param("phone")String phone);
    List<RspGetRecommendCardList> getRecommendCardList();
    List<RepRecommendCard> miniGetRecommendCard();
    RecommendCard getRecommendCardByStaffId(String staffId);
}
