package paas.resource.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import paas.common.utils.ContextHelper;
import paas.service.resource.*;

public class ResourceTest {
    private Logger logger = LoggerFactory.getLogger(ResourceTest.class);
    /**
     * 注册
     *	serviceName     服务资源名称      是       自定义，用以描述的服务的基本功能。
     * 	serviceProvider 服务提供商 ,      否
     * 	location        服务注册位置      否       编码规则遵循 GB/T 2260-201
     * 	requsetType     服务请求类型      是       1 表示 restful；2 表示 api 接口；3 表示 webservice；4 表示其它
     *  version         服务版本          是     形如：v1.1_r 其中 r 表示正式版（release）。v1.0_t 其中 t 表示测试版（testing）。
     * 	group           服务分组          否     自定义。
     * 	label           服务标签          否     自定义。
     * 	url             服务地址          是
     * 	docDir          服务帮助文档路径   否    提供一个 URL 地址，该地址所在页面文档包含各类型服务的具体帮助信息，包括接口参数说明，调用方式等。
     * 	accessToken     用户身份令牌      是   支持 OAuth、LDAP 等身份认证协议的TOKEN。
     */
    @Test
    public void register(){
        ResourceImpl resource = new ResourceImpl();
        ResourceRegisterResponse response = resource.register("serviceNameExample",
                "serviceproviderExample","310101", 1, "v1.0",
                "", "", "http://ip:port/",
                "http://serviceProvider/serviceName/docs.html", ContextHelper.constAccessToken);
        logger.info("------------------------");
        System.out.println("response.taskStatus=" + response.getTaskStatus());
        System.out.println("response.serviceId=" + response.getServiceId());
        System.out.println("response.errorMsg="+ response.getErrorMsg());
        System.out.println("response.errorCode=" + response.getErrorCode());
        logger.info("------------------------");
    }
    /**
     * 服务资源发布
     */
    @Test
    public void publish(){
        ResourceImpl resource = new ResourceImpl();
        ResourcePublishResponse response = resource.publish("appsr-12v20zzhss", ContextHelper.constAccessToken);
        //  任务执行状态 taskStatus 是 1 表示成功，0 表示失败
        logger.info("------------------------");
        System.out.println("response.taskStatus=" + response.getTaskStatus());
        System.out.println("response.serviceId=" + response.getServiceId());
        System.out.println("response.errorMsg="+ response.getErrorMsg());
        System.out.println("response.errorCode=" + response.getErrorCode());
        logger.info("------------------------");
    }
    /**
     * 服务资源撤销
     * serviceId    服务资源唯一标识  是
     * accessToken  用户身份令牌      是   支持 OAuth、LDAP 等身份认证协议的 TOKEN。
     */
    @Test
    public void revoke(){
        ResourceImpl resource = new ResourceImpl();
        ResourceRevokeResponse response = resource.revoke("appsr-hh5tldsf", ContextHelper.constAccessToken);
        logger.info("------------------------");
        System.out.println("response.taskStatus=" + response.getTaskStatus());
        System.out.println("response.serviceId=" + response.getServiceId());
        System.out.println("response.errorMsg="+ response.getErrorMsg());
        System.out.println("response.errorCode=" + response.getErrorCode());
        logger.info("------------------------");
    }
    /**
     * 服务资源查询
     * serviceId    服务资源唯一标识  是
     * accessToken  用户身份令牌      是   支持 OAuth、LDAP 等身份认证协议的 TOKEN。
     */
    @Test
    public void query(){
        ResourceImpl resource = new ResourceImpl();
        ResourceQueryResponse response = resource.query("12121", ContextHelper.constAccessToken);
        logger.info("------------------------");
        if(response.getTaskStatus()==1){
            System.out.println("response.TaskStatus= "+response.getTaskStatus());
            System.out.println("response.ServiceId= "+response.getServiceId());
            System.out.println("response.serviceName= "+response.getServiceName());
            System.out.println("response.serviceProvider= "+response.getServiceProvider());
            System.out.println("response.location= "+response.getLocation());
            System.out.println("response.requsetType= "+response.getRequsetType());
            System.out.println("response.version= "+response.getVersion());
            System.out.println("response.group= "+response.getGroup());
            System.out.println("response.label= "+response.getLabel());
            System.out.println("response.url= "+response.getUrl());
            System.out.println("response.docDir= "+response.getDocDir());
            System.out.println("response.releaseTime="+response.getReleaseTime());
            System.out.println("response.serviceStatus= "+response.getServiceStatus());
        }else{
            System.out.println("response.taskStatus=" + response.getTaskStatus());
            System.out.println("response.errorMsg="+ response.getErrorMsg());
            System.out.println("response.errorCode=" + response.getErrorCode());
        }
        logger.info("------------------------");
    }
    /**
     * 服务资源修改
     */
    @Test
    public void modify(){
        ResourceImpl resource = new ResourceImpl();
        /**
         *服务资源修改
         * @param serviceName      服务资源名称      是       自定义，用以描述的服务的基本功能。
         * @param serviceProvider  服务提供商 ,      否
         * @param location         服务注册位置      否       编码规则遵循 GB/T 2260-201
         * @param version          服务版本          是     形如：v1.1_r 其中 r 表示正式版（release）。v1.0_t 其中 t 表示测试版（testing）。
         * @param group            服务分组          否     自定义。
         * @param label            服务标签          否     自定义。
         * @param url              服务地址          是
         * @param docDir           服务帮助文档路径   否    提供一个 URL 地址，该地址所在页面文档包含各类型服务的具体帮助信息，包括接口参数说明，调用方式等。
         * @param accessToken      用户身份令牌      是   支持 OAuth、LDAP 等身份认证协议的TOKEN。
         * @return
         */
        ResourceModifyResponse response = resource. modify("appsr-yq0m32lo", "serviceNameExample1",
                "serviceProvider","310101",
                "v1.0", "","","","",0, ContextHelper.constAccessToken);
            System.out.println("TaskStatus: "+response.getTaskStatus());
            System.out.println("ErrorCode :"+response.getErrorCode());
            System.out.println("ErrorMsg : " +response.getErrorMsg());
    }
    /**
     * 服务资源注销
     */
    @Test
    public void unregister(){
        ResourceImpl resource = new ResourceImpl();
        /**
         * 服务资源注销
         * @param serviceId 服务资源唯一标识    是
         * @param accessToken   用户身份令牌       是 支持 OAuth、LDAP 等身份认证协议的TOKEN
         * @return
         */
        ResourceUnregisterResponse response = resource.unregister("appsr-yohmz72j", ContextHelper.constAccessToken);
        System.out.println("TaskStatus: "+response.getTaskStatus());
        System.out.println("ErrorCode :"+response.getErrorCode());
        System.out.println("ErrorMsg : " +response.getErrorMsg());
    }
    /**
     * 服务资源获取资源列表
     */
    @Test
    public void list(){
        ResourceImpl resource = new ResourceImpl();
        /**
         *服务资源获取资源列表
         * @param serviceName      服务资源名称       否       自定义，用以描述的服务的基本功能。
         *@param requsetType       服务请求类型       是       1 表示 rest 接口； 2 表示 api 接口； 3 表示 webservice； 4 表示其它。
         * @param group            服务分组          否     自定 义。
         * @param label            服务标签          否     自定义。
         * @param accessToken      用户身份令牌      是   支持 OAuth、LDAP 等身份认证协议的TOKEN。
         * @return
         */
        ResourceListResponse response = resource.list("",1,"","", ContextHelper.constAccessToken);
        if(response.getServiceList()!=null){
            System.out.println("ServiceList: "+response.getServiceList().toString());
        }
        System.out.println("TaskStatus: "+response.getTaskStatus());
        System.out.println("ErrorCode :"+response.getErrorCode());
        System.out.println("ErrorMsg : " +response.getErrorMsg());
    }


}
