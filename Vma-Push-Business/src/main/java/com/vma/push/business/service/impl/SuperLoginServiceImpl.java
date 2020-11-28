package com.vma.push.business.service.impl;

import com.vma.push.business.common.ErrCode;
import com.vma.push.business.common.exception.BusinessException;
import com.vma.push.business.dao.AdminMapper;
import com.vma.push.business.dto.req.ReqSuperLogin;
import com.vma.push.business.entity.Admin;
import com.vma.push.business.service.ISuperLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ChenXiaoBin
 * 2018/5/13 15:52
 */
@Service
public class SuperLoginServiceImpl implements ISuperLoginService {
    @Autowired
    private AdminMapper adminMapper;
    @Override
    public int deleteByPrimaryKey(String id) {
        return 0;
    }

    @Override
    public int insert(Admin record) {
        return 0;
    }

    @Override
    public int insertSelective(Admin record) {
        return 0;
    }

    @Override
    public Admin selectByPrimaryKey(String id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(Admin record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Admin record) {
        return 0;
    }

    @Override
    public String login(ReqSuperLogin reqSuperLogin) {
        Admin admin = adminMapper.findByPhone(reqSuperLogin.getPhone());
        if(admin == null){
            throw new BusinessException(ErrCode.NO_PHONE_EXIST,"账号不存在");
        }else {
            if (admin.getPassWord().equals(reqSuperLogin.getPass_word())){
                if (admin.getStatus().equals(0)){
                    throw new BusinessException(ErrCode. LOSE_NUM,"账号无效");
                }
                return admin.getCustomId();
            }else {
                throw new BusinessException(ErrCode.ERROR_PASSWORD,"密码错误");
            }
        }
    }
}