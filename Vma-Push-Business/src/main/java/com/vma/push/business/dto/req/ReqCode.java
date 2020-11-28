package com.vma.push.business.dto.req;

import lombok.Data;

/**
 * Created by chenzui on 2018/5/31.
 */
@Data
public class ReqCode {

    String staff_id;

    String department_id;

    String enterprise_id;

    String app_id;

    String secure;
}
