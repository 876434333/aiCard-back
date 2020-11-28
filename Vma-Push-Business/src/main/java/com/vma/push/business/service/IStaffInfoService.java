package com.vma.push.business.service;

import com.vma.push.business.dto.AttachmentAudio;
import com.vma.push.business.dto.AttachmentImage;
import com.vma.push.business.dto.CardResumeInfo;
import com.vma.push.business.dto.req.*;
import com.vma.push.business.dto.req.mini.ReqInvite;
import com.vma.push.business.dto.req.staff.*;
import com.vma.push.business.dto.req.store.ReqDiscount;
import com.vma.push.business.dto.req.store.ReqStaffDiscount;
import com.vma.push.business.dto.rsp.*;
import com.vma.push.business.dto.rsp.mini.ResInvite;
import com.vma.push.business.dto.rsp.staff.*;
import com.vma.push.business.dto.rsp.staff.RspStaffOrder;
import com.vma.push.business.dto.rsp.staff.RspTemplate;
import com.vma.push.business.dto.rsp.store.RspDiscount;
import com.vma.push.business.dto.rsp.store.RspDiscountList;
import com.vma.push.business.dto.rsp.userInfo.RspActionData;
import com.vma.push.business.dto.rsp.userInfo.RspAnalysisData;
import com.vma.push.business.dto.rsp.userInfo.RspUserLabelInfo;
import com.vma.push.business.entity.Staff;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by huxiangqiang on 2018/4/23.
 */
public interface IStaffInfoService extends IBaseService<Staff,String> {
    /**
     * 新增人员（微信api）
     *
     * @param employee 部门对象
     * @return
     */
    String insertApi(Employee employee,String enterprise_id);

    /**
     * 通过企业id和员工id获取员工信息
     * @param enid
     * @param id
     * @return
     */
    RspEnStaff queryStaff(String id,String enid);
    /**
     * 修改人员（微信api）
     *
     * @param employee 部门对象
     * @return
     */
    String updateApi(Employee employee,String enterprise_id);

    /**
     * 通过id删除人员（微信api）
     *
     * @return
     */
    String deleteApi(String id,String enterprise_id);

    /**
     * 获取部门员工api
     *
     * @return
     */
    List<ReqAddWeiChatUser>  wechatUsers(String enterprise_id,String department_id);

    /**
     * 获取部门员工api
     *
     * @return
     */
    List<ReqAddWeiChatUser>  wechatUsers(String enterprise_id);

    /**
     * 新增员工
     *
     * @param reqAddStaffInfo
     */
    RspStaffId addStaff(ReqAddStaffInfo reqAddStaffInfo,String enterprise_id, boolean isSyncEntWx) throws IOException;

    /**
     * 修改员工信息
     *
     * @param reqUpdateStaffInfo
     */
    void updateStaff(ReqUpdateStaffInfo reqUpdateStaffInfo,String enterprise_id);

    /**
     * 删除人员
     *
     * @param id
     */
    void delStaff(String id,String wxid,String enterprise_id);

    /**
     * 获取所有的人员
     *
     * @return
     */
    RspPage<RspStaff> getAll(ReqStaffPage page,String enterpriseid);

    /**
     * 通过用户id获取其对应的销售人员列表
     *
     * @param id
     * @return
     */
    RspPage<RspStaff> staffListByUserId(String id, Integer pageNum, Integer pageSize);

    int selectByUserNameAndPassword(ReqSystemLogin reqSystemLogin);


    void updatePassword(Staff sta);//更新密码

    List<RspStaff> radar();//

    RspRadarPage<RspStaff> findStaffSelect(ReqStaffSelect reqStaffSelect);//条件查询

    Staff selectStaff(ReqSystemLogin reqSystemLogin);//账号密码查询对象

    //void updateRadar(staff staff);//更新boss雷达以及staff雷达


    /**
     * 获取名片明细
     * @param userId
     * @return
     */
    RspCardInfo getById(String enterpriseId,String staffId,String userId);

    /**
     * 通过部门id和企业id获取员工列表
     * @param enterpriseid
     * @return
     */
    List<RspStaff> getStaffByDepart(String enterpriseid,String id);

    /**
     * 获取名片简要信息 我的
     * @param enid
     * @return
     */
    RspCard cardInfo(String staffid,String enid);

    /**
     * 获取个人名片
     * @param
     * @return
     */
    editCard getCard(String staffid,String enid);

    /**
     * 修改个人名片
     * @param
     */
    void editCard(editCard editCard,HttpServletRequest request);

