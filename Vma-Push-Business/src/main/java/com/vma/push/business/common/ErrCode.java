package com.vma.push.business.common;

/** 错误码说明
 * Created by zhangshilin on 2017/12/12.
 */
public interface ErrCode {
    //***********运行错误码**************//
    //0为默认值，表示没有错误

    //运行错误:请求方法不支持
    Integer METHOD_NOT_SUPPORT = 20001;
    //运行错误:请求参数不完整
    Integer PARAM_NOT_RIGHT = 20002;
    //运行错误:空值异常
    Integer NULL_POINTER = 20003;
    //运行错误:其他错误
    Integer OTHER_ERROR = 20004;
    //用户名或密码错误信息
    Integer ERR_NUM=  -1;
   //账号失效信息
    Integer LOSE_NUM = 0;


    //***********业务错误码**************//
    //0为默认值，表示没有错误

    //新增数据失败
    Integer ADD_DATA_FAIL = 10001;
    //更新数据失败
    Integer UPDATE_DATA_FAIL = 10002;
    //删除数据失败
    Integer DELETE_DATA_FAIL = 10003;
    //数据不存在
    Integer DATA_NOT_FOUND = 11001;
    //


    Integer LOGIN_TIME_OUT = 30001;

    Integer ERROR_ACTION_CODE = 30002;

    Integer IMG_FORMAT_ERROR = 30003;

    Integer MSG_CODE_TIME_OUT = 30004;

    Integer MSG_CODE_ERROR = 30005;

    Integer ILLEGAL_REQUEST = 30006;


    Integer NO_PHONE_EXIST = 30007; //账号不存在

    Integer ERROR_PASSWORD = 30008;


    Integer SIGN_ERROR = 30009;


    Integer NO_STAFF_FOUND =  30010;

    Integer CHECK_CODE_ERROR = 30011;

    Integer NOT_ENOUGH_POINT = 30012;

    Integer ERROR_PWD = 30013;

    Integer ADMIN_ROLE = 30014;

    Integer OUT_CARD = 30015;//名片数已用完

    Integer ERROR_STATUS = 30016;

    Integer NO_OFFERLEVEL = 30017;//库存不足

    Integer ERROR_PHONE = 40000;//手机号不正确
}
