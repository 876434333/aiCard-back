package com.vma.push.business.dto.req;

import com.vma.push.business.entity.ClickActionLog;
import com.vma.push.business.util.UuidUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by huxiangqiang on 2018/4/27.
 */
@Data
public class ReqClickInfo {

    @ApiModelProperty(value = "动作id",required = true)
    private String action_code;

    @ApiModelProperty(value = "销售人员id",required = true)
    private String employee_id;

    @ApiModelProperty(value = "企业id",required = true)
    private String enterprise_id;

    @ApiModelProperty(value = "商品名字",required = true)
    private String offer_name;

    @ApiModelProperty(value = "商品id",required = true)
    private String offer_id;

    @ApiModelProperty(value = "部门Id", required=true)
    private String department_id;



    public ClickActionLog toClickActionLog(){
        ClickActionLog clickActionLog=new ClickActionLog();
        clickActionLog.setActionCode(this.getAction_code());
        clickActionLog.setEmployeeId(this.getEmployee_id());
        clickActionLog.setEnterpriseId(this.getEnterprise_id());
        clickActionLog.setCreateTime(new Date());
        clickActionLog.setDescription("");
        clickActionLog.setId(UuidUtil.getRandomUuid());
        return clickActionLog;
    }
}
