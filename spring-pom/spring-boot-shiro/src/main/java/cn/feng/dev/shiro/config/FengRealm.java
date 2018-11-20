/*
 * File: FRealmService.java
 * Created By: fengtao.xue@gausscode.com
 * Date: 2018-11-20
 */

package cn.feng.dev.shiro.config;

import cn.feng.dev.shiro.db.UserBean;
import cn.feng.dev.shiro.service.AdminService;
import cn.feng.dev.shiro.utls.FengUtls;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * @author fengtao.xue
 */
@Service
public class FengRealm extends AuthorizingRealm {
    static Logger logger = LoggerFactory.getLogger(FengRealm.class);

    @Autowired
    AdminService adminService;

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof FengToken;
    }

    /**
     * 验证用户的权限,Role和Permission
     * @param principalCollection shiro传入的principal，实际为authKey
     * @return 用户的角色和权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //从缓存中获取对象，返回
        String username = FengUtls.getUsername(principalCollection.toString());
        UserBean user = adminService.getUser(username);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRole(user.getRole());
        Set<String> permission = new HashSet<>(Arrays.asList(user.getPermission().split(",")));
        simpleAuthorizationInfo.addStringPermissions(permission);
        return simpleAuthorizationInfo;
    }

    /**
     * 验证用户是否已经登陆，主要检验Header中的authKey在cache中是否存在
     * @param authenticationToken 要验证的token
     * @return 验证的结果
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String authKey = (String)authenticationToken.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = FengUtls.getUsername(authKey);
        if (username == null) {
            throw new AuthenticationException("token invalid");
        }

        UserBean userBean = adminService.getUser(username);
        if (userBean == null) {
            throw new AuthenticationException("User didn't existed!");
        }

        if (! FengUtls.verify(authKey, username, userBean.getPassword())) {
            throw new AuthenticationException("Username or password error");
        }

        return new SimpleAuthenticationInfo(authKey, authKey, "feng_realm");
    }
}
