package com.vma.push.business.service;

import com.vma.push.business.dto.req.*;
import com.vma.push.business.dto.req.enterprise.ReqEnterpriseAllId;
import com.vma.push.business.dto.req.superback.ReqCardNum;
import com.vma.push.business.dto.req.superback.ReqEditEnterprise;
import com.vma.push.business.dto.req.superback.ReqUpdateEnterStatus;
import com.vma.push.business.dto.req.superback.ReqUpdatePassword;
import com.vma.push.business.dto.rsp.*;
import com.vma.push.business.dto.rsp.superback.RspEnterpariseList;
import com.vma.push.business.entity.Enterprise;
import org.bouncycastle.cert.ocsp.Req;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * Created by ChenXiaoBin
 * 2018/5/6 20:53
 */
public interface ISuperEnterpriseService extends IBaseService<Enterprise,String>{

    RspPage<RspAllEnterprise> findAllEnterprise(ReqEnterpriseSelect reqEnterpriseSelect,String AdminId);//企业信息列表（超级管理后台）

    RspAllEnterprise findEnterpriseById(String id);

    void updateEnterprise(ReqUpdateEnterprise reqUpdateEnterprise);//修改企业信息

    void addEnterprise(ReqAddEnterprise reqAddEnterprise,String id);

    void updateEnterseAllId(ReqEnterpriseAllId reqEnterpriseAllId);

    RspEnterpriseAllId selectEnterpriseId(String id);

    RspPage<RspEnterpriseAllId> selectEnterpriseIdList(String adminId);

    RspIm findIm(String enterprise_id);

    /**
     * 获取下级列表
     * @param reqEnterList
     * @return
     */
    RspPage<RspEnterList> enterList(ReqEnterList reqEnterList,HttpServletRequest request);

    RspPage<RspEnterDetailList> enterDetailList(ReqEnterList reqEnterList);

    /**
     * 获取企业列表  单独处理
     * @param reqEnterList
     * @return
     */
    RspPage<RspEnterpariseList> enterpariseList(ReqEnterList reqEnterList);

    /**
     * 贴牌商信息
     * @param id
     * @return
     */
    RspOemInfo oemInfo(String id);

    /**
     * 地区总代信息
     * @param id
     * @return
     */
    RspAreaInfo areaInfo(String id);

    /**
     * 代理商信息
     * @param id
     * @return
     */
    RspAgentInfo agentInfo(String id,HttpServletRequest request);

    /**
     * 企业信息
     * @param id
     * @return
     */
    RspEnterInfo enterInfo(String id);

    /**
     * 增加贴牌商、代理商等。。。
     * @param reqEnterList
     */
    void addEnterAndAdmin(com.vma.push.business.dto.req.superback.ReqAddEnterprise reqEnterList);

    /**
     * 编辑贴牌商、代理商等。。。
     * @param reqEnterList
     */
    void editEnterAndAdmin(ReqEditEnterprise reqEnterList);

    /**
     * 添加名片
     * @param reqCardNum
     */
    void addCard(ReqCardNum reqCardNum,HttpServletRequest request);

    void updatePassword(ReqUpdatePassword reqUpdatePassword);

    void updateStatus(ReqUpdateEnterStatus reqUpdateEnterStatus);

    /**
     * 获取模板列表
     * @return
     */
    List<RspTemplate> findTemplate();

    void updateDeploy(ReqUpdateDeploy reqUpdateDeploy);
}
