package com.vma.push.business.dto.rsp.boss;

import lombok.Data;

/**
 * Created by chenzui on 2018/5/16.
 */
@Data
public class RspViewData {

    Long now_num;

    Long last_num;

    String desc;

    public RspViewData(){
        now_num = 0L;
        last_num = 0L;
    }
}
