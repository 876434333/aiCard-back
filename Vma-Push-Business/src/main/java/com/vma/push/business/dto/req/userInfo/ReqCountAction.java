package com.vma.push.business.dto.req.userInfo;

import lombok.Data;

import java.util.List;

/**
 * Created by chenzui on 2018/5/14.
 */
@Data
public class ReqCountAction {
    String userId;

    String staffId;

    String departmentId;

    String enterpriseId;

    List<String> codes;
}
