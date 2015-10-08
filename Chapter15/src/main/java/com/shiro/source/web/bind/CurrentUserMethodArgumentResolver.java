package com.shiro.source.web.bind;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Created by liwang on 15-10-7.
 */
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        if (methodParameter.hasParameterAnnotation(CurrentUser.class))
            return true;
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        CurrentUser currentUser = methodParameter.getParameterAnnotation(CurrentUser.class);
        return nativeWebRequest.getAttribute(currentUser.value(), NativeWebRequest.SCOPE_REQUEST);
    }

}
