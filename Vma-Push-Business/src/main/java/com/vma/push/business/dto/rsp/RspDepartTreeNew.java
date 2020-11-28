package com.vma.push.business.dto.rsp;

import lombok.Data;

import java.util.List;
/**
 * Created by JINzm on 2018/8/6.
 */
@Data
public class RspDepartTreeNew {

    //部门名称
    private String title;
    //部门id
    private String id;

    //上级部门id
    private String parent_id;
    //子部门
    private List<RspDepartTreeNew> Children;
}
