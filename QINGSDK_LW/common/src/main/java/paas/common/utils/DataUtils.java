package paas.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  *@description:
 *  *@title:
 *  *@author: zzr
 *  *@date: 
 *  
 */
public class DataUtils {
    /**
     * 判断 是否合法
     * @return
     */
    public static String isLocation(String location){
        String result = location;
        String str = "12345678";
        if(result.length()>0){
            if(result.length()==6){
                boolean status = str.contains(result.substring(0, 1));
                if(status){
                    result = "0"; // 合法
                }else{
                    result = "1"; // 不合法
                }
            }else{
                result = "2"; // 不满足6位数
            }
        }else{
            result ="0";
        }
        return result;
    }

    public static void main(String[] args) {
        String time="2021-01-27T11:01:28";
    }
     /**
     * 判断字符串 长度是否大于指定的参数上
     * @param str
     * @param num
     * @return
     */
    public static Integer isVersion(String str, int num) {
        if (null == str || str.equals("")) {
            return 1;
        }
        if(str.length()==num){
            return 0;
        }else{
            return 1;
        }
    }




}