    List<RspStaffIntro> getintro(String staffid,String enid);
    void delintro(String id);

    Long countUser(Map param);


    RspPage<RspCarlist> findStaffList(ReqCarList reqCarList);

    /**
     * 获取企业名称
     * @param enid
     * @return
     */
    String departname(String enid,String id);

    /**
     * 查询销售人员雷达用户详情里的数据分析
     * @param staffId
     * @param enterpriseId
     * @return
     */
    public RspAnalysisData queryUserData(String staffId, String departmentId,String enterpriseId);

    /**
     * 工作交接
     * @param formid
     * @param toid
     * @param enterpriseId
     * @return
     */
    List<RspHandover> handover(String formid,String toid,String enterpriseId);

    /**
     * 修改个人介绍中的图片 语音
     * @param list
     */
    void intro(List<RspStaffIntro> list);

    /**
     *获取openid
     * @param repOpenId
     * @return
     */
    String getopenid(RepOpenId repOpenId);
    RspDefalutInfo defaultInfo(ReqOpenIdNew reqOpenIdNew);



    List<RspActionData> actionData(ReqActionData reqActionData);
    ResPageAction<RspActionDetail> actionDetail(ReqActionCode reqActionCode);
    ResPageAction<RspInteract> interactData(ReqInteract reqInteract);
    List<RspInteractDetail> interactDetail(ReqInteractDetail reqInteractDetail);

    /**
     * 获取销售人员签名
     * @param staffid
     * @param enterpriseid
     * @return
     */
    String getSig(String staffid,String enterpriseid);

    /**
     * 修改销售人员签名
     * @param staffid
     * @param enterpriseid
     * @return
     */
    void editSig(String content,String staffid,String enterpriseid);

    /**
     * 获取销售人员语音介绍
     * @param staffid
     * @param enterpriseid
     * @return
     */
    String getSigVideo(String staffid,String enterpriseid);

    /**
     * 修改销售人员语音介绍
     * @param staffid
     * @param enterpriseid
     * @return
     */
    void editSigVideo(String content,String staffid,String enterpriseid);

    RepCardNumber getCallingCardNumber(ReqCardNumber req);

    List<RspDaysCardNumber> getCallingCardNumberByDayNumber(ReqCardNumber req);
    void insertIntro(ReqAddIntro reqAddIntro);

    Staff findstaffBywxidAndEnid(String userid, String enterpriseid);

    RspStaffIcon getByHeadIcon(String id);

    RspEnterIconAndWxLogo findEnterIconAndWxLogo(String staffId);

    List<RspStaffOrder> staffOrderBy(ReqStaffOrder req);

    RspStaffOrderPage  customOfStaffDetail(ReqCustomList req);

    void updateTemplate(ReqAiUpdateTemp reqAiUpdateTemp);//修改名片接口模版

    RspTemplate queryCardTemlate(String enterpriseId, String staffId);

    RspDiscountList<RspDiscount> discountList(ReqDiscount reqDiscount);

    void setEnterDiscount(String enterpriseId, BigDecimal count);

    void setStaffDiscount(ReqStaffDiscount reqStaffDiscount);

    RspCustomCount customCount(ReqCustomCount reqCustomCount);

    Integer staffRadar(String id, String enterpriseid);

    RspPage<RspStaffOfferIntro> staffOfferIntro(Page page,String staffid, String enid);

    void getRefreshRader(String enterpriseId);

    RspCardInfo getStaffInfoByOpenid(String openid,HttpServletRequest request);

    /**
     * Created by ljh on 2018/10/24.
     * begin
     */

    List<RspUserLabelInfo> getStaffLabelAndReferLabelInfo(String staffId, String userId);

    List<AttachmentImage> getStaffImages(String staffId);

    AttachmentAudio getStaffAudio(String staffId);

    Integer saveResume(CardResumeInfo cardResumeInfo);

    //保存我的个人简介
    Integer saveStaffSignature(String staffId,String signature);

    //保存我的标签
    Integer saveStaffLabels(String staffId, String userId, RspUserLabelInfo userLabelInfo);

    //保存我的录音
    Integer saveStaffAudio(String staffId,AttachmentAudio attachmentAudio);

    //保存我的照片
    Integer saveStaffImages(String staffId, List<AttachmentImage> images);

    /**
     * Created by ljh on 2018/10/24.
     * end
     */
    ResInvite getInviteInfo(ReqInvite ReqInvite);
    /**
     * 判断用户是否存在名片
     */
    Integer isExistCard(String openid);
}

