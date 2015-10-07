package com.shiro.source.web.exception;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Map;

/**
 * Created by liwang on 15-10-6.
 */
@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler({UnauthorizedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String processUnauthenticatedException(NativeWebRequest request, UnauthorizedException e,
                                                  Map<String, Object> map) {
        map.put("exception", e);
        return "unauthorized";
    }

}
