package com.vma.push.business.service;

import com.vma.push.business.dto.req.ReqModifyPwd;
import com.vma.push.business.dto.req.ReqSystemLogin;
import com.vma.push.business.dto.req.ReqUpdatePwd;
import com.vma.push.business.dto.rsp.ai.CompanyList;
import com.vma.push.business.dto.rsp.userInfo.RspWebInfo;
import com.vma.push.business.entity.Admin;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * created by linzh on 2018/6/14
 */
public interface ILoginService extends IBaseService<Admin,String>{
    public Admin getByPhone(String phone);//通过电话查找用户信息

    /**
     * 找回密码
     * @param reqUpdatePwd
     * @return
     */
    public String updatePwd(ReqUpdatePwd reqUpdatePwd); //修改用户密码


    public Admin login(ReqSystemLogin reqSystemLogin,HttpServletRequest request);
    public RspWebInfo findWebInfo(Integer type);

    /**
     * 修改密码
     * @param reqModifyPwd
     * @param request
     */
    public void modifyPwd(ReqModifyPwd reqModifyPwd,HttpServletRequest request);

    /**
     * 控制台选择企业
     */
    List<CompanyList> getCompanyList(String phone);
}
