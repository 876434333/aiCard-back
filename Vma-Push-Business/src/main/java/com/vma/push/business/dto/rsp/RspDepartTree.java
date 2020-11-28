package com.vma.push.business.dto.rsp;

import lombok.Data;

import java.util.List;

/**
 * Created by huxiangqiang on 2018/5/12.
 */
@Data
public class RspDepartTree {

    //部门名称
    private String name;
    //部门id
    private String id;

    //上级部门id
    private String parent_id;
    //子部门
    private List<RspDepartTree> Children;
}
