/*
 * File: SysUtils.java
 * Created By: fengtao.xue@gausscode.com
 * Date: 2018-11-21
 */

package cn.feng.dev.shiro.utls;

import cn.feng.dev.shiro.db.UserSimpleAuthorizationInfo;
import org.apache.catalina.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author fengtao.xue
 */
public class SysUtils {
    static Logger logger = LoggerFactory.getLogger(SysUtils.class);

    /**
     * 从缓存中获取UserSimpleAuthorizationInfo对象
     * @return
     */
    public static UserSimpleAuthorizationInfo getSysCache(){
        Subject subject = SecurityUtils.getSubject();
        if (subject.getPrincipal() != null){
            try {
                UserSimpleAuthorizationInfo simpleAuthorizationInfo = (UserSimpleAuthorizationInfo) CacheUtils.get(subject.getPrincipal().toString());
                if(simpleAuthorizationInfo != null){
                    return simpleAuthorizationInfo;
                }
                else{
                    //缓存过期
                    logger.info("================== no UserSimpleAuthorizationInfo in cache =====================");
                    return new UserSimpleAuthorizationInfo();
                }
            }catch (Exception e){
                logger.error("Error: {}\n{}", e.getMessage(), e.getStackTrace());
                return new UserSimpleAuthorizationInfo();
            }
        }
        //未登录或者authkey错误
        logger.info("================= invalid authKey or not login =====================");
        return new UserSimpleAuthorizationInfo();
    }
}
