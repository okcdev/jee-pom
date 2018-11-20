/*
 * File: DataSource.java
 * Created By: fengtao.xue@gausscode.com
 * Date: 2018-11-20
 */

package cn.feng.dev.shiro.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


/**
 * @author fengtao.xue
 */
public class DataSource {
    static Logger logger = LoggerFactory.getLogger(DataSource.class);

    private static Map<String, Map<String, String>> data = new HashMap<>();

    static {
        Map<String, String> data1 = new HashMap<>();
        data1.put("password", "123456");
        data1.put("role", "user");
        data1.put("permission", "view");

        Map<String, String> data2 = new HashMap<>();
        data2.put("password", "654321");
        data2.put("role", "admin");
        data2.put("permission", "view,edit");

        data.put("smith", data1);
        data.put("danny", data2);
    }

    public static Map<String, Map<String, String>> getData() {
        return data;
    }
}
