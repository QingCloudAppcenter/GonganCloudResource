package paas.service.resource;

import com.qingcloud.sdk.config.EnvContext;
import com.qingcloud.sdk.service.AppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import paas.common.utils.BusinessErrorCode;
import paas.common.utils.ContextHelper;

import com.qingcloud.sdk.exception.QCException;
import com.qingcloud.sdk.service.Types;
import paas.common.utils.DataUtils;
import paas.common.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务资源 api
 */
public class ResourceImpl implements IResource{
    private Logger logger = LoggerFactory.getLogger(ResourceImpl.class);
    /**
     *  注冊
     * @param serviceName      服务资源名称      是       自定义，用以描述的服务的基本功能。
     * @param serviceProvider  服务提供商 ,      否
     * @param location         服务注册位置      否       编码规则遵循 GB/T 2260-201
     * @param requsetType      服务请求类型      是       1 表示 restful；2 表示 api 接口；3 表示 webservice；4 表示其它
     * @param version          服务版本          是     形如：v1.1_r 其中 r 表示正式版（release）。v1.0_t 其中 t 表示测试版（testing）。
     * @param group            服务分组          否     自定义。
     * @param label            服务标签          否     自定义。
     * @param url              服务地址          是
     * @param docDir           服务帮助文档路径   否    提供一个 URL 地址，该地址所在页面文档包含各类型服务的具体帮助信息，包括接口参数说明，调用方式等。
     * @param accessToken      用户身份令牌      是   支持 OAuth、LDAP 等身份认证协议的TOKEN。
     * @return
     */
    @Override
    public ResourceRegisterResponse register(String serviceName, String serviceProvider,
                                             String location, Integer requsetType, String version, String group, String label,
                                             String url, String docDir, String accessToken) {
        ResourceRegisterResponse registerResponse=new ResourceRegisterResponse();
        // 判断 location 是否合法
        String strLocation = DataUtils.isLocation(location);
        // 判断version 是否大于指定是数
        Integer intVersion = DataUtils.isVersion(version, 4);
        if(!"0".equals(strLocation)|| intVersion!=0){
            registerResponse.setErrorCode(BusinessErrorCode.ILLEGAL_VERSION_CRROR.getValue());
            registerResponse.setErrorMsg(BusinessErrorCode.ILLEGAL_VERSION_CRROR.getDesc());
            if(intVersion!=0){
                registerResponse.setErrorCode(BusinessErrorCode.ILLEGAL_LOCATION_CRROR.getValue());
                registerResponse.setErrorMsg(BusinessErrorCode.ILLEGAL_LOCATION_CRROR.getDesc());
            }
            registerResponse.setTaskStatus(0);
            return registerResponse;
        }
        // 获取EnvContent的参数内容
        EnvContext envContext = ContextHelper.getEnvContext(accessToken);
        AppService service = new AppService(envContext);
        AppService.RegisterAppServiceResourceInput registerResourceInput = new AppService.RegisterAppServiceResourceInput();
        AppService.RegisterAppServiceResourceOutput resourceOutput = null;
        try {
            // 请求参数
            registerResourceInput.setServiceName(serviceName);
            registerResourceInput.setServiceProvider(serviceProvider);
            registerResourceInput.setLocation(location);
            registerResourceInput.setRequestType(requsetType);
            registerResourceInput.setVersion(version);
            registerResourceInput.setGroup(group);
            registerResourceInput.setLabel(label);
            registerResourceInput.setURL(url);
            registerResourceInput.setDocDir(docDir);
            resourceOutput = service.registerAppServiceResource(registerResourceInput);
            if(resourceOutput!=null && resourceOutput.getRetCode() ==0){
                logger.debug(" 服务资源--注册成功");
                registerResponse.setServiceId(resourceOutput.getServiceID());
                registerResponse.setTaskStatus(1);
                registerResponse.setErrorCode(0);
                registerResponse.setErrorMsg("");
            }else{
                logger.debug(" 服务资源--注册失败");
                registerResponse.setErrorCode(resourceOutput.getRetCode());
                registerResponse.setErrorMsg(resourceOutput.getMessage());
                registerResponse.setTaskStatus(0);
            }
        } catch (QCException e) {
            logger.error("--请求API异常 :"+e.getMessage());
            registerResponse.setTaskStatus(0);
            registerResponse.setErrorCode(500);
            registerResponse.setErrorMsg("请求API异常 :"+e.getMessage());
        }
        return registerResponse;
    }

