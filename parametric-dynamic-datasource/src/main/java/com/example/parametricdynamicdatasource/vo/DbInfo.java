package com.example.parametricdynamicdatasource.vo;

import lombok.Data;

/**
 * 数据库连接信息
 * @author qzz
 */
@Data
public class DbInfo {

    private String ip;
    private String port;
    private String dbName;
    private String driverClassName;
    private String userName;
    private String password;
}
