package com.wzy.common;

import com.wzy.exception.PermissionException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
@Slf4j
public class SpringExceptionHandler {

    /**
     * 异常处理方法
     * @param request 请求
     * @param e 异常对象
     * @return
     */
    @ExceptionHandler
    public Object handlerException(HttpServletRequest request, Exception e) {
        log(request, e);
        if (e instanceof PermissionException) {
            PermissionException permissionException = (PermissionException) e;
            return Result.error(permissionException.getCode(), permissionException.getMsg());
        } else if (e instanceof MethodArgumentNotValidException) {
            //参数验证错误异常
            String errorMsg = getValidationErrorMessage(((MethodArgumentNotValidException) e).getBindingResult());
            return Result.error(ErrorEnum.PARAM_ERROR.getCode(), errorMsg.length() > 0 ? errorMsg : ErrorEnum.PARAM_ERROR.getMsg());
        }else {
            return Result.error(ErrorEnum.SERVER_ERROR);
        }
    }

    /**
     * 获取验证错误信息
     * @param e BindingResult
     * @return 验证错误信息
     */
    private String getValidationErrorMessage(BindingResult e) {
        List<ObjectError> errors = e.getAllErrors();
        StringBuilder errorMsg = new StringBuilder();
        if (errors.size() > 0)
        {
            errors.forEach(o -> {
                if (o.contains(ConstraintViolationImpl.class))
                {
                    ConstraintViolationImpl cs = o.unwrap(ConstraintViolationImpl.class);
                    if (errorMsg.length() > 0)
                        errorMsg.append("\n");
                    errorMsg.append(cs.getPropertyPath());
                    errorMsg.append(":");
                    errorMsg.append(cs.getMessage());
                }
            });
        }
        return errorMsg.toString();
    }

    /**
     * 打印错误日志
     * @param request 请求
     * @param e 异常对象
     */
    private void log(HttpServletRequest request, Exception e) {
        log.error("error: {}", e.getMessage());
    }
}
