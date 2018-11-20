/*
 * File: AdminService.java
 * Created By: fengtao.xue@gausscode.com
 * Date: 2018-11-20
 */

package cn.feng.dev.shiro.service;

import cn.feng.dev.shiro.db.DataSource;
import cn.feng.dev.shiro.db.UserBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;


/**
 * @author fengtao.xue
 */
@Service
public class AdminService {
    static Logger logger = LoggerFactory.getLogger(AdminService.class);

    public UserBean getUser(String username) {
        // 没有此用户直接返回null
        if (! DataSource.getData().containsKey(username))
            return null;

        UserBean user = new UserBean();
        Map<String, String> detail = DataSource.getData().get(username);

        user.setUsername(username);
        user.setPassword(detail.get("password"));
        user.setRole(detail.get("role"));
        user.setPermission(detail.get("permission"));
        return user;
    }
}
