package com.shiro.source.jcaptcha;

import com.octo.captcha.engine.CaptchaEngine;
import com.octo.captcha.service.captchastore.CaptchaStore;
import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;

/**
 * Created by liwang on 15-10-9.
 */
public class MyManageableImageCaptchaService extends DefaultManageableImageCaptchaService {

    public MyManageableImageCaptchaService(CaptchaStore captchaStore, CaptchaEngine captchaEngine, int minGuarantedStorageDelayInSeconds, int maxCaptchaStoreSize, int captchaStoreLoadBeforeGarbageCollection) {
        super(captchaStore, captchaEngine, minGuarantedStorageDelayInSeconds, maxCaptchaStoreSize, captchaStoreLoadBeforeGarbageCollection);
    }

    public boolean hasCaptcha(String id, String userCaptchaResponse){
        return store.getCaptcha(id).validateResponse(userCaptchaResponse);
    }

}
