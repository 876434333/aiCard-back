package com.vma.push.business.service;

import com.vma.push.business.dto.req.ReqUpdateRate;
import com.vma.push.business.dto.req.ReqUpdateTime;
import com.vma.push.business.dto.req.ReqfollowUser;
import com.vma.push.business.entity.UserStaffRela;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by chenzui on 2018/5/14.
 */
public interface IUserStaffService extends IBaseService<UserStaffRela,String> {

    public UserStaffRela queryStaffInfoByUser(String staffId,String userId,String enterpriseId);
    Integer updateClinchTime(ReqUpdateTime reqUpdateTime);
    Integer updateClinchRate(ReqUpdateRate reqUpdateRate);

    /**
     * 用户跟进
     * @param reqfollowUser
     * @param request
     */
    public String follow(ReqfollowUser reqfollowUser, HttpServletRequest request);

    int updataPhone(UserStaffRela userStaffRela);
}
