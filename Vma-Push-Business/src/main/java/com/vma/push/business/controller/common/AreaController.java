package com.vma.push.business.controller.common;

import com.vma.push.business.dao.AreaMapper;
import com.vma.push.business.dto.rsp.area.AreaInfo;
import com.vma.push.business.dto.rsp.area.AreaList;
import com.vma.push.business.util.HttpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by chenzui on 2018/6/14.
 */
@RestController
@RequestMapping("/V1.0")
@Api(value = "AreaController",description = "省市区API",tags = {"AreaController"})
public class AreaController {

    @Autowired
    private AreaMapper areaMapper;

    @ApiOperation(value = "获取省份列表")
    @RequestMapping(value = "/common/area/province",method = RequestMethod.GET)
    public AreaList province(){
        List<AreaInfo> list = areaMapper.selectProvince();
        AreaList areaList = new AreaList();
        areaList.setList(list);
        return areaList;
    }
    @ApiOperation(value = "获取城市列表")
    @RequestMapping(value = "/common/area/city/{code}",method = RequestMethod.GET)
    public AreaList city(@ApiParam(required=true, name="code", value="code") @PathVariable("code") String code){
        List<AreaInfo> list = areaMapper.selectCity(code);
        AreaList areaList = new AreaList();
        areaList.setList(list);
        return areaList;
    }
    @ApiOperation(value = "获取地区列表")
    @RequestMapping(value = "/common/area/area/{code}",method = RequestMethod.GET)
    public AreaList area(@ApiParam(required=true, name="code", value="code") @PathVariable("code") String code){
        List<AreaInfo> list = areaMapper.selectArea(code);
        AreaList areaList = new AreaList();
        areaList.setList(list);
        return areaList;
    }
}
