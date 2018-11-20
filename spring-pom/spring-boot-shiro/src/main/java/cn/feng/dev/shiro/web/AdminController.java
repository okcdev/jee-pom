/*
 * File: AdminController.java
 * Created By: fengtao.xue@gausscode.com
 * Date: 2018-11-20
 */

package cn.feng.dev.shiro.web;

import cn.feng.dev.entity.ResultBean;
import cn.feng.dev.shiro.db.UserBean;
import cn.feng.dev.shiro.service.AdminService;
import cn.feng.dev.shiro.utls.FengUtls;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author fengtao.xue
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    static Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    AdminService adminService;

    @RequestMapping("/login")
    public ResultBean<String> login(@RequestParam("username") String username,
                                     @RequestParam("password") String password) {
        UserBean userBean = adminService.getUser(username);
        if (userBean.getPassword().equals(password)) {
            return new ResultBean<>(ResultBean.RESULT_SUCCESS, "Login success", FengUtls.sign(username, password));
        } else {
            throw new UnauthorizedException();
        }
    }
    @RequestMapping("/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResultBean<Integer> unauthorized() {
        return new ResultBean<>(ResultBean.RESULT_EXCEPTION, "invalid authKey or not login", null);
    }

}
