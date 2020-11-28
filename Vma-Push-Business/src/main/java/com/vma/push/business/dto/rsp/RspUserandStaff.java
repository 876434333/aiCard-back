package com.vma.push.business.dto.rsp;

import lombok.Data;

import java.util.List;

/**
 * Created by huxiangqiang on 2018/5/24.
 */
@Data
public class RspUserandStaff {

    //private List<RspUserinfo> user_list;
    private String id;
    private String pass_word;
    private String name;
    private String head_icon;
    private String userSig;
    private String sdk_app_id;
    private String acount_type;
    private String soft_img_url;

}
