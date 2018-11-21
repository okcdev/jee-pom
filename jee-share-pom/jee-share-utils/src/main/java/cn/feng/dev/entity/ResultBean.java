/*
 * File: ResultBean.java
 * Created By: fengtao.xue@gausscode.com
 * Date: 2018-11-20
 */

package cn.feng.dev.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author fengtao.xue
 */
public class ResultBean<T> {
    static Logger logger = LoggerFactory.getLogger(ResultBean.class);

    public static final int RESULT_SUCCESS = 2000;

    public static final int RESULT_FAILED = 2001;

    public static final int RESULT_EXCEPTION = 5002;

    private int code;

    private String message;

    private T data;

    public ResultBean(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
