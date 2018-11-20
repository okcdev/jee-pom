/*
 * File: FengToken.java
 * Created By: fengtao.xue@gausscode.com
 * Date: 2018-11-20
 */

package cn.feng.dev.shiro.config;

import cn.feng.dev.utils.JSONUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author fengtao.xue
 */
public class FengToken implements AuthenticationToken {
    static Logger logger = LoggerFactory.getLogger(FengToken.class);

    //秘钥
    private String authKey;

    public FengToken(String authKey) {
        this.authKey = authKey;
    }

    @Override
    public Object getPrincipal() {
        return authKey;
    }

    @Override
    public Object getCredentials() {
        return authKey;
    }

    @Override
    public String toString() {
        return JSONUtils.toJson(this);
    }
}
