package com.vma.push.business.service;

import com.vma.push.business.entity.UserLabelInfo;

/**
 * Created by chenzui on 2018/5/15.
 */
public interface IUserLabelInfoService extends IBaseService<UserLabelInfo,String> {

    public void delete(String userId,String staffId,String labelId);
}
