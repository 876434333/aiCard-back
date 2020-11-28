package com.vma.push.bi.service.impl;

import com.vma.push.bi.dao.*;
import com.vma.push.bi.dto.req.ReqAllId;
import com.vma.push.bi.dto.rsp.RspAllId;
import com.vma.push.bi.entity.*;
import com.vma.push.bi.service.BIDataService;
import com.vma.push.bi.utils.DateFormatUtils;
import com.vma.push.bi.utils.UuidUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by chenzui on 2018/5/19.
 */
@Service
public class BIDataServiceImpl implements BIDataService{

    private Logger LOG = Logger.getLogger(this.getClass());


    @Autowired
    private EnterpriseMapper enterpriseMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private StaffMapper staffMapper;

    @Autowired
    private UserStaffRelaMapper userStaffRelaMapper;

    @Autowired
    private BiUserLogMapper biUserLogMapper;

    @Autowired
    private ClickActionMapper clickActionMapper;

    @Autowired
    private ClickActionLogMapper clickActionLogMapper;

    @Autowired
    private BiActionLogMapper biActionLogMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private BiOrderLogMapper biOrderLogMapper;

    @Autowired
    private RelaMapper relaMapper;

    @Autowired
    private TotalsMapper totalsMapper;

    @Autowired
    private AdvMapper advMapper;

    @Override
    public void createBIUserLog() throws Exception {
        LOG.info("createBIUserLog");

        Date date1 = DateFormatUtils.addDay(new Date(),-1);

        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DAY_OF_MONTH, -1);

        String date = DateFormatUtils.formate(calendar.getTime(),DateFormatUtils.SPLIT_PATTERN_DATE);


        List<Enterprise> enterprises = enterpriseMapper.findAll();

        for(Enterprise enterprise:enterprises){

            List<Department> departments = departmentMapper.findCond(enterprise.getId());

            for (Department department:departments){

                List<Staff> staffs = staffMapper.findCond(enterprise.getId(),department.getId());

                for (Staff staff:staffs) {

                    Long count = userStaffRelaMapper.countNum(staff.getId(),department.getId(),enterprise.getId(),date);

                    BiUserLog biUserLog = new BiUserLog();
                    biUserLog.setId(UuidUtil.getRandomUuid());
                    biUserLog.setNum(count.intValue());
                    biUserLog.setCreateTime(date1);
                    biUserLog.setStaffId(staff.getId());
                    biUserLog.setDepartmentId(department.getId());
                    biUserLog.setEnterpriseId(enterprise.getId());

                    biUserLogMapper.insertSelective(biUserLog);
                }
            }
        }

    }

    /**
     * 点赞、转发、浏览定时汇总
     */
    @Override
    public void createTotal() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        List<Totals> list = totalsMapper.findAll();
        for (Totals t: list){
            String staffId = t.getStaffId();
            Long viewView = biActionLogMapper.queryActionNum(staffId, "1001");//汇总浏览量
            Long zanHis = (biActionLogMapper.queryActionNum(staffId,"1002")-biActionLogMapper.queryActionNum(staffId,"1003"));
            Long zhuan_num = biActionLogMapper.queryActionNum(staffId,"1004");
            Totals totals = new Totals();
            totals.setId(t.getId());
            totals.setViewOld(viewView.intValue());
            totals.setZanOld(zanHis.intValue());
            totals.setZhuanOld(zhuan_num.intValue());
            totals.setZanToday(0);
            totals.setZhuanToday(0);
            totals.setViewToday(0);
            totalsMapper.updateByPrimaryKeySelective(totals);

        }

    }

    @Override
    public void createBIActionLog() throws Exception {
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DAY_OF_MONTH, -1);

        String date = DateFormatUtils.formate(calendar.getTime(),DateFormatUtils.SPLIT_PATTERN_DATE);

        List<ClickAction> actions = clickActionMapper.findAll();

        Date date1 = DateFormatUtils.addDay(new Date(),-1);

        for (ClickAction action:actions) {

            List<Enterprise> enterprises = enterpriseMapper.findAll();

            for(Enterprise enterprise:enterprises){

                List<Department> departments = departmentMapper.findCond(enterprise.getId());

                for (Department department:departments){

                    List<Staff> staffs = staffMapper.findCond(enterprise.getId(),department.getId());

                    for (Staff staff:staffs) {

                        Long num = clickActionLogMapper.count(action.getCode(),staff.getId(),department.getId(),enterprise.getId(),date);

                        BiActionLog biActionLog = new BiActionLog();

                        biActionLog.setId(UuidUtil.getRandomUuid());
                        biActionLog.setActionCode(action.getCode());
                        biActionLog.setCreateTime(date1);
                        biActionLog.setNum(num.intValue());
                        biActionLog.setStaffId(staff.getId());
                        biActionLog.setDepartmentId(department.getId());
                        biActionLog.setEnterpriseId(enterprise.getId());

                        biActionLogMapper.insertSelective(biActionLog);
                    }
                }
            }
        }
    }

    @Override
    public void createBIAttachUserLog() {

    }

    @Override
    public void createOrderLog() throws Exception {

        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DAY_OF_MONTH, -1);

        String date = DateFormatUtils.formate(calendar.getTime(),DateFormatUtils.SPLIT_PATTERN_DATE);

        Date date1 = DateFormatUtils.addDay(new Date(),-1);

        List<Enterprise> enterprises = enterpriseMapper.findAll();

        for(Enterprise enterprise:enterprises){

            List<Department> departments = departmentMapper.findCond(enterprise.getId());

            for (Department department:departments){

                List<Staff> staffs = staffMapper.findCond(enterprise.getId(),department.getId());

                for (Staff staff:staffs) {

                    Long num = orderMapper.count(staff.getId(),department.getId(),enterprise.getId(),date);

                    BiOrderLog biOrderLog = new BiOrderLog();
                    biOrderLog.setId(UuidUtil.getRandomUuid());
                    biOrderLog.setNum(num.intValue());
                    biOrderLog.setCreateTime(date1);
                    biOrderLog.setStaffId(staff.getId());
                    biOrderLog.setDepartmentId(department.getId());
                    biOrderLog.setEnterpriseId(enterprise.getId());

                    biOrderLogMapper.insertSelective(biOrderLog);
                }
            }
        }
    }

    @Override
    public void addRela() {
        List<RspAllId> list =  relaMapper.findAllId();
        for (RspAllId rspAllId:list){
            ReqAllId reqAllId = new ReqAllId();
            reqAllId.setEnterprise_id(rspAllId.getEnterprise_id());
            reqAllId.setEmployee_id(rspAllId.getStaff_id());
            reqAllId.setUser_id(rspAllId.getUser_id());
            int count = relaMapper.findCountByAllId(reqAllId);
            if(count < 100){
                reqAllId.setCount(15);
            }else if(count < 200){
                reqAllId.setCount(25);
            }else if(count < 300){
                reqAllId.setCount(35);
            }else if(count < 400){
                reqAllId.setCount(45);
            }else if(count < 500){
                reqAllId.setCount(55);
            }else if(count < 600){
                reqAllId.setCount(65);
            }else if(count < 700){
                reqAllId.setCount(75);
            }else if(count < 800){
                reqAllId.setCount(85);
            }else {
                reqAllId.setCount(90);
            }
            relaMapper.insertRate(reqAllId);
        }
    }

    @Override
    public void moveClickData() {
        clickActionMapper.moveClickData();
    }

    @Override
    public void changeAdvStatus() {
        advMapper.changeStatus();
    }
}
