package com.vma.push.business.util;

import com.vma.push.business.dao.EnterpriseMapper;
import com.vma.push.business.dao.SysConfigMapper;
import com.vma.push.business.dao.UserFormRelaMapper;
import com.vma.push.business.dao.UserInfoMapper;
import com.vma.push.business.dto.req.ReqMiniSendMsg;
import com.vma.push.business.dto.req.ReqUserFormAdd;
import com.vma.push.business.entity.SysConfig;
import org.apache.log4j.Logger;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by huxiangqiang on 2018/4/24.
 */
@Service
public class SmallSoftApi {

    @Autowired
    private RestTemplate rest;

    @Autowired
    private EnterpriseMapper enterpriseMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserFormRelaMapper userFormRelaMapper;

    @Autowired
    private SysConfigMapper sysConfigMapper;

    @Autowired
    private QiniuUtils qiniuUtils;

    private static final String FILE_SEPARATOR = "/";

    private Logger LOG = Logger.getLogger(this.getClass());

    private  String getToken(String appId,String secret) {

        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appId+"&secret="+secret;
        String result = new JSONObject(HttpUtil.httpGetReq(url).getResponse_str()).getString("access_token");
        return result;
    }

    public String code(String staffId, String departmentId, String enterpriseId,String appId,String secure) throws IOException {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            String token = getToken(appId, secure);

            String url = "https://api.weixin.qq.com/wxa/getwxacode?access_token=" + token;
            Map param = new HashMap();
            param.put("path", "pages/card/card?department_id=" + departmentId + "&employee_id=" + staffId + "&enterprise_id=" + enterpriseId);
            param.put("width", 430);
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            HttpEntity requestEntity = new HttpEntity(param, headers);
            ResponseEntity<byte[]> entity = rest.exchange(url, HttpMethod.POST, requestEntity, byte[].class, new Object[0]);
            byte[] result = entity.getBody();
            LOG.info(Base64.encode(result));


            inputStream = new ByteArrayInputStream(result);


            //M by PLH at 2018-10-23 for 深卡名片的服务器路径被修改为/u01/app/deecard了
            //File file = new File("/alidata01/chenz/tmp/"+staffId+".png");
            //File file = new File("/alidata01/chenz/tmp/"+staffId+".png");
            //        File file = new File("/u01/app/deecard/tmp/"+staffId+".png");
            Properties props = System.getProperties();
            String osName = props.getProperty("os.name");
            String tempFilePath = null;
            if (osName.contains("Windows ")) {
                tempFilePath = "E:\\" + staffId + ".png";
            } else {
                tempFilePath = "/u01/app/deecard/tmp/" + staffId + ".png";
            }

            File file = new File(tempFilePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = inputStream.read(buf, 0, 1024)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.flush();
            SysConfig sysConfig = sysConfigMapper.selectByPrimaryKey(1);

            String upToken = qiniuUtils.getQiNiuToken(sysConfig.getQiniuBucket());

            String res = qiniuUtils.upFileToQiNiuZone(upToken, file.getAbsolutePath(), ".jpg");
            file.delete();
            return sysConfig.getQiniuUrl() + FILE_SEPARATOR + res;
        } finally {
            if(inputStream != null) {
                inputStream.close();
            }
            if(outputStream != null) {
                outputStream.close();
            }
        }
    }

