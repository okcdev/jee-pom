/*
 * File: UserSimpleAuthorizationInfo.java
 * Created By: fengtao.xue@gausscode.com
 * Date: 2018-11-21
 */

package cn.feng.dev.shiro.db;

import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author fengtao.xue
 */
public class UserSimpleAuthorizationInfo extends SimpleAuthorizationInfo {
    static Logger logger = LoggerFactory.getLogger(UserSimpleAuthorizationInfo.class);

    private UserBean userBean;

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }
}
