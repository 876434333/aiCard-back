package com.vma.push.bi.dto.req;

import lombok.Data;

/**
 * Created by ChenXiaoBin
 * 2018/5/16 14:50
 */
@Data
public class ReqAllId {
    String user_id;
    String enterprise_id;
    String employee_id;
    Integer count;
}
