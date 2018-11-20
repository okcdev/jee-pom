/*
 * File: UserBean.java
 * Created By: fengtao.xue@gausscode.com
 * Date: 2018-11-20
 */

package cn.feng.dev.shiro.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author fengtao.xue
 */
public class UserBean {
    static Logger logger = LoggerFactory.getLogger(UserBean.class);

    private String username;

    private String password;

    private String role;

    private String permission;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
