package com.vma.push.business.service.impl;

import com.vma.push.business.common.ErrCode;
import com.vma.push.business.common.exception.BusinessException;
import com.vma.push.business.dao.AdminMapper;
import com.vma.push.business.dto.req.ReqModifyPwd;
import com.vma.push.business.dto.req.ReqSystemLogin;
import com.vma.push.business.dto.req.ReqUpdatePwd;
import com.vma.push.business.dto.rsp.ai.CompanyList;
import com.vma.push.business.dto.rsp.userInfo.RspWebInfo;
import com.vma.push.business.entity.Admin;
import com.vma.push.business.entity.KjPointLog;
import com.vma.push.business.service.ILoginService;
import com.vma.push.business.util.MD5Helper;
import com.vma.push.business.util.RandomValidateCodeUtil;
import com.vma.push.business.util.UserDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created by linzh on 2018/6/14
 */
@Service
public class LoginServiceImp extends AbstractServiceImpl<Admin,String> implements ILoginService {
    @Autowired
    private AdminMapper adminMapper;

    public LoginServiceImp(AdminMapper mapper) {
        super(mapper);
        this.adminMapper = mapper;
    }
    @Override
    public Admin getByPhone(String phone){
        return adminMapper.selectByPhone(phone);
    }

    @Override
    public RspWebInfo findWebInfo(Integer type) {
        return adminMapper.findWebInfo(type);
    }

    @Override
    public String updatePwd(ReqUpdatePwd reqUpdatePwd){
        String phone = reqUpdatePwd.getPhone();
        //验证码
        String msgCode = UserDataUtil.getMsg(phone);
        //验证码正确
        if(reqUpdatePwd.getMsg_code().equals(msgCode)){
//            Admin user = this.getByPhone(phone);
            List <Admin> adminList = adminMapper.selectAdminListByPhone(phone);
            for (Admin admin: adminList) {
                admin.setPassWord(reqUpdatePwd.getPass_word());
                adminMapper.updateByPrimaryKey(admin);
            }
            if(adminList == null || adminList.size()< 1){
                throw new BusinessException(ErrCode.NO_PHONE_EXIST,"账号未注册");
            }
        }else{
            throw new BusinessException(ErrCode.CHECK_CODE_ERROR,"验证码错误");
        }
        return "success";
    }

    @Override
    public Admin login(ReqSystemLogin reqSystemLogin,HttpServletRequest request) {
        String login = reqSystemLogin.getPhone();
        String type = reqSystemLogin.getType();
        //M by PLH at 2019-01-09 for 暂时本版本不验证验证码
        //if(checkVerify(request,reqSystemLogin.getCode())){
            Map param = new HashMap<>();
            param.put("login",login);
            param.put("type",type);
            Admin admin = adminMapper.find(param);

            if(admin!=null){
                if(admin.getStatus()!=0) {
                    if (admin.getPassWord().equals(reqSystemLogin.getPass_word())) {
                        admin.setLoginTime(new Date()); //修改登录时间
                        adminMapper.updateByPrimaryKeySelective(admin);
                        return admin;
                    } else {
                        throw new BusinessException(ErrCode.ERROR_PASSWORD, "密码错误");
                    }
                } else {
                    throw new BusinessException(ErrCode.ERROR_STATUS, "账号已禁用");
                }
            }else {
                throw new BusinessException(ErrCode.NO_PHONE_EXIST,"账号不存在");
            }
        //}else {
        //    throw new BusinessException(ErrCode.CHECK_CODE_ERROR,"验证码错误");
        //}
    }

    @Override
    public void modifyPwd(ReqModifyPwd reqModifyPwd, HttpServletRequest request) {
        String adminId = UserDataUtil.getAdminId(request);
        Admin admin = adminMapper.selectByPrimaryKey(adminId);
        if(admin.getPassWord().equals(reqModifyPwd.getOld_pwd())){
            if(!StringUtils.isEmpty(reqModifyPwd.getNew_pwd())){
                admin.setPassWord(reqModifyPwd.getNew_pwd());
                adminMapper.updateByPrimaryKeySelective(admin);
            }else {
                throw new BusinessException(ErrCode.ERROR_PWD,"请设置新密码");
            }
        }else {
            throw new BusinessException(ErrCode.ERROR_PWD,"旧密码错误");
        }
    }

    private boolean checkVerify(HttpServletRequest request , String code) {
        try{
            if(StringUtils.isEmpty(code)){
                return true;
            }
            String random = (String) request.getSession().getAttribute(RandomValidateCodeUtil.RANDOMCODEKEY);
            if (random == null) {
                return false;
            }
            if (random.equals(code)) {
                return true;
            } else {
                return false;
            }
        }catch (Exception e){
            LOG.error("验证码校验失败", e);
            return false;
        }
    }

    /**
     * 控制用户登录获取企业
     *
     * @param phone
     * @return
     */
    @Override
    public List<CompanyList> getCompanyList(String phone) {

        return adminMapper.getEnterpriseListByPhone(phone);
    }
}
