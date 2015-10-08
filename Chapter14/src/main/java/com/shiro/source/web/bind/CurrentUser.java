package com.shiro.source.web.bind;

import com.shiro.source.Constants;

import java.lang.annotation.*;

/**
 * Created by liwang on 15-10-7.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUser {
    /**
     * 当前用户在request中的名字
     * @return
     */
    String value() default Constants.CURRENT_USER;
}
