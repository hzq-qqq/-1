package com.设计模式.接口鉴权功能.credentialStorage;

/**
 * @author hzq
 *
 * 从redis 中获取 appId，passowrd
 */
public class RedisCredentialStorage implements CredentialStorage {
    @Override
    public String getPasswordByAppId(String appId) {
        return null;
    }
}