    /**
     *  服务资源发布
     * @param serviceId 服务资源唯一标识    是
     * @param accessToken   用户身份令牌       是 支持 OAuth、LDAP 等身份认证协议的TOKEN
     * @return
     */
    @Override
    public ResourcePublishResponse publish(String serviceId, String accessToken) {
        // 返回结果
        ResourcePublishResponse publishResponse=new ResourcePublishResponse();
        //获取EnvContent的参数内容
       EnvContext context = ContextHelper.getEnvContext(accessToken);
       AppService appService  = new AppService(context);
       AppService.PublishAppServiceResourceInput publishResourceInput = new AppService.PublishAppServiceResourceInput();
       AppService.PublishAppServiceResourceOutput publishOutput = null;
       try {
           publishResourceInput.setServiceID(serviceId);
           publishOutput = appService.publishAppServiceResource(publishResourceInput);
           if(publishOutput!=null && publishOutput.getRetCode() ==0){
               logger.debug(" 服务资源--发布成功");
               publishResponse.setServiceId(publishOutput.getServiceID());
               publishResponse.setTaskStatus(1);
               publishResponse.setErrorCode(0);
           }else{
               logger.debug(" 服务资源--发布失败");
               publishResponse.setErrorCode(publishOutput.getRetCode());
               publishResponse.setErrorMsg(publishOutput.getMessage());
               publishResponse.setTaskStatus(0);
           }
       } catch (QCException e) {
           logger.error("--请求API异常 :"+e.getMessage());
           publishResponse.setTaskStatus(0);
           publishResponse.setErrorCode(500);
           publishResponse.setErrorMsg("请求API异常 :"+e.getMessage());
       }
        return publishResponse;
    }

    /**
     * 服务资源撤销
     * @param serviceId 服务资源唯一标识    是
     * @param accessToken   用户身份令牌       是 支持 OAuth、LDAP 等身份认证协议的TOKEN
     * @return
     */
    @Override
    public ResourceRevokeResponse revoke(String serviceId, String accessToken) {
        // 返回结果
        ResourceRevokeResponse revokeResponse = new ResourceRevokeResponse();
        //获取EnvContent的参数内容
        EnvContext context = ContextHelper.getEnvContext(accessToken);
        AppService appService  = new AppService(context);
        AppService.RevokeAppServiceResourceInput revokeResourceInput = new AppService.RevokeAppServiceResourceInput();
        revokeResourceInput.setServiceID(serviceId);
        AppService.RevokeAppServiceResourceOutput revokeOutput = null;
        try {
            revokeOutput = appService.revokeAppServiceResource(revokeResourceInput);
            // 判断是否成功
            if(revokeOutput!=null && revokeOutput.getRetCode() ==0){
                logger.debug(" 服务资源--撤销成功");
                revokeResponse.setServiceId(revokeOutput.getServiceID());
                revokeResponse.setTaskStatus(1);
                revokeResponse.setErrorCode(0);
                revokeResponse.setErrorMsg("");
            }else{
                logger.debug(" 服务资源--撤销失败");
                revokeResponse.setErrorCode(revokeOutput.getRetCode());
                revokeResponse.setErrorMsg(revokeOutput.getMessage());
                revokeResponse.setTaskStatus(0);
            }
        } catch (QCException e) {
            logger.error("--请求API异常 :"+e.getMessage());
            revokeResponse.setTaskStatus(0);
            revokeResponse.setErrorCode(500);
            revokeResponse.setErrorMsg("请求API异常 :"+e.getMessage());
        }
        return revokeResponse;
    }
    /**
     * 服务资源查询
     * serviceId    服务资源唯一标识  是
     * accessToken  用户身份令牌      是   支持 OAuth、LDAP 等身份认证协议的 TOKEN。
     * @return
     */
    @Override
    public ResourceQueryResponse query(String serviceId, String accessToken) {
        // 返回结果
        ResourceQueryResponse queryResponse = new ResourceQueryResponse();
        //获取EnvContent的参数内容
        EnvContext context = ContextHelper.getEnvContext(accessToken);
        AppService appService = new AppService(context);
        AppService.DescribeAppServiceResourcesInput describeResourcesInput = new AppService.DescribeAppServiceResourcesInput();
        AppService.DescribeAppServiceResourcesOutput describeOutput = null;
        try {
            describeResourcesInput.setServiceID(serviceId);
            describeOutput = appService.describeAppServiceResources(describeResourcesInput);
            // 判断是否成功
            if(describeOutput!=null && describeOutput.getRetCode() ==0){
                List<Types.AppServiceResourceModel> serviceResourceSet = describeOutput.getServiceResourceSet();
                if (serviceResourceSet != null && serviceResourceSet.size() > 0) {
                    for (int i = 0; i < serviceResourceSet.size(); i++) {
                        Types.AppServiceResourceModel resourceModel = serviceResourceSet.get(i);
                        queryResponse.setServiceId(resourceModel.getServiceID());
                        queryResponse.setServiceName(resourceModel.getServiceName());
                        queryResponse.setServiceProvider(resourceModel.getServiceProvider());
                        queryResponse.setLocation(resourceModel.getLocation());
                        queryResponse.setRequsetType(resourceModel.getRequestType());
                        queryResponse.setVersion(resourceModel.getVersion());
                        queryResponse.setGroup(resourceModel.getGroup());
                        queryResponse.setLabel(resourceModel.getLabel());
                        queryResponse.setUrl(resourceModel.getURL());
                        queryResponse.setDocDir(resourceModel.getDocDir());
                        queryResponse.setErrorCode(0);
                        queryResponse.setErrorMsg("");
                        /**
                         * 如果 resourceModel.getStatus是published  那么返回  ReleaseTime 就取 statusTime 否则为空
                         * DescribeAppServiceResourcesOutput.serviceResourceSet.status是published，
                         * 那么返回 DescribeAppServiceResourcesOutput.serviceResourceSet.statusTime，否则返回空
                         */
                        if("published".equals(resourceModel.getStatus())){
                             queryResponse.setReleaseTime(DateUtils.dealDateLong(resourceModel.getStatusTime()));
                        }
                        queryResponse.setServiceStatus(String.valueOf(resourceModel.getServiceStatus()));
                    }
                } else {
                    logger.info(" 未查到信息");
                }
                logger.debug(" 服务资源--查询成功");
                queryResponse.setTaskStatus(1);
            }else{
                logger.debug(" 服务资源--查询失败");
                queryResponse.setErrorCode(describeOutput.getRetCode());
                queryResponse.setErrorMsg(describeOutput.getMessage());
                queryResponse.setTaskStatus(0);
            }
        } catch (QCException e) {
            logger.error("--请求API异常 :"+e.getMessage());
            queryResponse.setTaskStatus(0);
            queryResponse.setErrorCode(500);
            queryResponse.setErrorMsg("请求API异常 :"+e.getMessage());
        }
        return queryResponse;
    }
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

