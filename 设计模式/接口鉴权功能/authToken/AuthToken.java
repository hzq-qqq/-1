package com.设计模式.接口鉴权功能.authToken;

import org.springframework.util.DigestUtils;
import sun.security.provider.MD5;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * 负责生成Token
 * @author hzq
 */
public class AuthToken {
    /**
     *
     * 生成token
     * 1. 将url，AppID，密码，时间戳 拼接成为一个字符串
     * 2. 对该字符串进行加密得到Token
     *
     * 验证token
     * 1. 根据时间戳判断token 是否失效
     * 2. 验证两个token是否相等
     *
     */

    private String token;

    private Long createTime;

    private Long expireTimeInterval = 0L;

    public AuthToken(String token, long timeTamp) {
        this.token = token;
        this.createTime = timeTamp;
    }

    /**
     * 创建AuthThon， 使用MD5 加密
     * @param originalUrl
     * @param appID
     * @param password
     * @param timeTamp
     * @return
     */
    public static AuthToken generate(String originalUrl, String appID, String password, long timeTamp) {
        String token = DigestUtils.md5DigestAsHex((originalUrl + appID + password + timeTamp).getBytes(StandardCharsets.UTF_8));
        return new AuthToken(token,timeTamp);
    }

    public String getToken() {
        return token;
    }

    /**
     * 验证token 是否过期， token的过期时间为60 秒
     * @return
     */
    public boolean isExpired(){
        return (System.currentTimeMillis() - this.createTime) <= 60 * 1000;
    }

    /**
     * 验证token 是否相等
     * @param authToken
     * @return
     */
    public boolean isMatch(AuthToken authToken){
        return this.token.equals(authToken.getToken());
    }

    @Override
    public String toString() {
        return "AuthToken{" +
                "token='" + token + '\'' +
                ", createTime=" + createTime +
                ", expireTimeInterval=" + expireTimeInterval +
                '}';
    }
}
