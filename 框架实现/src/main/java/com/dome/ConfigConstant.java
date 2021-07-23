package com.dome;

/**
 * 提供相关配置项常量  —— 配置文件中可以配置的选项
 *
 * @author huangyong
 * @since 1.0.0
 */
public interface ConfigConstant {

//    配置文件的名字
    String CONFIG_FILE = "smart.properties";

//    连接数据库所需要的信息 —— 这些信息需要在开发者得配置文件中自行配置
    String JDBC_DRIVER = "smart.framework.jdbc.driver";
    String JDBC_URL = "smart.framework.jdbc.url";
    String JDBC_USERNAME = "smart.framework.jdbc.username";
    String JDBC_PASSWORD = "smart.framework.jdbc.password";

//   项目名，jsp 基础位置， 静态资源文件的位置
    String APP_BASE_PACKAGE = "smart.framework.app.base_package";
//   配置jsp文件的位置
    String APP_JSP_PATH = "smart.app.jsp_path";
//    静态资源的位置
    String APP_ASSET_PATH = "smart.framework.app.asset_path";
//    上传文件的最大的容量
    String APP_UPLOAD_LIMIT = "smart.framework.app.upload_limit";
}
