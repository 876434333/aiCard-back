package com.vma.push.business.controller.superback;

import com.vma.push.business.dto.req.template.ReqTemplateSelect;
import com.vma.push.business.dto.req.template.ReqUpdateTem;
import com.vma.push.business.dto.rsp.RspPage;
import com.vma.push.business.dto.rsp.template.RspTemplatePage;
import com.vma.push.business.service.ITemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Create By ChenXiAoBin
 * on 2018/6/13
 */
@RestController
@RequestMapping("/V1.0")
@Api(value = "模版管理API", description = "管理后台--模版管理", tags = {"TemplateController"})
public class TemplateController {
    private Logger LOG = Logger.getLogger(this.getClass());

    @Autowired
    private ITemplateService iTemplateService;


    @ApiOperation(value = "条件查询模版列表", notes = "条件查询模版列表")
    @RequestMapping(value = "/super/template/list", method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqTemplateSelect",value = "条件查询模版列表",dataType = "ReqTemplateSelect")
    public RspPage<RspTemplatePage> findTemplateSerlect(@RequestBody ReqTemplateSelect reqTemplateSelect){
        LOG.info("条件查询模版列表");
        return iTemplateService.findTemplateSerlect(reqTemplateSelect);
    }


    @ApiOperation(value = "查看模版详情", notes = "查看模版详情")
    @RequestMapping(value = "/super/template/{id}", method = RequestMethod.GET)
    @ApiImplicitParam(name = "id",value = "查看模版参数",paramType = "path",required = true,dataType = "String")
    public RspTemplatePage findTemplateById(@PathVariable String id){
        LOG.info("根据id查询模版后编辑");
        return iTemplateService.findTemplateById(id);
    }


    @ApiOperation(value = "编辑模版信息",notes = "编辑模版信息")
    @RequestMapping(value = "/super/template",method = RequestMethod.PUT)
    @ApiImplicitParam(name = "reqUpdateTem",required = true,value = "编辑模版信息",dataType = "ReqUpdateTem")
    public void  updateTemp(@RequestBody ReqUpdateTem reqUpdateTem){
        LOG.info("编辑模版信息");
        iTemplateService.updateTem(reqUpdateTem);
    }

}