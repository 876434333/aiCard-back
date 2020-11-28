package com.vma.push.business.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.vma.push.business.dao.AdminMapper;
import com.vma.push.business.dao.DeployMapper;
import com.vma.push.business.dao.EnterpriseMapper;
import com.vma.push.business.dao.ShopTemplateMapper;
import com.vma.push.business.dto.req.customer.ReqApplyPage;
import com.vma.push.business.dto.rsp.RspPage;
import com.vma.push.business.dto.rsp.customer.RspApplyEnterprise;
import com.vma.push.business.dto.rsp.customer.RspApplyList;
import com.vma.push.business.entity.Deploy;
import com.vma.push.business.entity.Enterprise;
import com.vma.push.business.entity.ShopTemplate;
import com.vma.push.business.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create By ChenXiAoBin
 * on 2018/6/14
 */
@Service
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private EnterpriseMapper enterpriseMapper;
    @Autowired
    private ShopTemplateMapper shopTemplateMapper;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private DeployMapper deployMapper;
    @Override
    public int deleteByPrimaryKey(String id) {
        return 0;
    }

    @Override
    public int insert(Enterprise record) {
        return 0;
    }

    @Override
    public int insertSelective(Enterprise record) {
        return 0;
    }

    @Override
    public Enterprise selectByPrimaryKey(String id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(Enterprise record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Enterprise record) {
        return 0;
    }

    /**
     * 申请列表
     * @param reqApplyPage
     * @return
     */
    @Override
    public RspPage<RspApplyList> applyList(ReqApplyPage reqApplyPage) {
        RspPage<RspApplyList> rspPage = new RspPage<>();
        Page page = PageHelper.startPage(reqApplyPage.getPage_num(),reqApplyPage.getPage_size(),true);
        List<RspApplyList> list =  enterpriseMapper.applyList(reqApplyPage);
        rspPage.setData_list(list);
        rspPage.setTotal_num(page.getTotal());
        rspPage.setPage_num(page.getPageNum());
        rspPage.setPage_size(page.getPageSize());
        return  rspPage;
    }
    /**
     * 申请列表-企业详情
     * @param id
     * @return
     */
    @Override
    public RspApplyEnterprise detailById(String id) {
        Enterprise enterprise = enterpriseMapper.selectByPrimaryKey(id);
        ShopTemplate shopTemplate = shopTemplateMapper.selectByCode(enterprise.getTemplateId());
        Enterprise e= enterpriseMapper.selectByPrimaryKey(enterprise.getParentId());
        //Admin admin = adminMapper.selectByPrimaryKey(enterprise.getParentId());//查看创建人
        Enterprise a = enterpriseMapper.selectByPrimaryKey(enterprise.getParentId());//查看是否有有父类
        Deploy deploy = deployMapper.selectAll(id);//根据企业id查询deploy表
        RspApplyEnterprise rspApplyEnterprise = new RspApplyEnterprise();
        if (deploy != null){
            rspApplyEnterprise.setIs_deploy(enterprise.getIsDeploy());
            rspApplyEnterprise.setAddress(enterprise.getAddress());
            rspApplyEnterprise.setAi_agent_id(deploy.getAiAgentid());//ai_id
            rspApplyEnterprise.setAi_secret(deploy.getAisecret());//ai密钥
            rspApplyEnterprise.setApp_id(deploy.getAppId());//小程序id
            rspApplyEnterprise.setBoss_agent_id(deploy.getBossAgentid());//boss_id
            rspApplyEnterprise.setBoss_secret(deploy.getBosssecret());//boss密钥
            rspApplyEnterprise.setMessage_template(deploy.getMessageTemplate());//留言回复通知
            rspApplyEnterprise.setPay_template((deploy.getPayTemplate()));//支付回复通知
            rspApplyEnterprise.setCode(shopTemplate.getCode());//m模版编码
            rspApplyEnterprise.setContacts_secret(deploy.getContactssecret());
            rspApplyEnterprise.setCorp_id(deploy.getCorpid());
            rspApplyEnterprise.setCreate_name(e.getName());//创建人
            rspApplyEnterprise.setEnterprise_name(enterprise.getName());
            rspApplyEnterprise.setEnterprise_phone(enterprise.getPhone());
            rspApplyEnterprise.setId(enterprise.getId());
            rspApplyEnterprise.setManager_phone(enterprise.getManagerPhone());
            rspApplyEnterprise.setMch_id(deploy.getMchId());
            rspApplyEnterprise.setHead_icon(enterprise.getHeadIcon());//企业头像
            if (a != null){
                rspApplyEnterprise.setParent_enterprise(a.getName());
            }else{
                rspApplyEnterprise.setParent_enterprise("0");
            }
            rspApplyEnterprise.setPay_key(deploy.getPayKey());
            rspApplyEnterprise.setStatus(enterprise.getStatus());
            rspApplyEnterprise.setSecret(deploy.getSecret());
            rspApplyEnterprise.setSaleCardNum(enterprise.getSaleCardNum());
            rspApplyEnterprise.setCreate_time(enterprise.getCreateTime());
            rspApplyEnterprise.setRemark(enterprise.getRemark());
            //return rspApplyEnterprise;
        }else{
            rspApplyEnterprise.setIs_deploy(enterprise.getIsDeploy());
            rspApplyEnterprise.setAddress(enterprise.getAddress());
            rspApplyEnterprise.setCode(shopTemplate.getCode());//m模版编码
            rspApplyEnterprise.setCreate_name(e.getName());//创建人
            rspApplyEnterprise.setEnterprise_name(enterprise.getName());
            rspApplyEnterprise.setEnterprise_phone(enterprise.getPhone());
            rspApplyEnterprise.setId(enterprise.getId());
            rspApplyEnterprise.setManager_phone(enterprise.getManagerPhone());
            if (a != null){
                rspApplyEnterprise.setParent_enterprise(a.getName());
            }else{
                rspApplyEnterprise.setParent_enterprise("0");
            }
            rspApplyEnterprise.setStatus(enterprise.getStatus());
            rspApplyEnterprise.setSaleCardNum(enterprise.getMoneyInit());
            rspApplyEnterprise.setCreate_time(enterprise.getCreateTime());
            rspApplyEnterprise.setRemark(enterprise.getRemark());
            rspApplyEnterprise.setHead_icon(enterprise.getHeadIcon());//企业头像
            //return rspApplyEnterprise;

        }

        return rspApplyEnterprise;

    }
}
