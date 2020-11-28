package com.vma.push.business.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.vma.push.business.common.ErrCode;
import com.vma.push.business.common.exception.BusinessException;
import com.vma.push.business.dao.AdminMapper;
import com.vma.push.business.dao.EnterpriseMapper;
import com.vma.push.business.dto.req.ReqAccountSelect;
import com.vma.push.business.dto.req.ReqAddAccount;
import com.vma.push.business.dto.req.ReqUpdateAccount;
import com.vma.push.business.dto.rsp.RspAccount;
import com.vma.push.business.dto.rsp.RspAccountOne;
import com.vma.push.business.dto.rsp.RspAgent;
import com.vma.push.business.dto.rsp.RspPage;
import com.vma.push.business.entity.Admin;
import com.vma.push.business.entity.Enterprise;
import com.vma.push.business.service.ISuperStaffService;
import com.vma.push.business.util.UserDataUtil;
import com.vma.push.business.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by ChenXiaoBin
 * 2018/5/8 16:22
 */
@Service
public class SuperStaffServicerImpl implements ISuperStaffService {
 @Autowired
 private EnterpriseMapper enterpriseMapper;
@Autowired
 private AdminMapper adminMapper;
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
     * 获取所有账号
     * @param reqAccountSelect
     * @return
     */
    public RspPage<RspAccount> findAllAccount(ReqAccountSelect reqAccountSelect,String adminId){
        RspPage rspPage = new RspPage();
        Page page = PageHelper.startPage(reqAccountSelect.getPage_num(),reqAccountSelect.getPage_size(),true);
        //List<RspAccount> accounts =enterpriseMapper.findAllAccount(reqAccountSelect);
        Admin admin = adminMapper.selectByPrimaryKey(adminId);
        if(admin.getRole()== 0){
            reqAccountSelect.setAdminId(adminId);
            List<RspAccount> accounts = adminMapper.findAllAccount(reqAccountSelect);
            rspPage.setData_list(accounts);
            rspPage.setTotal_num(page.getTotal());
            rspPage.setPage_num(page.getPageNum());
            rspPage.setPage_size(page.getPageSize());
            return rspPage;
        }else{
            List<RspAccount> accounts = adminMapper.findAllAccount(reqAccountSelect);
            rspPage.setData_list(accounts);
            rspPage.setTotal_num(page.getTotal());
            rspPage.setPage_num(page.getPageNum());
            rspPage.setPage_size(page.getPageSize());
            return rspPage;
        }

    }

    @Override
    public Admin findAdmin(String adminId) {
        return null;
    }

    /**
     * 账号详情
     * @param id
     * @return
     */
    @Override
    public RspAccountOne findAccountById(String id) {
        Admin admin = adminMapper.selectByPrimaryKey(id);
        RspAccountOne rspAccountOne = new RspAccountOne();
        rspAccountOne.setPass_word(admin.getPassWord());
        rspAccountOne.setId(admin.getId());
        rspAccountOne.setName(admin.getName());
        rspAccountOne.setRole(admin.getRole());
        rspAccountOne.setStatus(admin.getStatus());
        rspAccountOne.setPhone(admin.getPhone());
        rspAccountOne.setPass_word(admin.getPassWord());
        rspAccountOne.setAgent(admin.getAgent());
        return rspAccountOne;
    }

    /**
     * 查询代理商列表
     * @return
     */
    @Override
    public List<RspAgent> findAllAgent() {
        return adminMapper.findAllAgent();
    }

    /**
     * 添加账号
     * @param reqAddAccount
     */
    @Override
    public void addAccount(ReqAddAccount reqAddAccount,String adminId) {
//       // String phone =  reqAddAccount.getPhone();
//        String passwrod = String.valueOf(System.currentTimeMillis()).substring(0,6);
//        String url =  "http://112.90.92.219:8811/sms.aspx?action=send&userid=1003&account=chenzui&password=chenzui123&mobile="+phone+"&content="+createContent(phone,passwrod)+"&sendTime=&extno=";
//        HttpUtil.sendGet(url);
        Admin admins = adminMapper.selectByPhone(reqAddAccount.getPhone());
        if (admins == null){
            Admin admin = new Admin();
            admin.setPassWord(reqAddAccount.getPass_word());//密码
            admin.setCreateId(adminId);//创建人的id
            admin.setId(UuidUtil.getRandomUuid());//主键id
            admin.setName(reqAddAccount.getName());//真实姓名
            admin.setRole(reqAddAccount.getRole());//角色
            admin.setAgent(reqAddAccount.getAgent());//代理商
            admin.setPhone(reqAddAccount.getPhone());//电话账号
            admin.setCreateTime(new Date());//创建时间
            adminMapper.insertSelective(admin);
        }else {
            throw new BusinessException(ErrCode.NO_PHONE_EXIST,"该账号已被注册");
        }

    }
    private String createContent(String phone,String password){
        //String code = String.valueOf(System.currentTimeMillis()).substring(0,6);
        UserDataUtil.setMsgCode(phone,password);
        return "【客迹】您的登录密码为:"+password;
    }
    /**
     * 更新账号
     * @param reqUpdateAccount
     */
    @Override
    public void updateAccount(ReqUpdateAccount reqUpdateAccount) {
         Admin adminById = adminMapper.selectByPrimaryKey(reqUpdateAccount.getId());
         Admin adminByPhone = adminMapper.selectByPhone(reqUpdateAccount.getPhone());
         if (adminById.getPhone().equals(reqUpdateAccount.getPhone())){
             Admin admin = new Admin();
             admin.setId(reqUpdateAccount.getId());//id
             admin.setStatus(reqUpdateAccount.getStatus());//添加状态
             admin.setRole(reqUpdateAccount.getRole());//角色
             admin.setName(reqUpdateAccount.getName());//姓名
             admin.setPassWord(reqUpdateAccount.getPass_word());//密码
             admin.setPhone(reqUpdateAccount.getPhone());
             adminMapper.updateByPrimaryKeySelective(admin);
         }else if (adminByPhone == null){
             Admin admin = new Admin();
             admin.setId(reqUpdateAccount.getId());//id
             admin.setStatus(reqUpdateAccount.getStatus());//添加状态
             admin.setRole(reqUpdateAccount.getRole());//角色
             admin.setName(reqUpdateAccount.getName());//姓名
             admin.setPassWord(reqUpdateAccount.getPass_word());//密码
             admin.setPhone(reqUpdateAccount.getPhone());
             adminMapper.updateByPrimaryKeySelective(admin);
         }else {
             throw new BusinessException(ErrCode.NO_PHONE_EXIST,"该账号已被注册");
         }

    }

}
