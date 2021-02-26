package paas.common.utils;

import com.qingcloud.sdk.config.EnvContext;

public class ContextHelper {

    // 测试用的accessToken信息
    public static String constAccessToken="UBMCZZDPMXHOFVXBKYPB|UbVExcLt0RmS0Je8TBUfUr1AiHRllkaktC6osRox";

    /**
     * 获取查询到的EnvContext数据
     * @param accessToken
     * @return
     */
    public static EnvContext getEnvContext(String accessToken) {

        String accessKeyStr[] = accessToken.split("\\|");
        EnvContext context = new EnvContext(accessKeyStr[0], accessKeyStr[1]);
        context.setProtocol(PropertiesUtils.getPropertiesValue(PropertiesUtils.CONFIG,"protocol"));
        context.setHost(PropertiesUtils.getPropertiesValue(PropertiesUtils.CONFIG,"host"));
        context.setPort(PropertiesUtils.getPropertiesValue(PropertiesUtils.CONFIG,"port"));
        context.setZone(PropertiesUtils.getPropertiesValue(PropertiesUtils.CONFIG,"zone"));
        context.setApiLang(PropertiesUtils.getPropertiesValue(PropertiesUtils.CONFIG,"apiLang"));
        return context;

    }

    public static void main(String args[]){
        String accessToken  = "UBMCZZDPMXHOFVXBKYPB|UbVExcLt0RmS0Je8TBUfUr1AiHRllkaktC6osRox";
        String accessStr[] = accessToken.split("\\|");
        System.out.println(accessStr[0]);
        System.out.println(accessStr[1]);
    }
}
