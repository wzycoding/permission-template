package com.wzy.service.impl;

import com.alibaba.fastjson.JSON;
import com.wzy.common.ErrorEnum;
import com.wzy.common.RequestHolder;
import com.wzy.dao.SysUserMapper;
import com.wzy.entity.SysUser;
import com.wzy.param.SysLoginParam;
import com.wzy.param.SysUserParam;
import com.wzy.redis.prefix.support.SysUserPrefix;
import com.wzy.service.ISysUserService;
import com.wzy.util.IpUtil;
import com.wzy.util.MD5Util;
import com.wzy.util.UUIDUtil;
import com.wzy.vo.SysUserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * 系统UserService实现类
 */
@Service
public class SysUserServiceImpl implements ISysUserService {

    private static final String TOKEN_KEY_SEPARATOR = "-";
    /**
     * 盐值的长度
     */
    private static final int SLAT_LENGTH = 8;

    /**
     * 登录方法
     * @param param 登录参数
     * @param response response
     */
    @Override
    public void login(SysLoginParam param, HttpServletResponse response) {
        //首先判断用户名和密码是否匹配
        SysUser sysUser = sysUserMapper.selectByUsername(param.getUsername());
        if (sysUser == null) {
            //不匹配则直接抛出异常，返回用户名或者密码错误
            ErrorEnum.USERNAME_OR_PASSWORD_ERROR.throwException();
        }
        //数据库中的密码
        String dbPass = sysUser.getPassword();
        //数据库的盐值
        String dbSalt = sysUser.getSalt();
        //表单提交的密码
        String inputPass = MD5Util.inputPassFormPass(param.getPassword());
        //计算后的密码
        String calcPass = MD5Util.formPassToDBPass(inputPass, dbSalt);
        //如果不匹配抛出异常
        if (!dbPass.equals(calcPass)) {
            //不匹配则直接抛出异常，返回用户名或者密码错误
            ErrorEnum.USERNAME_OR_PASSWORD_ERROR.throwException();
        }
        //匹配的话直接生成token保存到redis当中，过期时间为30分钟
        //将生成的token通过response对象存放到客户端的cookie当中，下次访问带上token
        String token = UUIDUtil.uuid();
        addCookie(response, sysUser, token);
    }

    /**
     * 将token信息添加到Cookie
     * @param response response
     * @param sysUser 用户
     * @param token token
     */
    private void addCookie(HttpServletResponse response, SysUser sysUser, String token) {
        String realKey = SysUserPrefix.tokenPrefix.getPrefix() + TOKEN_KEY_SEPARATOR + token;
        stringRedisTemplate.opsForValue().set(realKey, JSON.toJSONString(sysUser), SysUserPrefix.tokenPrefix.expireSeconds(), TimeUnit.SECONDS );
        Cookie cookie = new Cookie("token", token);
        cookie.setMaxAge(SysUserPrefix.TOKEN_EXPIRE_SECOND);
        cookie.setPath("/");
        response.addCookie(cookie);
    }


    /**
     * 保存用户信息
     * @param param 用户信息
     */
    public void save(SysUserParam param) {
        //1、验证是否有重复用户名
        if (checkUsernameExist(param)) {
            ErrorEnum.REPEAT_USERNAME.throwException();
        }
        //对提交的表单数据进行二次MD5加密
        String dbSalt = MD5Util.generateDbSlat(SLAT_LENGTH);
        String formPass = param.getPassword();
        String dbPass = MD5Util.formPassToDBPass(formPass, dbSalt);
        param.setPassword(dbPass);
        param.setSalt(dbSalt);
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(param, sysUser);
//        sysUser.setOperator(RequestHolder.getCurrentUser().getUsername());
//        sysUser.setOperatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
//        sysUser.setUpdateTime(LocalDateTime.now());

        sysUser.setOperator("admin");
        sysUser.setOperatorIp("127.0.0.1");
        //2、保存用户信息
        sysUserMapper.insertSelective(sysUser);
    }

    /**
     * 更新用户信息
     * @param param 用户信息入参
     */
    public void update(SysUserParam param) {
        //1、验证是否有重复用户名
        if (checkUsernameExist(param)) {
            ErrorEnum.REPEAT_USERNAME.throwException();
        }
        //根据id获取用户信息
        SysUser sysUser = sysUserMapper.selectById(param.getId());

        //对提交的表单数据进行二次MD5加密
        if (param.getPassword() != null) {
            String formPass = param.getPassword();
            //不更新盐值
            String dbPass = MD5Util.formPassToDBPass(formPass, sysUser.getSalt());
            param.setPassword(dbPass);
        }

        SysUser targetUser = new SysUser();
        BeanUtils.copyProperties(param, targetUser);

        targetUser.setOperator("admin");
        targetUser.setOperatorIp("127.0.0.1");
        //更新用户信息
        sysUserMapper.update(targetUser);
    }

    @Override
    public void logout() {
        String token = RequestHolder.getCurrentToken();
//        String token = "ca2c216837594d049a65cd9f10e7e24e";
        String realKey = SysUserPrefix.tokenPrefix.getPrefix() + TOKEN_KEY_SEPARATOR + token;
        stringRedisTemplate.delete(realKey);
    }

    @Override
    public SysUserVO getById(long userId) {
        SysUser sysUser = sysUserMapper.selectById(userId);
        SysUserVO sysUserVO = new SysUserVO();
        BeanUtils.copyProperties(sysUser, sysUserVO);
        return sysUserVO;
    }

    private boolean checkUsernameExist(SysUserParam sysUserParam) {
        return sysUserMapper.countByUserName(sysUserParam.getUsername(), sysUserParam.getId()) > 0;
    }

    /**
     * 引入用户DAO
     */
    @Resource
    private SysUserMapper sysUserMapper;

    /**
     * 引入Redis服务
     */
    @Resource
    StringRedisTemplate stringRedisTemplate;

}
