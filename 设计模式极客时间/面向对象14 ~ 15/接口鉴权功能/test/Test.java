package com.设计模式.接口鉴权功能.test;

import com.设计模式.接口鉴权功能.apiAuthenticator.DefaultApiAuthenticator;

/**
 * @author hzq
 */
public class Test {


    @org.junit.jupiter.api.Test
    public void test(){
        String url = "https://time.geekbang.org/column/article/171760?appID=123&token=4&createTime=9999";
        DefaultApiAuthenticator authenticator = new DefaultApiAuthenticator();
        authenticator.auth(url);
    }
}
