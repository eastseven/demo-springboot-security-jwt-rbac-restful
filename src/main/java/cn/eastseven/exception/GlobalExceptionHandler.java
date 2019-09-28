package cn.eastseven.exception;

import cn.eastseven.model.ApiResponse;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

/**
 * @author d7
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity exceptionHandle(HttpServletRequest request, Exception e) {
        log.error("", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.ofError(50001, e.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public Object runtimeExceptionHandle(HttpServletRequest request, RuntimeException e) {
        log.error("", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.ofError(50002, e.getMessage()));
    }

    @Override
    protected ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("", ex);
        final List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        Set<String> errors = Sets.newHashSet();
        fieldErrors.forEach(fieldError -> errors.add(fieldError.getDefaultMessage()));
        return new ResponseEntity(ApiResponse.ofError(50003, "MethodArgumentNotValidException", errors), headers, status);
    }
}
