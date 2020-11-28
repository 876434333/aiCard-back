package com.vma.push.bi.common;

import com.vma.push.bi.common.exception.BusinessException;
import com.vma.push.bi.common.exception.RspError;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>Title: ControllerExceptionHandler</p>
 * <p>Description: 控制器异常处理</p>
 *
 * @author zhangsl
 */
@ControllerAdvice
public abstract class ControllerExceptionHandler {
    private static final Logger LOG = Logger.getLogger(ControllerExceptionHandler.class);

    /**
     * 控制器异常处理入口
     *
     * @param e 异常信息
     */
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResponseEntity<RspError> resolveException(Exception e) {
        //LOG.error(e.getMessage(), e);
        RspError rspError = new RspError();
        if (e instanceof HttpRequestMethodNotSupportedException){
            rspError.setErr_code(ErrCode.METHOD_NOT_SUPPORT);
            rspError.setErr_msg("运行错误:请求方法不支持");
            return new ResponseEntity<RspError>(rspError, HttpStatus.METHOD_NOT_ALLOWED);
        }else if (e instanceof MissingServletRequestParameterException){
            rspError.setErr_code(ErrCode.PARAM_NOT_RIGHT);
            rspError.setErr_msg("运行错误:请求参数不完整");
            return new ResponseEntity<RspError>(rspError, HttpStatus.BAD_REQUEST);
        }else if (e instanceof NullPointerException){
            rspError.setErr_code(ErrCode.NULL_POINTER);
            rspError.setErr_msg("运行错误:空值异常");
            return new ResponseEntity<RspError>(rspError, HttpStatus.BAD_REQUEST);
        }else if (e instanceof BusinessException){
            rspError.setErr_msg(((BusinessException) e).getMsg());
            rspError.setErr_code(((BusinessException) e).getCode());
            return new ResponseEntity<RspError>(rspError, HttpStatus.BAD_REQUEST);
        }else{
            rspError.setErr_code(ErrCode.OTHER_ERROR);
            rspError.setErr_msg("运行错误:其他错误");
            return new ResponseEntity<RspError>(rspError, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
