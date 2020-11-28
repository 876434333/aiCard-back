package com.vma.push.business.dto.rsp.staff;

import lombok.Data;

import java.util.List;

/**
 * Created by chenzui on 2018/5/16.
 */
@Data
public class RspAnalysis {
    int max_v1;

    int max_v2;

    int max_v3;

    int max_v4;

    int max_v5;

    int max_v6;

    List<RspStaffAnalysis> staffs;
}
