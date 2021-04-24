package paas.common.utils;

import org.apache.commons.codec.binary.StringUtils;

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
    public static boolean isLocation(String location){

        if (location != null && location.length() == 6 && isInteger(location)) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        String time="2021-01-27T11:01:28";
    }

    public static boolean outLength(String val, int max, boolean required) {
        if (required) {
            return val == null || val.length() == 0 || val.length() > max;
        }  else {
            return (val != null && val.length() > max);
        }
    }

    public static boolean isInteger(String val) {
        try {
            Integer.parseInt(val);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**

     * 判断一个字符串是否为url

     * @param str String 字符串

     * @return boolean 是否为url

     * @author peng1 chen

     * **/

    public static boolean isURL(String str){
        //转换为小写
        str = str.toLowerCase();
        String regex = "^((https|http|ftp|rtsp|mms)?://)"  //https、http、ftp、rtsp、mms
                + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@
                + "(([0-9]{1,3}\\.){3}[0-9]{1,3}" // IP形式的URL- 例如：199.194.52.184
                + "|" // 允许IP和DOMAIN（域名）
                + "([0-9a-z_!~*'()-]+\\.)*" // 域名- www.
                + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\." // 二级域名
                + "[a-z]{2,6})" // first level domain- .com or .museum
                + "(:[0-9]{1,5})?" // 端口号最大为65535,5位数
                + "((/?)|" // a slash isn't required if there is no file name
                + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
        return  str.matches(regex);

    }
}
