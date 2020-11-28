package com.vma.push.business.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.vma.push.business.dao.CircleCommentLogMapper;
import com.vma.push.business.dto.req.ReqAddComment;
import com.vma.push.business.dto.req.ReqCircleComment;
import com.vma.push.business.dto.rsp.RspCirComFormCompanyPage;
import com.vma.push.business.dto.rsp.RspPage;
import com.vma.push.business.dto.rsp.staff.RspCarlist;
import com.vma.push.business.entity.CircleCommentLog;
import com.vma.push.business.service.ICircleCommentLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huxiangqiang on 2018/4/25.
 */
@Service
public class CircleCommentLogServiceImpl implements ICircleCommentLogService {

    @Autowired
    private CircleCommentLogMapper circleCommentLogMapper;

    @Override
    public int deleteByPrimaryKey(String id) {
        return circleCommentLogMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(CircleCommentLog record) {
        return 0;
    }

    @Override
    public int insertSelective(CircleCommentLog record) {
        return circleCommentLogMapper.insertSelective(record);
    }

    @Override
    public CircleCommentLog selectByPrimaryKey(String id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(CircleCommentLog record) {
        return circleCommentLogMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CircleCommentLog record) {
        return 0;
    }

    @Override
    public void circleComment(CircleCommentLog circleCommentLog) {
        if (insertSelective(circleCommentLog) > 0) {
            circleCommentLogMapper.addcomment(circleCommentLog.getCircleId());//朋友圈评论数+1
        }


    }

    @Override
    public void delComment(String id) {
        circleCommentLogMapper.delcomment(id);//朋友圈评论数-1
        circleCommentLogMapper.deleteByPrimaryKey(id);
    }

    @Override
    public RspCirComFormCompanyPage getAllComment(ReqCircleComment req){
        RspCirComFormCompanyPage rsp = new RspCirComFormCompanyPage();;
        Page page = PageHelper.startPage(req.getPage_num(),req.getPage_size(),true);
        rsp.setData_list(circleCommentLogMapper.getAllComment(req));
        rsp.setTotal_num(page.getTotal());
        rsp.setPage_num(page.getPageNum());
        rsp.setPage_size(page.getPageSize());
        return rsp;
    }


}
