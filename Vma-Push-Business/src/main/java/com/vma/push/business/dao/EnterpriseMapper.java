package com.vma.push.business.dao;

import com.vma.push.business.dto.req.*;
import com.vma.push.business.dto.req.customer.ReqApplyPage;
import com.vma.push.business.dto.req.enterprise.ReqEnterpriseHistoryNumber;
import com.vma.push.business.dto.req.enterprise.ReqEnterpriseNumber;
import com.vma.push.business.dto.rsp.*;
import com.vma.push.business.dto.rsp.customer.RspApplyList;
import com.vma.push.business.dto.rsp.mini.ResGetSameCompanyNameList;
import com.vma.push.business.dto.rsp.staff.RspIndex;
import com.vma.push.business.dto.rsp.superback.RspEnterpariseList;
import com.vma.push.business.entity.Enterprise;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by ChenXiaoBin
 * 2018/5/4 6:06
 */
@Service
public  interface EnterpriseMapper extends BaseDao<Enterprise,String> {

    RspPointDetail pointDetail(String id);

    List<RspAllEnterprise> findAllEnterprise(ReqEnterpriseSelect reqEnterpriseSelect);//查询企业列表（超级管理

    Enterprise selectByManagerPhone(String manager_phone);//

    List<RspAccount> findAllAccount(ReqAccountSelect reqAccountSelect);//查询所有账号

    String findCropID(String id);//根据企业id查询scrop

    String getBosssecret(String id);//获取boss雷达的token

    String findAisecret(String id);//获取ai雷达的token

    String getContactssecret(String id);//获取通讯录的token

    //RspEnterpriseWei findWei(String enterise_id);//根据企业id查询

    String findBoosAgentId(String id);

    String findAiAgentId(String id);
    String findAppId(String id);
    String findsecret(String id);

    RspEnterpriseAllId selectEnterpriseId(String id);

    AllSecret AllSecret(@Param("id") String id);

    public List<Enterprise> findAll();

    RspIm findIm(@Param("enterprise_id") String entrprise_id);//

    String findmsgtmp(String id);

    List<RspApplyList> applyList(ReqApplyPage reqApplyPage);
    //下级列表
    List<RspEnterList> enterList(ReqEnterList reqEnterList);

    List<RspEnterDetailList> enterDetailList(ReqEnterList reqEnterList);

    List<RspEnterpariseList> enterpriseList(ReqEnterList reqEnterList);
    //贴牌商信息
    RspOemInfo oemInfo(String id);
    //下级地区总代理数
    Integer areacount(String id);
    //下级代理数
    Integer agentcount(String id);

    //下级企业数
    Integer entercount(String id);
    //地区总代信息
    RspAreaInfo areaInfo(String id);

    //代理商信息
    RspAgentInfo agentInfo(String id);

    //企业信息

    RspEnterInfo enterInfo(String id);

    Integer findFirstAgentNumber();

    Integer findSecondAgentNumber(ReqEnterpriseNumber req);

    Integer findThirdAgentNumber(ReqEnterpriseNumber req);

    Integer findEnterpriseNumber(ReqEnterpriseNumber req);

    Integer findEnterpriseHistoryNumber(ReqEnterpriseHistoryNumber req);

    Enterprise getEntpById(String id); //通过id查询客户信息

    List<RspTemplate> findTemplate();
    String provinceName(@Param("pcode") String pcode);
    String cityName(@Param("pcode") String pcode,@Param("ccode") String ccode);
    String areaName(@Param("pcode") String pcode,@Param("ccode") String ccode,@Param("acode") String acode);
    void proChildEnterpriseId(@Param("enid") String enid);
    void proChildDepartmentId(@Param("deptid") String deptid,@Param("enid") String enid);
    String getenterByAppid(String appid);
    int getEnterpriseCount(String appid);

    List<String> findChildids(String enid);

    Integer addRela(@Param("id")String id, @Param("enid")String enid);

    List<String> findDeptChildids(@Param("enid")String enid,@Param("dept")String dept);

    void addDeptRela(@Param("id") String id, @Param("dept") String dept, @Param("enid") String enid);

    Integer checkkey(@Param("appid") String appid,@Param("secret") String secret,@Param("enid") String enid);

    String apiUrlByAppid(String appid);

    BigDecimal findDiscount(String enterprise_id);

    String findEnterpriseName(String enterprise_id);//获取企业名称

    void uodateDepartName(@Param("name")String name, @Param("id")String id);

    RspIndex cardAndAcount(String enterpriseId);

    Integer findCusNum(String enterpriseId);

    Integer findStaffNun(String enterpriseId);

    Integer findCardUsedNum(String enterpriseId);

    RspApiAndImgUrl apiImgUrlByAppid(String appid);

    RepParentEnter parentEnter(String id);

    RspMiniOem oemInfos(String id);
    List<com.vma.push.business.dto.rsp.mini.RspEnterpriseList> getEnterpriseListByOpenid(@Param("openid")String openid);

    /**
     * 查看用户所在企业列表
     * Created by ljh
     * 2018/10/23 10:20
     */
    List<Enterprise> selectEnterprisetListByOpenId(@Param("openId")String openId);

    // 根据企业微信ID，找出所有企业
    List<Enterprise> selectEnterprisetListByWxId(@Param("wxId")String wxId);

    List<ResGetSameCompanyNameList> getSameCompanyNameList(@Param("name")String name);

    Integer updateEnterpriseStaffNum(@Param("enterpriseId")String enterpriseId,@Param("num")Integer num);
}
