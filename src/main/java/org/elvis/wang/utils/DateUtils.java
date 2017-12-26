
package org.elvis.wang.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期格式化工具类
 * elvis.wang
 */
public class DateUtils {
    private final static Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);
    /**
     * 根据格式把日期转换成字符串
     * @param date 日期
     * @param template 格式
     * @return
     */
    public static String format(Date date,String template) {
        try {
            return new SimpleDateFormat(template).format(date);
        } catch (Exception e) {
            LOGGER.error("格式转换错误:"+e.getMessage(), e.fillInStackTrace());
            return "";
        }
    }
    
    /**
     * 根据格式把日期转换成Date对象
     * @param date 日期
     * @param template 格式
     * @return
     * @throws ParseException
     */
    public static Date parse(String template,String date) {
        try {
            return new SimpleDateFormat(template).parse(date);
        } catch (Exception e) {
            LOGGER.error("格式转换错误:"+e.getMessage(), e.fillInStackTrace());
            return null;
        }
    }
    
    /***
     * 获取两个月份之间的月份字符串
     * @param begMonth 开始月份
     * @param endMonth 结束月份
     * @param template
     * @return
     */
    public static String monthToMonthStr(String begMonth,String endMonth,String template) throws Exception{
        SimpleDateFormat dateFarmot = new SimpleDateFormat(template, Locale.CHINA); 
        Date beMonth = dateFarmot.parse(begMonth);
        Date enMonth = dateFarmot.parse(endMonth);
        StringBuffer monthStr = new StringBuffer("'"+begMonth+"'");
        while(enMonth.compareTo(beMonth)>=0){
            Calendar befor = Calendar.getInstance();
            befor.setTime(beMonth);
            befor.add(Calendar.MONTH,1);
            beMonth=befor.getTime();
            if(beMonth.compareTo(enMonth)<=0){
                monthStr.append(",'"+dateFarmot.format(beMonth)+"'");
            }
        }
        return monthStr.substring(0,monthStr.length());
    }
    
    
    /**
     * 计算两个日期之间相差的天数
     * @param beginDate 开始时间
     * @param endDate  结束时间
     * @return int 天数
     * @throws ParseException
     */
    public static Integer daysBetween(Date beginDate, Date endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            beginDate = sdf.parse(sdf.format(beginDate));
            endDate = sdf.parse(sdf.format(endDate));
        } catch (ParseException e) {
            LOGGER.error("计算两个日期之间相差的天数异常"+e.getMessage(), e.fillInStackTrace());
            return -1; // 异常返回-1
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(beginDate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(endDate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }
    
    /**
     * 按照日期格式，将字符串解析为日期对象
     * @param strDate
     *            (格式 yyyy-MM-dd)
     * @return
     * @throws ParseException
     */
    public static Date convertYYYYMMDDToDate(String strDate) {
        Date aDate = null;

        aDate = convertStringToDate("yyyy-MM-dd", strDate);
        return aDate;
    }

    /**
     * 按照日期格式，将字符串解析为日期对象
     * @param aMask
     *            输入字符串的格式
     * @param strDate
     *            一个按aMask格式排列的日期的字符串描述
     * @return Date 对象
     * @date: 2016年10月24日 上午11:38:02
     */
    public static final Date convertStringToDate(String aMask, String strDate) {
        SimpleDateFormat df = null;
        Date date = null;
        df = new SimpleDateFormat(aMask);
        try {
            date = df.parse(strDate);
        } catch (ParseException pe) {
            LOGGER.error("字符串解析为日期对象异常",pe.getErrorOffset());
        }
        return (date);
    }

    /**
     * 根据日期格式，返回日期按datePattern格式转换后的字符串
     * @param aDate 日期对象
     * @param template 格式例 yyyyMMddhhmmss
     * @return 格式化后的日期的页面显示字符串
     * @date: 2016年10月24日 上午11:38:02
     */
    public static final String getDateToStr(Date aDate,String template) {
        SimpleDateFormat df = null;
        String returnValue = "";
        if (aDate != null) {
            df = new SimpleDateFormat(template);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }
    /**
     * @description 根据日期和指定格式返回对应格式的date类型数据  
     * @date: 2016年10月24日 下午2:48:18
     * @param aDate
     * @param template
     * @return
     */
    public static final Date getDateToDate(Date aDate,String template) {
        SimpleDateFormat df = null;
        String returnValue = "";
        Date date=null;
        if (aDate != null) {
            df = new SimpleDateFormat(template);
            returnValue = df.format(aDate);
            try {
                date = df.parse(returnValue);
            } catch (ParseException e) {
                LOGGER.error("coop#根据格式获取日期内容异常",e);
            }
        }
        return date;
    }
    
    
    
    /***
     * 判断当前时间是否在给定时间之间
     * @date: 2016年10月24日 上午11:38:02
     * @param startTime
     * @return boolean
     */
    public static boolean isBetweenTime(String startTime, String endTime) {
        boolean istrue = false;
        if (null == startTime || null == endTime) {
            return false;
        }
        Date startDate = DateUtils.parse("yyyy-MM-dd HH:mm:ss", startTime
                + " 00:00:00");
        Date endDate = DateUtils.parse("yyyy-MM-dd HH:mm:ss", endTime
                + " 23:59:59");
        Date nowDate = new Date();
        if (startDate.getTime() <= nowDate.getTime()
                && endDate.getTime() >= nowDate.getTime()) {
            istrue = true;
        }
        return istrue;
    }

    /**
     * @description 当前时间是否大于给定时间  
     * @date: 2016年10月24日 上午11:38:02
     * @param startTime 与系统时间比较的时间
     * @param templt 时间格式yyyy-mm-dd hh:mm:ss
     * @return boolean
     */
    public static boolean isBeforeTime(String startTime,String templt) {
        boolean istrue = false;
        if (null == startTime) {
            return false;
        }
        Date startDate = DateUtils.parse(templt, startTime);
        Date nowDate = new Date();
        if (startDate.getTime() < nowDate.getTime()) {
            istrue = true;
        }
        return istrue;
    }
    
    /**
     * @description 獲取前幾天的日期
     * @date: 2016年12月6日 下午3:07:34
     * @param d  當前日期
     * @param day  前幾天數
     * @return
     *
     */
    public static Date getDateBefore(Date d, int day) {  
        Calendar now = Calendar.getInstance();  
        now.setTime(d);  
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);  
        return now.getTime();  
    }    
    
    /**
     * @description 獲取后幾天的日期
     * @date: 2016年12月6日 下午3:08:31
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfter(Date d, int day) {  
        Calendar now = Calendar.getInstance();  
        now.setTime(d);  
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);  
        return now.getTime();  
    }
    
    /** 
     * 判断某一时间是否在一个区间内 
     *  
     * @param sourceTime 
     *            时间区间,半闭合,如[10:00-20:00] 
     * @param curTime 
     *            需要判断的时间 如10:00 
     * @return  
     * @throws IllegalArgumentException 
     */  
    public static boolean isInTime(String sourceTime, Date date) {  
        if (sourceTime == null || !sourceTime.contains("-") || !sourceTime.contains(":")) {  
            throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);  
        }  
        if (date == null) {  
            throw new IllegalArgumentException("Illegal Argument arg:" + date);  
        }  
        String[] args = sourceTime.split("-");  
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");  
        String curTime = sdf.format(date);
        try {  
        	 long now = sdf.parse(curTime).getTime();  
            long start = sdf.parse(args[0]).getTime();  
            long end = sdf.parse(args[1]).getTime();  
            if (args[1].equals("00:00")) {  
                args[1] = "24:00";  
            }  
            if (end < start) {  
                if (now > end && now < start) {  
                    return false;  
                } else {  
                    return true;  
                }  
            }else {  
                if (now >= start && now < end) {  
                    return true;  
                } else {  
                    return false;  
                }  
            }  
        } catch (ParseException e) {  
        	  LOGGER.error("isInTime方法异常"+e.getMessage(), e.fillInStackTrace());
            throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);  
        }  
      
    }  
    
    
    public static void main(String[] args){
    	Date date  = DateUtils.parse("yyyy-MM-dd HH:mm:ss", "2017-04-17 00:06:00");
    	System.out.println(DateUtils.isInTime("23:50-00:05",date));
    }
		    
}
