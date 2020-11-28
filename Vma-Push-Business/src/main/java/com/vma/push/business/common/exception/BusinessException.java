package com.vma.push.business.common.exception;

/** 业务异常
 * Created by zhangshilin on 2018/1/12.
 */
public class BusinessException extends RuntimeException {

    private String msg;

    private Integer code;

    public BusinessException(Integer errCode,String msg){
//        super(errCode);
        this.msg = msg;
        this.code = errCode;
    }

    public BusinessException(String errCode, Throwable cause){
        super(errCode,cause);
    }

    public BusinessException(Throwable cause){
        super(cause);
    }

    public String getMsg() {
        return msg;
    }

    public Integer getCode() {
        return code;
    }


}
