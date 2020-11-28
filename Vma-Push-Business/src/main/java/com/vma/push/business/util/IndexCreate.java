package com.vma.push.business.util;

import com.vma.push.business.dao.IndexConfigMapper;
import com.vma.push.business.entity.IndexConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by huxiangqiang on 2018/6/14.
 */
@Service
public class IndexCreate {

    @Autowired
    private IndexConfigMapper mapper;

    public String getIndex(String enterpriseId,String pri) {
        String result = "";
        IndexConfig config = mapper.getNo(enterpriseId,pri);
        boolean flag = false;
        if(config == null){
            config = new IndexConfig();
            config.setId(UuidUtil.getRandomUuid());
            config.setNo("1");
            config.setCreateTime(new Date());
            config.setModifiedTime(new Date());
            config.setPrix(pri);
            config.setEnterpriseId(enterpriseId);
            mapper.insertSelective(config);
            flag = true;
        }
        int no = 0;
        if(flag){
            no = 1;
        }else {
            no = Integer.valueOf(config.getNo());
            no++;
        }
        result = no+"";
        int length = config.getNo().length();
        //M by plh at 2018-11-01 for 企业编码由6位增加到8位，满足亿级企业数量的设计0000,0000
        for(int i=0;i<8-length;i++){
            result = "0"+result;
        }
        config.setNo(no+"");
        mapper.updateByPrimaryKeySelective(config);
        return pri+result;
    }

    /**
     * 从ID控制表中获取最大的ID值 -- 部门编码
     */
    public int getDeptIdIndex() {
        String enterpriseId = "DEP";
        String pri = "DEP";

        IndexConfig config = mapper.getNo(enterpriseId,pri);
        boolean flag = false;
        if(config == null){
            config = new IndexConfig();
            config.setId(UuidUtil.getRandomUuid());
            config.setNo("1");
            config.setCreateTime(new Date());
            config.setModifiedTime(new Date());
            config.setPrix(pri);
            config.setEnterpriseId(enterpriseId);
            mapper.insertSelective(config);
            flag = true;
        }
        int no = 0;
        if(flag){
            no = 1;
        }else {
            no = Integer.valueOf(config.getNo());
            no++;
        }
        config.setNo(no+"");
        mapper.updateByPrimaryKeySelective(config);
        return no;
    }

}
