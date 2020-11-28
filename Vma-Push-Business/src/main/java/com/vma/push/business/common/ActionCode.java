package com.vma.push.business.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenzui on 2018/5/8.
 */
public class ActionCode {

    //用户跟进
    public static String ACTION_CODE_1000 = new String("1000");
    //查看名片
    public static String ACTION_CODE_1001 = new String("1001");

    //名片点赞
    public static String ACTION_CODE_1002 = new String("1002");

    //名片取消点赞
    public static String ACTION_CODE_1003 = new String("1003");

    //转发名片
    public static String ACTION_CODE_1004 = new String("1004");

    //呼叫座机
    public static String ACTION_CODE_1005 = new String("1005");

    //呼叫手机
    public static String ACTION_CODE_1006 = new String("1006");

    //复制邮箱
    public static String ACTION_CODE_1007 = new String("1007");

    //复制微信
    public static String ACTION_CODE_1008 = new String("1008");

    //复制地址
    public static String ACTION_CODE_1009 = new String("1009");

    //保存信息
    public static String ACTION_CODE_1010 = new String("1010");

    //查看朋友圈
    public static String ACTION_CODE_1011 = new String("1011");

    //评论朋友圈
    public static String ACTION_CODE_1012 = new String("1012");

    //点赞朋友圈
    public static String ACTION_CODE_1013 = new String("1013");

    //转发朋友圈
    public static String ACTION_CODE_1014 = new String("1014");

    //查看商品
    public static String ACTION_CODE_1015 = new String("1015");

    //查看具体商品详情
    public static String ACTION_CODE_1016 = new String("1016");

    //转发商品
    public static String ACTION_CODE_1017 = new String("1017");

    //咨询商品
    public static String ACTION_CODE_1018 = new String("1018");

    //下单
    public static String ACTION_CODE_1019 = new String("1019");

    //支付
    public static String ACTION_CODE_1020 = new String("1020");

    //查看官网
    public static String ACTION_CODE_1021 = new String("1021");

    //转发官网
    public static String ACTION_CODE_1022 = new String("1022");

    //主动发起跟用户沟通
    public static String ACTION_CODE_1023 = new String("1023");

    //获客能力
    public static List LIST_V1 = new ArrayList();
    static {
        LIST_V1.add(ACTION_CODE_1001);
    }

    //个人魅力值
    public static List LIST_V2 = new ArrayList();
    static {
        LIST_V2.add(ACTION_CODE_1001);
        LIST_V2.add(ACTION_CODE_1011);
        LIST_V2.add(ACTION_CODE_1012);
        LIST_V2.add(ACTION_CODE_1013);
        LIST_V2.add(ACTION_CODE_1014);
    }
    //销售主动性
    public static List LIST_V3 = new ArrayList();
    static {
        LIST_V3.add(ACTION_CODE_1023);
    }
    //客户互动值
    public static List LIST_V4 = new ArrayList();
    static {
        LIST_V4.add(ACTION_CODE_1004);
        LIST_V4.add(ACTION_CODE_1005);
        LIST_V4.add(ACTION_CODE_1006);
        LIST_V4.add(ACTION_CODE_1007);
        LIST_V4.add(ACTION_CODE_1008);
        LIST_V4.add(ACTION_CODE_1009);
        LIST_V4.add(ACTION_CODE_1010);

    }
    //官网推广值
    public static List LIST_V5 = new ArrayList();
    static {
        LIST_V5.add(ACTION_CODE_1021);
        LIST_V5.add(ACTION_CODE_1022);
    }
    //产品推广值
    public static List LIST_V6 = new ArrayList();
    static {
        LIST_V6.add(ACTION_CODE_1017);
        LIST_V6.add(ACTION_CODE_1018);
        LIST_V6.add(ACTION_CODE_1019);
        LIST_V6.add(ACTION_CODE_1020);
    }
}
