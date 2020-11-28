package com.vma.push.business.dto.req.store;

import lombok.Data;

import java.util.Date;

/**
 * Created by huxiangqiang on 2018/7/25.
 */
@Data
public class ReqChangStatus {
    private String id;
    private Integer status;
    private Date onsale_time;
}
