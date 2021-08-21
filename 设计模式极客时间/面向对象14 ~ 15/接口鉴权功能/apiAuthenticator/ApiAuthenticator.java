package com.设计模式.接口鉴权功能.apiAuthenticator;

import com.设计模式.接口鉴权功能.apiRequest.ApiRequest;

/**
 * @author hzq
 */
public interface ApiAuthenticator {


    /**
     * 根据url 构建一个ApiRequest
     * @param url
     */
    void auth(String url);

    /**
     * 通过给定的ApiRequest 来验证 1.token是否过期 2.token是否合法
     * @param apiRequest
     */
    void auth(ApiRequest apiRequest);
}
