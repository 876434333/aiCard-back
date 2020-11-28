package com.vma.push.business.dao;

import com.vma.push.business.dto.req.ReqCircleComment;
import com.vma.push.business.dto.rsp.RspCircleCommentFromCompany;
import com.vma.push.business.dto.rsp.RspCommentInfo;
import com.vma.push.business.entity.CircleCommentLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by huxiangqiang on 2018/4/25.
 */
public interface CircleCommentLogMapper extends BaseDao<CircleCommentLog,String> {
    List<RspCommentInfo> getCommentInfo(String id);

    /**
     * 通过朋友圈id删除对应的评论
     * @param id
     */
    void deleteCommentByCircleId(String id);

    /**
     * 朋友圈表中的评论记录+1
     * @param id
     */
    void addcomment(String id);

    /**
     * 朋友圈表中的评论记录-1
     * @param id
     */
    void delcomment(@Param("id") String id);

    List<RspCircleCommentFromCompany> getAllComment(ReqCircleComment req);
}
