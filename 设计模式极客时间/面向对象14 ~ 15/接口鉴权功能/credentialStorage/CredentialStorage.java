package com.设计模式.接口鉴权功能.credentialStorage;

/**
 * @author hzq
 */
public interface CredentialStorage {

    /**
     * 1.从存储介质中得到appID ,密码
     */

     String getPasswordByAppId(String appId);
}
