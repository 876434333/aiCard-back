package com.vma.push.business.common.exception;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>Title: RspError</p>
 * <p>Description: Http错误返回封装</p>
 *
 * @author zhangsl
 */
@Data
public class RspError implements Serializable {
    private int err_code;

    private String err_msg;

    private String request_id;

    public void setErr_code(int err_code) {
        this.err_code = err_code;
    }

    public String getErr_msg() {
        return err_msg;
    }

    public void setErr_msg(String err_msg) {
        this.err_msg = err_msg;
    }

    public int getErr_code() {
        return err_code;
    }

}
