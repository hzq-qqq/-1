package com.dome.helper;

import com.dome.ConfigConstant;
import com.dome.util.PropsUtil;

import java.util.Properties;

/**
 *  获取ConfigHelper 中的配置项   ——  获取配置文件中配置的各类属性值
 */
public class ConfigHelper {

//  这里是加载配置文件的名字  —— ConfigConstant 从上下文Config 中获取配置文件的名字

//    加载配置文件中的信息到 CONFIG_PROPS 中
    private static final Properties CONFIG_PROPS = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);

    String APP_UPLOAD_LIMIT = "smart.framework.app.upload.limit";

    /**
     * 获取jdbc 驱动
     * @return
     */
    public static String getDriver(){
       return PropsUtil.getString(CONFIG_PROPS,ConfigConstant.JDBC_DRIVER);
    }


    /**
     * 获取jdbc URL
     * @return
     */
    public static String getUrl(){
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstant.JDBC_URL);
    }


    /**
     * 获取 用户名
     * @return
     */
    public static String getUsername(){
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstant.JDBC_USERNAME);
    }


    /**
     * 获取 密码
     * @return
     */
    public static String getPassword(){
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstant.JDBC_PASSWORD);
    }


    /**
     * 获取jdbc 应用包基础名
     * @return
     */
    public static String getAppBasePackage(){
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstant.APP_BASE_PACKAGE);
    }


    /**
     * 获取 jsp 文件的位置
     *  第三个参数表示的是默认值
     * @return
     */
    public static String getJsp(){
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstant.APP_JSP_PATH,"/WEB-INF/view/");
    }


    /**
     * 获取 静态资源的路径
     *  第三个参数表示的是默认值
     *   就是在smart.properties 中是可选的配置项
     * @return
     */
    public static String getAsset(){
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstant.APP_ASSET_PATH,"/asset");
    }


    /**
     * 返回   得到文件上传的最大大小
     * @return
     */
    public static int getAppUploadLimit() {
//        返回上传文件的最大值，默认是10
        return PropsUtil.getInt(CONFIG_PROPS,ConfigConstant.APP_UPLOAD_LIMIT,10);
    }
}
