package com.vma.push.business.dao;

import com.vma.push.business.entity.CircleImg;

import java.util.List;

/**
 * Created by huxiangqiang on 2018/4/25.
 */
public interface CircleImgMapper extends BaseDao<CircleImg,String> {
    /**
     * 通过朋友圈id删除对应的图片
     * @param circleid
     */
    void deleteByCircleId(String circleid);
    List<String> getCircleImg(String id);


}
