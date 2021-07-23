package com.dome.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 测试Shiro 服务
 * @author hzq
 */
public class HelloShiro {
    private static final Logger logger = LoggerFactory.getLogger(HelloShiro.class);

    public static void main(String[] args) {
//        初始化SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");

        SecurityManager securityManager = factory.getInstance();
//       将securityManage 对象存入SecurityUtils中 共shiro使用
        SecurityUtils.setSecurityManager(securityManager);

//        获取当前的普通用户
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("shiro", "201314");

        try {
            subject.login(token);
        }catch (AuthenticationException ae){
            logger.info("登陆失败");
            return;
        }
        logger.info("登陆成功！Hello " + subject.getPrincipal());

        subject.logout();
    }
}
