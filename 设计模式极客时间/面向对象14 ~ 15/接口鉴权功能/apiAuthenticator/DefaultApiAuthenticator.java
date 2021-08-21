package com.设计模式.接口鉴权功能.apiAuthenticator;

import com.设计模式.接口鉴权功能.apiRequest.ApiRequest;
import com.设计模式.接口鉴权功能.authToken.AuthToken;
import com.设计模式.接口鉴权功能.credentialStorage.CredentialStorage;
import com.设计模式.接口鉴权功能.credentialStorage.MysqlCredentialStorage;

/**
 * @author hzq
 */
public class DefaultApiAuthenticator implements ApiAuthenticator{
//    因为这里的存储介质是不稳定的，所以组合为成员变量，后续可以动态改变
    private CredentialStorage credentialStorage;

    public DefaultApiAuthenticator(CredentialStorage credentialStorage) {
        this.credentialStorage = credentialStorage;
    }

    /**
     * 默认使用Mysql来存储appId和密码
     */
    public DefaultApiAuthenticator() {
        this.credentialStorage = new MysqlCredentialStorage();
    }


    /**
     * 根据URL，做访问权限检查
     * @param url
     */
    @Override
    public void auth(String url) {
        ApiRequest apiRequest = ApiRequest.buildFromUrl(url);
        auth(apiRequest);
    }

    /**
     * 根据ApiRequest 做权限检查
     * @param apiRequest
     */
    @Override
    public void auth(ApiRequest apiRequest) {
        String appID = apiRequest.getAppID();
        String token = apiRequest.getToken();
        long timeTamp = apiRequest.getTimeTamp();
        String originalUrl = apiRequest.getOriginalUrl();
        AuthToken clientAuthToken = new AuthToken(token,timeTamp);
        //验证token是否失效
        if (clientAuthToken.isExpired()){
            throw new RuntimeException("Token is expired");
        }
        //验证token是否相等,从存储介质中根据appID得到password
        String password = credentialStorage.getPasswordByAppId(appID);
        AuthToken serverAuthToken =  AuthToken.generate(originalUrl,appID,password,timeTamp);
        if (!serverAuthToken.isMatch(clientAuthToken)){
            throw new RuntimeException("Token verfication failed");
        }
    }
}
