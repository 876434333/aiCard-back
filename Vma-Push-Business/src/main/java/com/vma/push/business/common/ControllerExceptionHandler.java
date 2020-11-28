package com.vma.push.business.common;

import com.google.gson.Gson;
import com.vma.push.business.common.exception.BusinessException;
import com.vma.push.business.common.exception.RspError;
import com.vma.push.business.dao.ExceptionLogMapper;
import com.vma.push.business.entity.ExceptionLog;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.util.Date;

/**
 * <p>Title: ControllerExceptionHandler</p>
 * <p>Description: 控制器异常处理</p>
 *
 * @author zhangsl
 */
@ControllerAdvice
public abstract class ControllerExceptionHandler {
    private static final Logger LOG = Logger.getLogger(ControllerExceptionHandler.class);

    @Autowired
    private ExceptionLogMapper exceptionLogMapper;
    /**
     * 控制器异常处理入口
     *
     * @param e 异常信息
     */
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResponseEntity<RspError> resolveException(Exception e, HttpServletRequest request) {
//        e.printStackTrace();
        LOG.error(e.getMessage(), e);


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
            String paramJson = new Gson().toJson(request.getParameterMap());


            ByteArrayOutputStream buf = new java.io.ByteArrayOutputStream();
            e.printStackTrace(new java.io.PrintWriter(buf, true));
            String  expMessage = buf.toString();

            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setActionKey(request.getRequestURI());
            exceptionLog.setExceptionInfo(expMessage);
            exceptionLog.setParam(paramJson);
            exceptionLog.setCreateTime(new Date());
            exceptionLogMapper.insertSelective(exceptionLog);
            rspError.setErr_code(ErrCode.NULL_POINTER);
            rspError.setErr_msg("运行错误:空值异常");

            return new ResponseEntity<RspError>(rspError, HttpStatus.BAD_REQUEST);
        }else if (e instanceof BusinessException){
            rspError.setErr_msg(((BusinessException) e).getMsg());
//            if (e.getMessage().equals(ErrCode.ADD_DATA_FAIL.toString())){
//                rspError.setErr_msg("业务错误:新增数据失败");
//            }else if (e.getMessage().equals(ErrCode.UPDATE_DATA_FAIL.toString())){
//                rspError.setErr_msg("业务错误:更新数据失败");
//            }else if (e.getMessage().equals(ErrCode.DELETE_DATA_FAIL.toString())){
//                rspError.setErr_msg("业务错误:删除数据失败");
//            }else if (e.getMessage().equals(ErrCode.DATA_NOT_FOUND.toString())){
//                rspError.setErr_msg("业务错误:数据不存在");
//            }else{
//                rspError.setErr_msg("业务错误:未知错误");
//            }
            rspError.setErr_code(((BusinessException) e).getCode());
            if(((BusinessException) e).getCode().equals(ErrCode.LOGIN_TIME_OUT)){
                return new ResponseEntity<RspError>(rspError, HttpStatus.UNAUTHORIZED);
            }else if(((BusinessException) e).getCode().equals(ErrCode.SIGN_ERROR)){
                return new ResponseEntity<RspError>(rspError, HttpStatus.UNAUTHORIZED);
            }else {
                return new ResponseEntity<RspError>(rspError, HttpStatus.BAD_REQUEST);
            }

        }else{
            String paramJson = new Gson().toJson(request.getParameterMap());


            ByteArrayOutputStream buf = new java.io.ByteArrayOutputStream();
            e.printStackTrace(new java.io.PrintWriter(buf, true));
            String  expMessage = buf.toString();

            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setActionKey(request.getPathInfo()+request.getMethod());
            exceptionLog.setExceptionInfo(expMessage);
            exceptionLog.setParam(paramJson);
            exceptionLog.setCreateTime(new Date());
            exceptionLogMapper.insertSelective(exceptionLog);

            rspError.setErr_code(ErrCode.OTHER_ERROR);
            rspError.setErr_msg("运行错误:其他错误");
            return new ResponseEntity<RspError>(rspError, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