    @Override
    public ResourceModifyResponse modify(String serviceId, String serviceName, String serviceProvider, String location,
                                         String version, String group, String label, String url, String docDir, Integer serviceStatus, String accessToken) {
        ResourceModifyResponse response = new ResourceModifyResponse();
        if (serviceId !=null  && accessToken !=null){
            // 判断 location 是否合法
            String strLocation = DataUtils.isLocation(location);
            // 判断version 是否大于指定是数
            Integer intVersion = DataUtils.isVersion(version, 4);
            if(!"0".equals(strLocation)|| intVersion!=0){
                response.setErrorCode(BusinessErrorCode.ILLEGAL_VERSION_CRROR.getValue());
                response.setErrorMsg(BusinessErrorCode.ILLEGAL_VERSION_CRROR.getDesc());
                if(intVersion!=0){
                    response.setErrorCode(BusinessErrorCode.ILLEGAL_LOCATION_CRROR.getValue());
                    response.setErrorMsg(BusinessErrorCode.ILLEGAL_LOCATION_CRROR.getDesc());
                }
                response.setTaskStatus(0);
                return response;
            }
            //获取EnvContent的参数内容
            EnvContext context = ContextHelper.getEnvContext(accessToken);
            AppService AppService = new AppService(context);
            AppService.ModifyAppServiceResourceInput input = new AppService.ModifyAppServiceResourceInput();
             input.setServiceID(serviceId);
             input.setServiceName(serviceName);
             input.setServiceProvider(serviceProvider);
             input.setLocation(location);
             input.setVersion(version);
             input.setGroup(group);
             input.setLabel(label);
             input.setURL(url);
             input.setDocDir(docDir);
             input.setServiceStatus(serviceStatus);
            try {
                AppService.ModifyAppServiceResourceOutput output = AppService.modifyAppServiceResource(input);
                if(output!=null && output.getRetCode() ==0){
                    logger.debug("服务资源--修改成功！！！");
                    response.setErrorCode(output.getRetCode());
                    response.setErrorMsg(output.getMessage());
                    response.setTaskStatus(1);
                }else{
                    logger.debug("服务资源--修改失败！！！");
                    response.setErrorCode(output.getRetCode());
                    response.setErrorMsg(output.getMessage());
                    response.setTaskStatus(0);
                }
            }catch (Exception e){
                logger.error("程序错误"+e.getMessage());
                response.setErrorCode(0);
                response.setTaskStatus(5000);
                response.setErrorMsg("程序错误"+e.getMessage());
            }
        }else{
            logger.info("服务资源唯一标识或用户令牌 不可为空");
            response.setTaskStatus(0);
            response.setErrorCode(BusinessErrorCode.NULL_REQUIED_PARA_ERROR.getValue());
            response.setErrorMsg(BusinessErrorCode.NULL_REQUIED_PARA_ERROR.getDesc());
        }
        return response;
    }
    /**
     * 服务资源注销
     * @param serviceId 服务资源唯一标识    是
     * @param accessToken   用户身份令牌       是 支持 OAuth、LDAP 等身份认证协议的TOKEN
     * @return
     */
    @Override
    public ResourceUnregisterResponse unregister(String serviceId, String accessToken) {
        ResourceUnregisterResponse response = new ResourceUnregisterResponse();
        //获取EnvContent的参数内容
        if(serviceId !=null && accessToken !=null) {
            EnvContext context = ContextHelper.getEnvContext(accessToken);
            AppService AppService = new AppService(context);
            AppService.UnregisterAppServiceResourceInput unregisterAppServiceResourceInput = new AppService.UnregisterAppServiceResourceInput();
            unregisterAppServiceResourceInput.setServiceID(serviceId);
            try {
                AppService.UnregisterAppServiceResourceOutput output = AppService.unregisterAppServiceResource(unregisterAppServiceResourceInput);
                if(output!=null && output.getRetCode() ==0){
                    logger.debug("服务资源--注销成功！！！");
                    response.setErrorCode(output.getRetCode());
                    response.setErrorMsg(output.getMessage());
                    response.setTaskStatus(1);
                }else{
                    logger.debug("服务资源--注销失败！！！");
                    response.setErrorCode(output.getRetCode());
                    response.setErrorMsg(output.getMessage());
                    response.setTaskStatus(0);
                }
            }catch (Exception e){
                logger.error("程序错误"+e.getMessage());
                response.setErrorCode(0);
                response.setTaskStatus(5000);
                response.setErrorMsg("程序错误"+e.getMessage());
            }
        }else{
            logger.info("服务资源唯一标识或用户令牌 不可为空");
            response.setTaskStatus(0);
            response.setErrorCode(BusinessErrorCode.NULL_REQUIED_PARA_ERROR.getValue());
            response.setErrorMsg(BusinessErrorCode.NULL_REQUIED_PARA_ERROR.getDesc());
        }
        return response;
    }
    /**
     *服务资源获取资源列表
     * @param serviceName      服务资源名称       否       自定义，用以描述的服务的基本功能。
     *@param requsetType       服务请求类型       是       1 表示 rest 接口； 2 表示 api 接口； 3 表示 webservice； 4 表示其它。
     * @param group            服务分组          否     自定 义。
     * @param label            服务标签          否     自定义。
     * @param accessToken      用户身份令牌      是   支持 OAuth、LDAP 等身份认证协议的TOKEN。
     * @return
     */
    @Override
    public ResourceListResponse list(String serviceName, Integer requsetType, String group, String label, String accessToken) {
        ResourceListResponse response = new ResourceListResponse();
        if(requsetType !=null  && accessToken !=null) {
            EnvContext context = ContextHelper.getEnvContext(accessToken);
            AppService AppService  = new AppService(context);
            AppService.DescribeAppServiceResourcesInput describeAppServiceResourcesInput = new AppService.DescribeAppServiceResourcesInput();
            describeAppServiceResourcesInput.setServiceName(serviceName);
            describeAppServiceResourcesInput.setRequestType(requsetType);
            describeAppServiceResourcesInput.setGroup(group);
            describeAppServiceResourcesInput.setLabel(label);
            try{
                AppService.DescribeAppServiceResourcesOutput output =  AppService.describeAppServiceResources(describeAppServiceResourcesInput);

                if(output!=null && output.getRetCode() ==0){
                    logger.debug("服务资源--获取列表成功！！！");
                    List<String> listService = new ArrayList<String>();
                    for (Types.AppServiceResourceModel service:output.getServiceResourceSet()) {
                        listService.add(service.getServiceID());
                    }
                    response.setServiceList(listService.toString());
                    response.setErrorCode(output.getRetCode());
                    response.setErrorMsg(output.getMessage());
                    response.setTaskStatus(1);
                }else{
                    logger.debug("服务资源--获取列表失败！！！");
                    response.setErrorCode(output.getRetCode());
                    response.setErrorMsg(output.getMessage());
                    response.setTaskStatus(0);
                }
            }catch (Exception e){
                logger.error("程序错误"+e.getMessage());
                response.setErrorCode(0);
                response.setTaskStatus(5000);
                response.setErrorMsg("程序错误"+e.getMessage());
            }
        }else {
            logger.info("服务请求类型或用户令牌 不可为空");
            response.setTaskStatus(1);
            response.setErrorCode(BusinessErrorCode.NULL_REQUIED_PARA_ERROR.getValue());
            response.setErrorMsg(BusinessErrorCode.NULL_REQUIED_PARA_ERROR.getDesc());
        }
        return response;
    }
}
