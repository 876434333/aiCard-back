package com.vma.push.business.dao;

import com.vma.push.business.dto.req.ReqActionCode;
import com.vma.push.business.dto.req.ReqActionData;
import com.vma.push.business.dto.req.ReqInteract;
import com.vma.push.business.dto.req.ReqInteractDetail;
import com.vma.push.business.dto.req.userInfo.ReqCountAction;
import com.vma.push.business.dto.req.userInfo.ReqLog;
import com.vma.push.business.dto.rsp.RspActionDetail;
import com.vma.push.business.dto.rsp.RspActionLog;
import com.vma.push.business.dto.rsp.RspInteract;
import com.vma.push.business.dto.rsp.RspInteractDetail;
import com.vma.push.business.dto.rsp.actionLog.UserActionLog;
import com.vma.push.business.dto.rsp.userInfo.DataItem;
import com.vma.push.business.dto.rsp.userInfo.RspActionData;
import com.vma.push.business.entity.ClickActionLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by huxiangqiang on 2018/4/27.
 */
public interface ClickActionLogMapper extends BaseDao<ClickActionLog,String> {

    public Long queryActionNum(@Param("enterpriseId")String enterpriseId, @Param("staffId") String staffId,@Param("actionCode") String actionCode);

    public Long queryActionNumByUser(@Param("staffId") String staffId,@Param("actionCode") String actionCode,@Param("userId")String userId);

    public List<RspActionLog> getActionListByUser (@Param("staffId") String staffId);

    /**
     * 根据userId,staffId查询
     * @param reqLog
     * @return
     */
    public List<UserActionLog> queryUserActionLog(ReqLog reqLog);
    public List<UserActionLog> queryFollowLog(ReqLog reqLog);


    public DataItem countActionByUser(ReqCountAction reqCountAction);

    /**
     * 查询用户过去十五日的动作统计
     * @param userId
     * @param staffId
     * @param enterpriseId
     * @return
     */
    public List<DataItem> countUserActionByDay(@Param("userId") String userId,@Param("staffId") String staffId,@Param("enterpriseId") String enterpriseId);

    Long findNum(@Param("enterpriseId")String enterpriseId, @Param("actionCode")String actionCode1016, @Param("offerId")String offerId);

    Long queryActionNumAll(@Param("enterpriseId") String enterpriseId, @Param("actionCode") String actionCode1002);//企业点赞数

    List<RspActionData> actionDataByStaff(ReqActionData reqActionData);
    String cancelZan(ReqActionData reqActionData);

    List<RspActionDetail> actionDetail(ReqActionCode reqActionCode);
    List<RspInteract> interactDataByStaff(ReqInteract reqInteract);
    List<RspInteractDetail> reqInteractDetail(ReqInteractDetail reqInteractDetail);


    public void handoverData(@Param("toId") String toId,@Param("fromId") String fromId);
}
