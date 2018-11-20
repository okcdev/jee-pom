/*
 * File: AdminController.java
 * Created By: fengtao.xue@gausscode.com
 * Date: 2018-11-20
 */

package cn.feng.dev.shiro.web;

import cn.feng.dev.entity.ResultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @RequestMapping("/login")
    public ResultBean<Integer> login(){
        return new ResultBean(ResultBean.RESULT_SUCCESS, "登录成功", 1);
    }

    @RequestMapping("/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResultBean<Integer> unauthorized() {
        return new ResultBean<>(401, "invalid authKey or not login", null);
    }

}
