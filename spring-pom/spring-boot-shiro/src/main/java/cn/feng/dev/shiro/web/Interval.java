/*
 * File: Interval.java
 * Created By: fengtao.xue@gausscode.com
 * Date: 2018-12-14
 */

package cn.feng.dev.shiro.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author fengtao.xue
 */
public class Interval {
    static Logger logger = LoggerFactory.getLogger(Interval.class);

    private int price;

    private int start;

    private int end;

    public Interval() {

    }

    public Interval(int price, int start, int end) {
        this.price = price;
        this.start = start;
        this.end = end;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Interval{" +
                "price=" + price +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
