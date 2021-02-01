package paas.common.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Date工具类
 */
public class DateUtils {
    private  static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * Long类型时间->转换成日期->转成要求格式的String类型
     */
    public static String fromLongToDate(Long time) {
        Date date = new Date(time);
        return sdf.format(date);
    }

    public static void main(String[] args) {
        System.out.println(DateUtils.fromLongToDate(1568020783663L));
    }
    /**
     * 将字符串时间转换为 date 在转换为 long
     * @param create_time
     * @return
     */
    public static Long dealDateLong(String create_time) {
        long dateTime = 0l;
        if (create_time != null && create_time != "NULL" && create_time != "") {
            if (isDate(create_time)) {
                dateTime = 0l;
            } else {
                //转换日期格式(将Mon Jun 18 2018 00:00:00 GMT+0800 (中国标准时间) 转换成yyyy-MM-dd)
                create_time = create_time.replace("Z", " UTC");
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                Date d = null;//Mon Mar 06 00:00:00 CST 2017
                try {
                    d = sdf1.parse(create_time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                dateTime = d.getTime();
            }
        }
        return dateTime;

    }
    private static boolean isDate(String date)
    {
        /**
         * 判断日期格式和范围
         */
        String rexp = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
        Pattern pat = Pattern.compile(rexp);
        Matcher mat = pat.matcher(date);
        boolean dateType = mat.matches();
        return dateType;
    }

}
