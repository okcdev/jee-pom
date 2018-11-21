/*
 * File: AdminService.java
 * Created By: fengtao.xue@gausscode.com
 * Date: 2018-11-20
 */

package cn.feng.dev.shiro.service;

import cn.feng.dev.entity.ResultBean;
import cn.feng.dev.shiro.db.DataSource;
import cn.feng.dev.shiro.db.UserBean;
import cn.feng.dev.shiro.db.UserSimpleAuthorizationInfo;
import cn.feng.dev.shiro.utls.CacheUtils;
import cn.feng.dev.shiro.utls.SysUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


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


    public ResultBean<Map> login(String username, String password){
        HashMap<String, Object> data = new HashMap<>();
        UserBean userBean = getUser(username);
        if (userBean.getPassword().equals(password)){
            String authKey = UUID.randomUUID().toString();
            UserSimpleAuthorizationInfo userSimpleAuthorizationInfo = new UserSimpleAuthorizationInfo();
            userSimpleAuthorizationInfo.setUserBean(userBean);
            userSimpleAuthorizationInfo.addRole(userBean.getRole());
            userSimpleAuthorizationInfo.addStringPermission(userBean.getPermission());
            data.put("authKey", authKey);
            data.put("userInfo", userSimpleAuthorizationInfo);
            CacheUtils.put(authKey, userSimpleAuthorizationInfo);
            return new ResultBean<>(ResultBean.RESULT_SUCCESS, "登录成功", data);
        }
        else {
            return new ResultBean<>(ResultBean.RESULT_FAILED, "登录失败", null);
        }
    }

    public ResultBean<UserBean> getLoginUses(){
        UserBean userBean = SysUtils.getSysCache().getUserBean();
        if (userBean != null){
            return new ResultBean<>(ResultBean.RESULT_SUCCESS, "调用成功", userBean);
        }
        else {
            return new ResultBean<>(ResultBean.RESULT_FAILED, "调用失败", null);
        }
    }
}
