package com.vma.push.business.service.impl;

import com.vma.push.business.common.ErrCode;
import com.vma.push.business.common.exception.BusinessException;
import com.vma.push.business.dao.AdminMapper;
import com.vma.push.business.dao.EnterpriseMapper;
import com.vma.push.business.dao.StaffMapper;
import com.vma.push.business.dto.req.ReqSystemLogin;
import com.vma.push.business.dto.req.enterprise.ReqModifyPhone;
import com.vma.push.business.dto.rsp.RspAdminLogin;
import com.vma.push.business.dto.rsp.RspEnterprise;
import com.vma.push.business.entity.Admin;
import com.vma.push.business.entity.Enterprise;
import com.vma.push.business.entity.Staff;
import com.vma.push.business.service.IBasicService;
import com.vma.push.business.util.UserDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by ChenXiaoBin
 * 2018/5/4 5:54
 */
@Service
public class BasicServiceImpl implements IBasicService {
    @Autowired
    private EnterpriseMapper enterpriseMapper;

    @Autowired
    private StaffMapper staffMapper;

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public int deleteByPrimaryKey(String id) {
        return enterpriseMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Enterprise record) {
        return enterpriseMapper.insert(record);
    }

    @Override
    public int insertSelective(Enterprise record) {
        return enterpriseMapper.insertSelective(record);
    }

    @Override
    public Enterprise selectByPrimaryKey(String id) {
        return enterpriseMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Enterprise record) {
        return enterpriseMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Enterprise record) {
        return enterpriseMapper.updateByPrimaryKey(record);
    }

    /**
     * 查看企业基本信息
     * @param enterpriseId
     * @return
     */
    @Override
    public RspEnterprise selectEnterprisetById(String enterpriseId) {
        Enterprise enterprise = enterpriseMapper.selectByPrimaryKey(enterpriseId);
        RspEnterprise rspEnterprise = new RspEnterprise();
        rspEnterprise.setId(enterprise.getId());
        rspEnterprise.setUsed_car_num(enterprise.getMoneySum()-enterprise.getMoneyLeave());//剩余
        rspEnterprise.setName(enterprise.getName());
        rspEnterprise.setAddress(enterprise.getAddress());
        rspEnterprise.setPhone(enterprise.getPhone());
        rspEnterprise.setBusiness_license_code(enterprise.getBusinessLicenseCode());
        rspEnterprise.setExpire_time(enterprise.getExpireTime());
        rspEnterprise.setSale_card_num(enterprise.getMoneySum());//名片总数
        rspEnterprise.setManager_name(enterprise.getManagerName());
        rspEnterprise.setManager_phone(enterprise.getManagerPhone());
        rspEnterprise.setPass_word(enterprise.getPassword());
        rspEnterprise.setHead_icon(enterprise.getHeadIcon());
        return rspEnterprise;
    }

    @Override
    public void modifyPhone(ReqModifyPhone reqModifyPhone, HttpServletRequest request) {
        String enterpriseId = UserDataUtil.getEnterpriseId(request);
        Enterprise enterprise = enterpriseMapper.selectByPrimaryKey(enterpriseId);
        String redisCode = UserDataUtil.getMsgCode(reqModifyPhone.getPhone());
        if(redisCode != null && !"".equals(redisCode)){
            if(!reqModifyPhone.getCode().equals(redisCode)){
                throw new BusinessException(ErrCode.MSG_CODE_ERROR,"验证码错误");
            }else {
                enterprise.setPhone(reqModifyPhone.getPhone());
//                enterprise.setPassword(reqModifyPhone.getPassword());
                enterpriseMapper.updateByPrimaryKeySelective(enterprise);
            }
        }else {
            throw new BusinessException(ErrCode.MSG_CODE_TIME_OUT,"请重新请求验证码");
        }

    }

    @Override
    public RspAdminLogin login(ReqSystemLogin reqSystemLogin) {
        //Enterprise enterprise = enterpriseMapper.selectByManagerPhone(reqSystemLogin.getPhone());
        Admin admin = adminMapper.selectLogin(reqSystemLogin.getPhone());
        RspAdminLogin rspAdminLogin = new RspAdminLogin();
        if(admin == null){
            throw new BusinessException(ErrCode.NO_PHONE_EXIST,"账号不存在");
        }else {
            if(admin.getPassWord().equals(reqSystemLogin.getPass_word())){
                if (admin.getStatus().equals(0)){
                    throw new BusinessException(ErrCode. LOSE_NUM,"账号无效");
                } else if (admin.getStatus().equals(2)){
                    throw new BusinessException(ErrCode. LOSE_NUM,"账号已过期");
                }
                rspAdminLogin.setCustomId(admin.getCustomId());
                rspAdminLogin.setId(admin.getId());
                return rspAdminLogin;
            }else {
                throw new BusinessException(ErrCode.ERROR_PASSWORD,"密码错误");
            }
        }

    }

    @Override
    public void updateCardUsedInfo(String enterpriseId,Staff staff) {
        Integer used = staffMapper.queryUsedCardNum(enterpriseId);//已用绩点（名片）数
        Enterprise enterprise = enterpriseMapper.selectByPrimaryKey(enterpriseId);
        Integer total =  enterprise.getMoneySum();//累计绩点数（总名片数）
        if (total-used<0){
            staff.setOpenAi(0);
            staff.setOpenBoss(0);
            staffMapper.updateByPrimaryKeySelective(staff);
            throw new BusinessException(ErrCode.OUT_CARD,"名片已用完");
        }else if(total-used==0){
            enterprise.setMoneyLeave(0);//剩余绩点/名片数
            enterpriseMapper.updateByPrimaryKeySelective(enterprise);
            throw new BusinessException(ErrCode.OUT_CARD,"名片即将用完");
        } else {
            //enterprise.setUsedCarNum(used);
            enterprise.setMoneyLeave(enterprise.getMoneySum()-used);//剩余绩点数
            enterpriseMapper.updateByPrimaryKeySelective(enterprise);
        }

    }

    @Override
    public List<Enterprise> selectEnterprisetListByOpenId(String openId) {
        return enterpriseMapper.selectEnterprisetListByOpenId(openId);
    }


    //@Override
//    public RspEnterprise selectEnterprisetById(String id) {
//        String enterpriseId = staffInfoMapper.findEnterpriseId(id);
//        Staff staff = staffInfoMapper.selectByPrimaryKey(id);
//        Enterprise enterprise = enterpriseMapper.selectByPrimaryKey(enterpriseId);
//       RspEnterprise rspEnterprise = new RspEnterprise();
//       rspEnterprise.setAddress(enterprise.getAddress());
//       rspEnterprise.setPhone(enterprise.getPhone());
//       rspEnterprise.setBusiness_license_code(enterprise.getBusinessLicenseCode());
//       rspEnterprise.setExpiry_time(enterprise.getExpiryTime());
//       rspEnterprise.setSale_card_num(enterprise.getSaleCardNum());
//       rspEnterprise.setManager_name(enterprise.getManagerName());
//       rspEnterprise.setManager_phone(enterprise.getManagerPhone());
//       rspEnterprise.setPass_word(staff.getPassWord());
//        return rspEnterprise;
//    }
}
