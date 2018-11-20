/*
 * File: Controller.java
 * Created By: fengtao.xue@gausscode.com
 * Date: 2018-11-20
 */

package cn.feng.dev.shiro.web;

import cn.feng.dev.entity.ResultBean;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author fengtao.xue
 */
@RestController
public class Controller {
    static Logger logger = LoggerFactory.getLogger(Controller.class);

    @RequestMapping("/test")
    public ResultBean<Integer> test(){
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return new ResultBean(ResultBean.RESULT_SUCCESS, "You are already logged in", 1);
        } else {
            return new ResultBean(ResultBean.RESULT_FAILED, "You are guest", 0);
        }
    }

    @PostMapping("/auth")
    @RequiresAuthentication
    public ResultBean<Integer> requireAuth() {
        return new ResultBean(ResultBean.RESULT_SUCCESS, "You are authenticated", 1);
    }

    @PostMapping("/permission")
    @RequiresPermissions(value = {"view", "edit"}, logical = Logical.OR)
    public ResultBean<Integer> requirePermission() {
        return new ResultBean(ResultBean.RESULT_SUCCESS, "You are visiting permission require edit,view", 1);
    }

    @PostMapping("/role")
    @RequiresRoles("admin")
    public ResultBean<Integer> requireRole() {
        return new ResultBean(ResultBean.RESULT_SUCCESS, "You are visiting require_role", 1);
    }
}
