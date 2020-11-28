package com.vma.push.business.service.impl;

import com.vma.push.business.dao.CircleZanLogMapper;
import com.vma.push.business.entity.CircleZanLog;
import com.vma.push.business.service.ICircleZanLogService;
import com.vma.push.business.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by huxiangqiang on 2018/4/25.
 */
@Service
public class CircleZanLogServiceImpl implements ICircleZanLogService {

    @Autowired
    private CircleZanLogMapper circleZanLogMapper;

    @Override
    public int deleteByPrimaryKey(String id) {
        return 0;
    }

    @Override
    public int insert(CircleZanLog record) {
        return 0;
    }

    @Override
    public int insertSelective(CircleZanLog record) {
        return circleZanLogMapper.insertSelective(record);
    }

    @Override
    public CircleZanLog selectByPrimaryKey(String id) {
        return circleZanLogMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(CircleZanLog record) {
        return circleZanLogMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CircleZanLog record) {
        return 0;
    }

    @Override
    public String zan(CircleZanLog circleZanLog) {
        //原先有点赞或者取消赞，则更新记录
        CircleZanLog z=circleZanLogMapper.isZan(circleZanLog);
        if (z!=null){
            z.setModifyTime(new Date());
            //获取原先状态，然后修改成与之不同的状态
            if (z.getStatus()==1){
                z.setStatus(0);
                circleZanLogMapper.delZan(circleZanLog.getCircleId());//朋友圈边点赞数+1
            }
            else{
                z.setStatus(1);
                circleZanLogMapper.addZan(circleZanLog.getCircleId());//朋友圈点赞数-1
            }
            updateByPrimaryKeySelective(z);
            return z.getId();
        }else{
            //从未点击赞，则向数据库添加赞的记录
            circleZanLog.setCreateTime(new Date());
            circleZanLog.setModifyTime(new Date());
            circleZanLog.setId(UuidUtil.getRandomUuid());
            circleZanLog.setStatus(1);
            insertSelective(circleZanLog);
            circleZanLogMapper.addZan(circleZanLog.getCircleId());
            return circleZanLog.getId();
        }
    }
}
