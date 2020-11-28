package com.vma.push.business.service.impl;

import com.vma.push.business.dao.UserLabelInfoMapper;
import com.vma.push.business.entity.UserLabelInfo;
import com.vma.push.business.service.IUserLabelInfoService;
import org.springframework.stereotype.Service;

/**
 * Created by chenzui on 2018/5/15.
 */
@Service
public class UserLabelInfoServiceImpl extends AbstractServiceImpl<UserLabelInfo,String> implements IUserLabelInfoService {

    private UserLabelInfoMapper mapper;

    public UserLabelInfoServiceImpl(UserLabelInfoMapper mapper) {
        super(mapper);
        this.mapper = mapper;
    }

    @Override
    public void delete(String userId, String staffId, String labelId) {
        mapper.delete(userId,staffId,labelId);
    }
}
