package com.vma.push.business.dao;

import com.vma.push.business.entity.OfferRecommend;

/**
 * Created by huxiangqiang on 2018/8/22.
 */
public interface OfferRecommendMapper extends BaseDao<OfferRecommend,String> {
    OfferRecommend checkOfferRecommendExist(OfferRecommend offerRecommend);
}
