package com.设计模式.接口鉴权功能.apiRequest;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 处理请求的API
 * @author hzq
 */
public class ApiRequest {

    /**
     * 类中的这些属性就是url中的字段
     *
     * 1. 将token，url，时间戳，appID，密码拼接成字符串
     *
     * 2. 解析 url 得到token，时间戳，appID，密码
     *
     */
    private String baseUrl;

    private String token;

    private String appID;

    private long timeTamp;

//    get请求中设置的参数
    private Map<String,String> params;

    public ApiRequest() {
    }

    public ApiRequest(String baseUrl, String token, String appID, long timeTamp, Map<String, String> params) {
        this.baseUrl = baseUrl;
        this.token = token;
        this.appID = appID;
        this.timeTamp = timeTamp;
        this.params = params;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getToken() {
        return token;
    }

    public String getAppID() {
        return appID;
    }

    public long getTimeTamp() {
        return timeTamp;
    }

    /**
     * 解析Url，构建ApiRequest
     * URL 中包含了四部分信息：
     *
     *  1. baseUrl
     *  2. token
     *  3. appID
     *  4. 访问的时间
     *
     *  这里暂时不考虑在url 中包含的除了上面以外的其他的属性
     * @param url
     * @return
     */
    public static ApiRequest buildFromUrl(String url) {
//        String url = "https://time.geekbang.org/column/article/171760?AppID=123&token=4&createTime=9999";
        String baseUrl = "";
        int index;
        for (index = 0; index < url.length(); index++) {
            if (url.charAt(index) == '?'){
                baseUrl = url.substring(0,index);
                break;
            }
        }
        String appID = null;
        String token = null;
        Long createTime = null;
        Map<String,String> map = new ConcurrentHashMap<>();
        String[] values = url.substring(index + 1).split("&");
        for (int i = 0; i < values.length; i++) {
            String[] temp = values[i].split("=");
            if ("appID".equals(temp[0])){
                appID = temp[1];
            }else if ("token".equals(temp[0])){
                token = temp[1];
            }else if ("createTime".equals(temp[0])){
                createTime = Long.parseLong(temp[1]);
            }else {
                map.put(temp[0],temp[1]);
            }
        }
        return new ApiRequest(baseUrl,token,appID,createTime,map);
    }

    public String getOriginalUrl() {
        return this.baseUrl;
    }

    @Override
    public String toString() {
        return "ApiRequest{" +
                "baseUrl='" + baseUrl + '\'' +
                ", token='" + token + '\'' +
                ", appID='" + appID + '\'' +
                ", timeTamp=" + timeTamp +
                '}';
    }
}