    /**
     * 获取没有数量限制的小程序码
     * @param staffId
     * @param appId
     * @param secure
     * @return
     * @throws IOException
     */
    public String getMiniUnlimitCode(String staffId,String appId,String secure) throws IOException {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            String token = getToken(appId, secure);

            String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + token;
            Map param = new HashMap();
            param.put("page", "pages/card/cardView");
            param.put("scene", staffId);
            param.put("width", 430);
            Map<String, Object> line_color = new HashMap<>();
            line_color.put("r", 0);
            line_color.put("b", 0);
            line_color.put("g", 0);
            param.put("line_color", line_color);
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            HttpEntity requestEntity = new HttpEntity(param, headers);
            ResponseEntity<byte[]> entity = rest.exchange(url, HttpMethod.POST, requestEntity, byte[].class, new Object[0]);
            byte[] result = entity.getBody();
            LOG.info("lql-------------------123"+Base64.encode(result));

            inputStream = new ByteArrayInputStream(result);

            //M by PLH at 2018-10-23 for 深卡名片的服务器路径被修改为/u01/app/deecard了
            //File file = new File("/alidata01/chenz/tmp/"+staffId+".png");
            //File file = new File("/alidata01/chenz/tmp/"+staffId+".png");
            //File file = new File("/u01/app/deecard/tmp/"+staffId+".png");
            Properties props = System.getProperties();
            String osName = props.getProperty("os.name");
            String tempFilePath = null;
            if (osName.contains("Windows ")) {
                tempFilePath = "E:\\" + staffId + ".png";
            } else {
                tempFilePath = "/u01/app/deecard/tmp/" + staffId + ".png";
            }

            File file = new File(tempFilePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = inputStream.read(buf, 0, 1024)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.flush();
            SysConfig sysConfig = sysConfigMapper.selectByPrimaryKey(1);
            String upToken = qiniuUtils.getQiNiuToken(sysConfig.getQiniuBucket());
            String res = qiniuUtils.upFileToQiNiuZone(upToken, file.getAbsolutePath(), ".jpg");
            file.delete();
            return sysConfig.getQiniuUrl() + FILE_SEPARATOR + res;
        } finally {
            if(inputStream != null) {
                inputStream.close();
            }
            if(outputStream != null) {
                outputStream.close();
            }
        }
    }
    public String message(ReqMiniSendMsg reqMiniSendMsg) throws Exception {

        String appId=enterpriseMapper.findAppId(reqMiniSendMsg.getEnterprise_id());
        String openid=userInfoMapper.openByUserId(reqMiniSendMsg.getUser_id());
        String secret=enterpriseMapper.findsecret(reqMiniSendMsg.getEnterprise_id());
        String token  = getToken(appId,secret);
        String  url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token="+token;
        Map param =  new HashMap();
        param.put("touser",openid);
        param.put("template_id",reqMiniSendMsg.getTemplate_id());
        param.put("form_id",reqMiniSendMsg.getForm_id());
//        param.put("page","pages/card/cardView?department_id="+reqMiniSendMsg.getDepartment_id()+"&employee_id="+reqMiniSendMsg.getStaff_id()+"&enterprise_id="+reqMiniSendMsg.getEnterprise_id()+"&from_openid=123");
        param.put("page","pages/card/cardView?scene="+reqMiniSendMsg.getStaff_id());
        Map data = new HashMap();
        Map value = new HashMap();
        Map value1 = new HashMap();
        Map value2 = new HashMap();
        value.put("value",reqMiniSendMsg.getStaff_name());
        value1.put("value",reqMiniSendMsg.getTimes());
        value2.put("value",reqMiniSendMsg.getContent());
        data.put("keyword1",value);
        data.put("keyword2",value1);
        data.put("keyword3",value2);
        param.put("data",data);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        HttpEntity requestEntity = new HttpEntity(param, headers);
        ResponseEntity<String> entity = rest.exchange(url, HttpMethod.POST, requestEntity, String.class, new Object[0]);
        String result = entity.getBody();
        LOG.info(result);

        //删除 user_form_rela 数据
        ReqUserFormAdd reqUserFormAdd=new ReqUserFormAdd();
        reqUserFormAdd.setFormId(reqMiniSendMsg.getForm_id());
        reqUserFormAdd.setUserId(reqMiniSendMsg.getUser_id());
        userFormRelaMapper.userformDel(reqUserFormAdd);

        return result;

    }


    public String miniSendTosale(ReqMiniSendMsg reqMiniSendMsg) throws Exception {

        String appId=enterpriseMapper.findAppId(reqMiniSendMsg.getEnterprise_id());
        String openid=userInfoMapper.openByUserId(reqMiniSendMsg.getUser_id());
        String secret=enterpriseMapper.findsecret(reqMiniSendMsg.getEnterprise_id());
        String token  = getToken(appId,secret);
        String  url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token="+token;
        Map param =  new HashMap();
        param.put("touser",openid);
        param.put("template_id",reqMiniSendMsg.getTemplate_id());
        param.put("form_id",reqMiniSendMsg.getForm_id());
//        param.put("page","pages/card/cardView?department_id="+reqMiniSendMsg.getDepartment_id()+"&employee_id="+reqMiniSendMsg.getStaff_id()+"&enterprise_id="+reqMiniSendMsg.getEnterprise_id()+"&from_openid=123");
        param.put("page","pages/index");
        Map data = new HashMap();
        Map value = new HashMap();
        Map value1 = new HashMap();
        Map value2 = new HashMap();
        value.put("value",reqMiniSendMsg.getStaff_name());
        value1.put("value",reqMiniSendMsg.getTimes());
        value2.put("value",reqMiniSendMsg.getContent());
        data.put("keyword1",value);
        data.put("keyword2",value1);
        data.put("keyword3",value2);
        param.put("data",data);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        HttpEntity requestEntity = new HttpEntity(param, headers);
        ResponseEntity<String> entity = rest.exchange(url, HttpMethod.POST, requestEntity, String.class, new Object[0]);
        String result = entity.getBody();
        LOG.info(result);

        //删除 user_form_rela 数据
        ReqUserFormAdd reqUserFormAdd=new ReqUserFormAdd();
        reqUserFormAdd.setFormId(reqMiniSendMsg.getForm_id());
        reqUserFormAdd.setUserId(reqMiniSendMsg.getUser_id());
        userFormRelaMapper.userformDel(reqUserFormAdd);

        return result;

    }
    /**
     * 对象转map
     * @param obj
     * @return
     */
    public static Map<String, Object> transBean2Map(Object obj) {
        if (obj == null) {
            return null;
        }

        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    if(value != null && !"".equals(value)){
                        map.put(key, value);
                    }
                }

            }
        } catch (Exception e) {
            //LOGGER.error("transBean2Map Error {}" ,e);
        }
        return map;

    }

    public static void main(String[] args) throws IOException {
//        SmallSoftApi smallSoftApi = new SmallSoftApi();
//
//        smallSoftApi.code("02a3a07f-ccd2-4a5e-b784-4380939097f2",
//                "1","56c369ec-0c96-40a8-8d9e-a69727f19180",
//                "wxcfc72555c41b62b1","9ca226930dba9554f1922f89bb1a1939");

        String id = UuidUtil.getRandomUuidWithoutSeparator();

        System.out.println(id);

        System.out.println(id.length());
    }
}
