package com.wzy.captcha;

import com.wzy.redis.prefix.support.SysCaptchaPrefix;
import com.wzy.redis.prefix.support.SysUserPrefix;
import com.wzy.util.UUIDUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 验证码controller
 */
@Controller
@RequestMapping("/sys/captcha")
public class CaptchaController {

    @GetMapping("/get")
    public void getCaptcha(HttpServletResponse response) throws IOException {
        Captcha captcha = new GifCaptcha(100,40,4);//   gif格式动画验证码
        String uuid = UUIDUtil.uuid();
        addCookie(response, uuid);
        captcha.out(response.getOutputStream());
        String verifyCode = captcha.text();
        String realKey = SysCaptchaPrefix.captcha.getPrefix() + "-" + uuid;
        stringRedisTemplate.opsForValue().set(realKey, verifyCode, SysCaptchaPrefix.captcha.expireSeconds(), TimeUnit.SECONDS);
    }

    private void addCookie(HttpServletResponse response, String uuid) {
        Cookie cookie = new Cookie("_code", uuid);
        cookie.setMaxAge(SysUserPrefix.tokenPrefix.expireSeconds());
        cookie.setPath("/");
        cookie.setDomain("localhost");
        response.addCookie(cookie);
    }

    @Resource
    private StringRedisTemplate stringRedisTemplate;
}
