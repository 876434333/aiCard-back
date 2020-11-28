package com.vma.push.business.common;

import com.qiniu.common.Zone;

/** 系统常量定义
 * Created by zhangshilin on 2017/8/28.
 */
public interface Constants {
    final static String SESSION_ID = "session_id";

    final static String CLIENT_UUID = "uuid";

    final static String CLIENT_SIGN = "sign";

    final static Integer LOGIN_KEEP_TIME = 60*60*24*3;

    final static Integer MSG_KEEP_TIME = 60*3;

    //七牛
//    public static  String QINIU_ACCESS_KEY = "vjuXziLm4B5pAOIk7l4KDVlSAXnHuYMYnGhp5zFY";
//
//    public static  String QINIU_SECRET_KEY = "527uoCQ4PrV2TmMwtaM2nNoYtzYlZJorAO2TOuJk";
//
//    public static  String QINIU_URL="http://wawaji.xiyoucc.com";

//    public static  String QINIU_ACCESS_KEY = "-V2tA9xmyVKb0GRkH_MrJJXbNwwn0PQDi29lNFip";
//
//    public static  String QINIU_SECRET_KEY = "TRB4swL5N_kBCib_coKU3keHxN5ksAQMv5vQuiWD";
//
//    public static  String QINIU_URL="https://keji-res.h5h5h5.cn";

//    public static Zone QINIU_ZONE2 = Zone.zone2();//华南
//    public static Zone QINIU_ZONE0 = Zone.zone0();//华东

    public static  long EXPIRE_SECONDS = 3600;

//    public static String bucket="keji";

    //微信
    //appid 微信开放平台审核通过的应用APPID   wx0f9522e594d9a320
    public static  final String WX_APP_ID="wxcb03526b1f1b7960";
    //mch_id 微信支付分配的商户号
    public static  final String WX_MCH_ID="1493194872";
    //sign_type 签名类型
    public static final String WX_SIGN_TYPE="MD5";
    //key 商户号对应的密钥
    public static String WX_PARTNER_KEY = "erhjvbdsertyuawtgvtkfdt4ldmfs32s";//商户号对应的密钥

}
