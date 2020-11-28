package com.vma.push.business.dto.req;

import lombok.Data;

/**
 * Created by huxiangqiang on 2018/6/23.
 */
@Data
public class ReqEditMenuRole {
    private String userid;
    private String menuid;
    private Integer role;
}
