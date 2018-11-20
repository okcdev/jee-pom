/*
 * File: FRealmService.java
 * Created By: fengtao.xue@gausscode.com
 * Date: 2018-11-20
 */

package cn.feng.dev.shiro.service;

import cn.feng.dev.shiro.entity.FToken;
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
import org.springframework.stereotype.Service;


/**
 * @author fengtao.xue
 */
@Service
public class FRealmService extends AuthorizingRealm {
    static Logger logger = LoggerFactory.getLogger(FRealmService.class);

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof FToken;
    }

    /**
     * 验证用户的权限
     * @param principalCollection shiro传入的principal，实际为authKey
     * @return 用户的角色和权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //从缓存中获取对象，返回
        return new SimpleAuthorizationInfo();
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
        //根据缓存获取
        return new SimpleAuthenticationInfo(authKey, authKey, "f_realm");
    }
}
