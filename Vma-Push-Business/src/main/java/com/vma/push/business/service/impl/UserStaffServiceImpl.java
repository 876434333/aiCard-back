package com.vma.push.business.service.impl;

import com.vma.push.business.dao.BaseDao;
import com.vma.push.business.dao.ClickActionLogMapper;
import com.vma.push.business.dao.UserStaffRelaMapper;
import com.vma.push.business.dto.req.ReqUpdateRate;
import com.vma.push.business.dto.req.ReqUpdateTime;
import com.vma.push.business.dto.req.ReqfollowUser;
import com.vma.push.business.entity.ClickActionLog;
import com.vma.push.business.entity.UserStaffRela;
import com.vma.push.business.service.IUserStaffService;
import com.vma.push.business.util.UserDataUtil;
import com.vma.push.business.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by chenzui on 2018/5/14.
 */
@Service
public class UserStaffServiceImpl extends AbstractServiceImpl<UserStaffRela,String> implements IUserStaffService {

    private UserStaffRelaMapper mapper;

    @Autowired
    private ClickActionLogMapper clickActionLogMapper;

    public UserStaffServiceImpl(UserStaffRelaMapper mapper) {
        super(mapper);
        this.mapper = mapper;
    }


    @Override
    public UserStaffRela queryStaffInfoByUser(String staffId, String userId, String enterpriseId) {
        return mapper.queryStaffInfoByUser(staffId,userId,enterpriseId);
    }

    @Override
    public Integer updateClinchTime(ReqUpdateTime reqUpdateTime) {
        return mapper.updateClinchTime(reqUpdateTime);
    }

    @Override
    public Integer updateClinchRate(ReqUpdateRate reqUpdateRate) {
        return mapper.updateClinchRate(reqUpdateRate);
    }

    @Override
    public String follow(ReqfollowUser reqfollowUser, HttpServletRequest request) {
        ClickActionLog actionLog=new ClickActionLog();
        String userId=reqfollowUser.getUser_id();
        String content=reqfollowUser.getContent();
//        String enid= UserDataUtil.getEnterpriseId(request);
//        String dept=UserDataUtil.getDepartmentId(request);
//        String staffid=UserDataUtil.getStaffId(request);
        String enid= UserDataUtil.getMyEnterpriseId(request);
        String dept=UserDataUtil.getMyDepartmentId(request);
        String staffid=UserDataUtil.getMyStaffId(request);
        actionLog.setUserId(userId);
        actionLog.setId(UuidUtil.getRandomUuid());
        actionLog.setEnterpriseId(enid);
        actionLog.setDepartmentId(dept);
        actionLog.setDescription(content);
        actionLog.setActionCode("1000");
        actionLog.setCreateTime(new Date());
        actionLog.setEmployeeId(staffid);
        int count=clickActionLogMapper.insertSelective(actionLog);

        UserStaffRela userStaffRela = mapper.queryStaffInfoByUser(staffid,userId,enid);
        userStaffRela.setLastAttachTime(new Date());

        mapper.updateByPrimaryKeySelective(userStaffRela);
        //
        if(count>0){
            return "success";
        }else{
            return "fail";
        }
    }

    /**
     * 更新用户关系，更新手机号
     * @param userStaffRela
     * @return
     */
    @Override
    public int updataPhone(UserStaffRela userStaffRela) {
        return mapper.updataPhone(userStaffRela);
    }
}
