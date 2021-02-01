package paas.common.utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ResourceBundle;

/**
 *  *@description:
 *  *@title:
 *  *@author: zzr
 *  *@date: 
 *  
 */
public enum PropertiesUtils {
    CONFIG;
    private static ResourceBundle bundle[] = new ResourceBundle[1];
    static{
        try{
            bundle[0] = ResourceBundle.getBundle("config");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 得到对应的properties文件名称..
     * src 目录下面对应的 properties文件
     * 2012-05-24
     * @param type
     * @return
     */
    public static String getPropertiesValue(PropertiesUtils type, String keyName){

        try{
            if(type==PropertiesUtils.CONFIG){
                return bundle[0].getString(keyName);
            }
            return "";
        }catch (Exception e) {
            //e.printStackTrace();
            return "";
        }
    }

    public static void main(String args[]) throws Exception {
        Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);
        String value = PropertiesUtils.getPropertiesValue(PropertiesUtils.CONFIG,"protocol");
        logger.info(value);
        System.out.println(value);
    }
}
