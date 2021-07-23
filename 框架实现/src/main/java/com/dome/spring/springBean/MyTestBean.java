package com.dome.spring.springBean;

/**
 * @author hzq
 */
public class MyTestBean {
    private String testStr = "test";


    public String getTestStr() {
        return testStr;
    }

    public void setTestStr(String testStr) {
        this.testStr = testStr;
    }

    @Override
    public String toString() {
        return "MyTestBean{" +
                "testStr='" + testStr + '\'' +
                '}';
    }
}
