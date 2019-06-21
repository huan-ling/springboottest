package com.huan.springboottest.common.exception;

import com.huan.springboottest.common.dto.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: ${Description}
 * @Author: Huan
 * @CreateTime: 2019-02-23 15:51
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     *
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 异常处理
     *
     * @param request
     * @param exception
     * @return
     * @throws Exception
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> exceptionHandler(HttpServletRequest request, Exception exception) throws Exception {
        LOGGER.info("exception=" + exception);
        // 请求方式不支持
        if (exception instanceof HttpRequestMethodNotSupportedException) {
            LOGGER.warn("【http请求异常】" + exception.getMessage(), exception);
            return new ResponseEntity<>("请求方式非法", HttpStatus.BAD_REQUEST);
        }
        // 请求异常
        else if (exception instanceof ServletRequestBindingException) {
            LOGGER.warn("【servlet请求异常】" + exception.getMessage(), exception);
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
        // 数字转换异常
        else if (exception instanceof NumberFormatException) {
            LOGGER.warn("【参数异常】" + exception.getMessage(), exception);
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
        // 业务异常
        else if (exception instanceof BaseException) {
            LOGGER.info("【业务异常】" + exception.getMessage(), exception);
            BaseException bizException = (BaseException) exception;
            ResponseDto<Object> responseDto = new ResponseDto<>();
            responseDto.setCode(bizException.getCode());
            responseDto.setDesc(bizException.getMessage());
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } else {
            LOGGER.error("【服务异常】" + exception.getMessage(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
