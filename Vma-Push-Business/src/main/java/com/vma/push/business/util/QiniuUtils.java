package com.vma.push.business.util;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.vma.push.business.common.Constants;
import com.vma.push.business.dao.SysConfigMapper;
import com.vma.push.business.entity.SysConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Random;
import java.util.UUID;

/**
 * 七牛云相关操作
 * Created by zhangshilin on 2017/4/23.
 */
@Service
public class QiniuUtils {
    private static final long EXPIRE_SECONDS = Constants.EXPIRE_SECONDS;

    @Autowired
    private SysConfigMapper sysConfigMapper;

    //A by plh at 2019-01-09 for 七牛云支持在sysconfig表配置是在哪个区域（测试环境和开发环境可能会不同）
    //private static final Zone QINIU_ZONE1 = Constants.QINIU_ZONE;
    // 获得七牛云所在的区域
    public com.qiniu.common.Zone getQiNiuZone() {
        SysConfig sysConfig = sysConfigMapper.selectByPrimaryKey(1);
        if("0".equals(sysConfig.getQiniuZone())) {
            //华南
            return Zone.zone0();
        } else if("1".equals(sysConfig.getQiniuZone())) {
            //华北
            return Zone.zone1();
        } else if("2".equals(sysConfig.getQiniuZone())) {
            //华东
            return Zone.zone2();
        }
        return null;
    }
    /**
     * 获取七牛上传凭证
     *
     * @return
     */
    public String getQiNiuToken(String buket) {
        SysConfig sysConfig = sysConfigMapper.selectByPrimaryKey(1);
        Auth auth = Auth.create(sysConfig.getQiniuAccessKey(), sysConfig.getQiniuSecretKey());
        StringMap putPolicy = new StringMap();
        //putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize),\"user\":\"$(x:user)\"");
        String upToken = auth.uploadToken(buket, null, EXPIRE_SECONDS, null);
        return upToken;
    }

    /**
     * @param upToken
     * @param localFilePath 本地文件路径
     * @return 七牛文件名称
     */
    public String upFileToQiNiuZone(String upToken, String localFilePath, String fileType) {
        try {
            //System.out.println("开始上传七牛视频");

            //M by plh at 2019-01-09 for 七牛云支持在sysconfig表配置是在哪个区域（测试环境和开发环境可能会不同）
            //Configuration cfg = new Configuration(QiniuUtils.QINIU_ZONE);
            com.qiniu.common.Zone qiniuZone = this.getQiNiuZone();
            Configuration cfg = new Configuration(qiniuZone);

            UploadManager uploadManager = new UploadManager(cfg);
            String str = "img" + UuidUtil.getRandomUuidWithoutSeparator() + new Random().nextInt(10) + fileType;

            Response response = uploadManager.put(localFilePath, str, upToken);
            //解析上传成功的结果
            //System.out.println(response.bodyString());
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);

            //System.out.println("上传七牛视频成功,key = " + putRet.key + " hash = " + putRet.hash);
            //System.out.println(putRet.key);
            //System.out.println(putRet.hash);
            File localFile = new File(localFilePath);
            localFile.delete();

            return putRet.key;
        } catch (QiniuException ex) {
            ex.printStackTrace();

            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return "";
    }

    /**
     * 删除资源文件
     *
     * @param fileName
     */
    public boolean deteteBucketFile(String fileName) {
        //M by plh at 2019-01-09 for 七牛云支持在sysconfig表配置是在哪个区域（测试环境和开发环境可能会不同）
        //Configuration cfg = new Configuration(QiniuUtils.QINIU_ZONE);
        com.qiniu.common.Zone qiniuZone = this.getQiNiuZone();
        Configuration cfg = new Configuration(qiniuZone);

        SysConfig sysConfig = sysConfigMapper.selectByPrimaryKey(1);
        Auth auth = Auth.create(sysConfig.getQiniuAccessKey(), sysConfig.getQiniuSecretKey());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(sysConfig.getQiniuBucket(), fileName);
        } catch (QiniuException ex) {
            ex.printStackTrace();

            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
            return false;
        }

        return true;

    }

    public String newUlr(String weburl) {
        //M by plh at 2019-01-09 for 七牛云支持在sysconfig表配置是在哪个区域（测试环境和开发环境可能会不同）
        //Configuration cfg = new Configuration(QiniuUtils.QINIU_ZONE);
        com.qiniu.common.Zone qiniuZone = this.getQiNiuZone();
        Configuration cfg = new Configuration(qiniuZone);

        //获取到 Access Key 和 Secret Key 之后，您可以按照如下方式进行密钥配置
        SysConfig sysConfig = sysConfigMapper.selectByPrimaryKey(1);
        Auth auth = Auth.create(sysConfig.getQiniuAccessKey(), sysConfig.getQiniuSecretKey());
        //获取空间管理器
        BucketManager bucketManager = new BucketManager(auth,cfg);
        String newUrl="";
        try {

            String imgid=UuidUtil.getRandomUuid();
            // 要求url可公网正常访问BucketManager.fetch(url, bucketName, key);
            // @param url 网络上一个资源文件的URL
            // @param bucketName 空间名称
            // @param key 空间内文件的key[唯一的]
            DefaultPutRet putret = bucketManager.fetch(weburl, sysConfig.getQiniuBucket(), imgid);
            newUrl= sysConfig.getQiniuUrl() + "/" + imgid;
            System.out.println(putret.key);
            System.out.println("succeed upload image");
        } catch (QiniuException e1) {
            e1.printStackTrace();
        }

        return newUrl;
    }




}
