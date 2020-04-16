package com.wzy.captcha;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class GifCaptchaTest {
    public static void main(String[] args) throws FileNotFoundException {
        Captcha captcha = new SpecCaptcha(150,40,5);// png格式验证码
        captcha.out(new FileOutputStream("/Users/wzy/1.png"));
        captcha = new GifCaptcha(150,40,5);//   gif格式动画验证码
        captcha.out(new FileOutputStream("/Users/wzy/1.gif"));
    }
}
