package com.shiro.source.web.bind;

import com.shiro.source.Constants;

import java.lang.annotation.*;

/**
 * Created by liwang on 15-10-9.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUser {
    String value() default Constants.CURRENT_USER;
}
