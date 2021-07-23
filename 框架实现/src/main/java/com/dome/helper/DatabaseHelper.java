package com.dome.helper;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 操纵数据库常用的操作
 * @author hzq
 */
public class DatabaseHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);

    private static final ThreadLocal<Connection> CONNECTION_HOLDER;

    private static final QueryRunner QUERY_RUNNER;

    private static final BasicDataSource DATA_SOURCE;

    static {
        CONNECTION_HOLDER = new ThreadLocal<Connection>();

        QUERY_RUNNER = new QueryRunner();

        DATA_SOURCE = new BasicDataSource();
        DATA_SOURCE.setDriverClassName(ConfigHelper.getDriver());
        DATA_SOURCE.setUrl(ConfigHelper.getUrl());
        DATA_SOURCE.setUsername(ConfigHelper.getUsername());
        DATA_SOURCE.setPassword(ConfigHelper.getPassword());
    }

    /**
     * 获取数据源
     */
    public static DataSource getDataSource() {
        return DATA_SOURCE;
    }

    /**
     * 获取数据库连接
     */
    public static Connection getConnection() {
//        获取线程独有的一个connection
        Connection conn = CONNECTION_HOLDER.get();
        if (conn == null) {
            try {
                conn = DATA_SOURCE.getConnection();
            } catch (SQLException e) {
                LOGGER.error("get connection failure", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.set(conn);
            }
        }
        return conn;
    }

    /**
     * 开启事务
     */
    public static void beginTransaction(){
        Connection conn = getConnection();
        if (conn != null){
            try {
//            设置不自动提交
                conn.setAutoCommit(false);
            } catch (Exception e) {
                LOGGER.error("begin transaction failure",e);
                throw new RuntimeException(e);
            }finally {
//                设置最新的Connection 的状态 （断开   ，  连接种）
                CONNECTION_HOLDER.set(conn);
            }
        }
    }

    /**
     * 提交事务
     */
    public static void commitTransaction(){
        Connection conn = getConnection();
        if (conn != null){
            try {
                conn.commit();
                conn.close();
            } catch (Exception e) {
                LOGGER.error("commit transaction failure",e);
                throw new RuntimeException(e);
            }finally {
//                事务提交后需要线程当前的连接 —— 以免照成资源的浪费
                CONNECTION_HOLDER.remove();
            }
        }
    }

    /**
     * 回滚事务
     */
    public static void rollbackTransaction(){
        Connection conn = getConnection();
        if (conn != null){
            try {
                conn.rollback();
                conn.close();
            } catch (Exception e) {
                LOGGER.error("rollback transaction failure",e);
                throw new RuntimeException(e);
            }finally {
//                设置最新的Connection 的状态 （断开   ，  连接种）
                CONNECTION_HOLDER.remove();
            }
        }
    }
}
