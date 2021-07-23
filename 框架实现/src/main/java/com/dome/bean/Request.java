package com.dome.bean;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 请求对象  —— 用于处理请求逻辑
 */
public class Request {
    /**
     * 请求方式： get/post/put/delete/
     */
    private final String requestMethod;

    /**
     * 请求路径
     */
    private final String requestPath;

    public Request(String requestMethod, String requestPath) {
        this.requestMethod = requestMethod;
        this.requestPath = requestPath;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }

    @Override
    public int hashCode(){
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj){
        return EqualsBuilder.reflectionEquals(this,obj);
    }

}
