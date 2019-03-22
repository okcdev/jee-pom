/*
 * File: PriceCheck.java
 * Created By: fengtao.xue@gausscode.com
 * Date: 2018-12-14
 */

package cn.feng.dev.shiro.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Vector;


/**
 * @author fengtao.xue
 */
public class PriceCheck {
    static Logger logger = LoggerFactory.getLogger(PriceCheck.class);


    /**
     * 获取可能的日期区间
     * @param ranges
     * @return
     */
    public static Vector<Interval> getDateInterval(Vector<Interval> ranges){
        Vector<Interval> intervalVector = new Vector<>();
        //获取日期数组
        int[] dayArray = new int[2 * ranges.size()];
        for (int i = 0; i < ranges.size(); i++){
            dayArray[2*i] = ranges.get(i).getStart();
            dayArray[2*i+1] = ranges.get(i).getEnd();
        }
        logger.debug("dayArray:{}", dayArray);
        //日期递增排序
        Arrays.sort(dayArray);
        logger.debug("dayArray:{}", dayArray);
        //划分区间
        for (int i = 0; i < dayArray.length-1; i++){
            if (dayArray[i] != dayArray[i+1]){
                Interval param = new Interval();
                param.setStart(dayArray[i]);
                param.setEnd(dayArray[i + 1]);
                intervalVector.add(param);
            }
        }
        return intervalVector;
    }

    /**
     * 获取区间最低价格
     * @param ranges
     * @return
     */
    public static Vector<Interval> getLowestPrices(Vector<Interval> ranges) {
        logger.debug("********************获取区间最低价格开始***********************");
        //获取可能的日期区间
        Vector<Interval> result = getDateInterval(ranges);
        for (Interval interval : result){
            logger.debug("处理区间:{}", interval.toString());
            for (Interval range : ranges){
                if (interval.getStart() >= range.getStart() && interval.getEnd() <= range.getEnd()){
                    if (interval.getPrice() == 0 || interval.getPrice() > range.getPrice()){
                        interval.setPrice(range.getPrice());
                    }
                }
            }
            logger.debug("interval:{}", interval);
        }

        logger.debug("********************获取区间最低价格结束***********************");
        return result;
    }

    public static void main(String[] args) {

        Interval inter1 = new Interval(20,1,6);
        Interval inter2 = new Interval(10,3,5);
        Interval inter3 = new Interval(25, 5,9);
        Interval inter4 = new Interval(20, 9,12);
        Interval inter5 = new Interval(8, 10,15);

        Vector<Interval> ranges = new Vector<>();
        ranges.add(inter1);
        ranges.add(inter2);
        ranges.add(inter3);
        ranges.add(inter4);
        ranges.add(inter5);

        for (Interval interval : getLowestPrices(ranges)){
            logger.debug("start:{},end:{},price:{}", interval.getStart(), interval.getEnd(), interval.getPrice());
        }
    }
}
