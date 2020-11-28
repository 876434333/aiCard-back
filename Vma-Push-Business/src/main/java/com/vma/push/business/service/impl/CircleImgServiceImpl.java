package com.vma.push.business.service.impl;

import com.vma.push.business.dao.CircleImgMapper;
import com.vma.push.business.entity.CircleImg;
import com.vma.push.business.service.ICircleImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huxiangqiang on 2018/4/25.
 */
@Service
public class CircleImgServiceImpl implements ICircleImgService {

    @Autowired
    private CircleImgMapper circleImgMapper;
    @Override
    public int deleteByPrimaryKey(String id) {
        return 0;
    }

    @Override
    public int insert(CircleImg record) {
        return 0;
    }

    @Override
    public int insertSelective(CircleImg record) {
        return circleImgMapper.insertSelective(record);
    }

    @Override
    public CircleImg selectByPrimaryKey(String id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(CircleImg record) {
        return circleImgMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CircleImg record) {
        return 0;
    }
}
