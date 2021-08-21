package com.设计模式.接口鉴权功能.credentialStorage;

import com.设计模式.接口鉴权功能.credentialStorage.CredentialStorage;

/**
 * @author hzq
 *
 * 从redis 中获取 appId，passowrd
 */
public class MysqlCredentialStorage implements CredentialStorage {
    @Override
    public String getPasswordByAppId(String appId) {
        return null;
    }
}
