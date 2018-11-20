/*
 * File: FengShiroFilter.java
 * Created By: fengtao.xue@gausscode.com
 * Date: 2018-11-20
 */

package cn.feng.dev.shiro.config;

import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author fengtao.xue
 */
public class FengShiroFilter extends BasicHttpAuthenticationFilter {
    static Logger logger = LoggerFactory.getLogger(FengShiroFilter.class);

    public static String AUTH_KEY = "authKey";

    /**
     * 判断用户是否需要登陆，只需检测Header中的authKey字段即可
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authKey = req.getHeader(AUTH_KEY);
        logger.debug("authKey in isLoginAttempt: {}", authKey);
        return authKey != null;
    }

    /**
     *
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response)  {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authKey = httpServletRequest.getHeader(AUTH_KEY);
        logger.debug("authKey in executeLogin: {}", authKey);

        FengToken token = new FengToken(authKey);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(token);
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    /**
     * 这里我们详细说明下为什么最终返回的都是true，即允许访问
     * 例如我们提供一个地址 GET /article
     * 登入用户和游客看到的内容是不同的
     * 如果在这里返回了false，请求会被直接拦截，用户看不到任何东西
     * 所以我们在这里返回true，Controller中可以通过 subject.isAuthenticated() 来判断用户是否登入
     * 如果有些资源只有登入用户才能访问，我们只需要在方法上面加上 @RequiresAuthentication 注解即可
     * 但是这样做有一个缺点，就是不能够对GET,POST等请求进行分别过滤鉴权(因为我们重写了官方的方法)，但实际上对应用影响不大
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request, response)) {
            try {
                executeLogin(request, response);
            } catch (Exception e) {
                response401(request, response);
            }
        }
        return true;
    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    /**
     * 将非法请求跳转到 /401
     */
    private void response401(ServletRequest req, ServletResponse resp) {
        try {
            HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
            String path = req.getServletContext().getContextPath() + "/admin/401";
            logger.debug("redirect path: {}", path);
            httpServletResponse.sendRedirect(path);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
