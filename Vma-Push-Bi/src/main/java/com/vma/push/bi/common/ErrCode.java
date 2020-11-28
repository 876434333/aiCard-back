package com.vma.push.bi.common;

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


}
