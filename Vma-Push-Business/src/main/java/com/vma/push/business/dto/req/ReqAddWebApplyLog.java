package com.vma.push.business.dto.req;

import lombok.Data;

import java.util.Date;

@Data
public class ReqAddWebApplyLog {

    private String name;

    private String mobile;

    private String company_name;

    private String remarks;

    private Date create_time;

    private Integer apply_type;

    private Integer platform_id;


}