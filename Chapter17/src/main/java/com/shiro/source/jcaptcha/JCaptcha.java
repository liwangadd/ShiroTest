package com.shiro.source.jcaptcha;

import com.octo.captcha.engine.image.gimpy.BasicGimpyEngine;
import com.octo.captcha.service.captchastore.FastHashMapCaptchaStore;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liwang on 15-10-9.
 */
public class JCaptcha {

    public static final MyManageableImageCaptchaService captchaService
            = new MyManageableImageCaptchaService(new FastHashMapCaptchaStore(), new GMailEngine(), 180, 100000, 75000);

    public static boolean validateResponse(HttpServletRequest request, String userCaptchaResponse) {
        if (request.getSession(false) == null) return false;
        boolean validated = false;
        try {
            String id = request.getSession().getId();
            validated = captchaService.validateResponseForID(id, userCaptchaResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return validated;
    }

    public static boolean hasCaptcha(HttpServletRequest request, String userCaptchaResponse) {
        if (request.getSession(false) == null) return false;
        boolean validated = false;
        try {
            String id = request.getSession().getId();
            validated = captchaService.hasCaptcha(id, userCaptchaResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return validated;
    }

}
