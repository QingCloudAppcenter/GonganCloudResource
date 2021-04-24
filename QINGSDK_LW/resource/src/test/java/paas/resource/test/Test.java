package paas.resource.test;

import com.qingcloud.sdk.config.EnvContext;
import com.qingcloud.sdk.exception.QCException;
import com.qingcloud.sdk.service.AppService;
import com.qingcloud.sdk.service.Types;

public class Test {
    public static void printRegisterAppServiceResourceOutput(AppService.RegisterAppServiceResourceOutput registerAppServiceResourceOutput){

        System.out.println("==================");
        System.out.println("register app service resource start...");
        System.out.println(registerAppServiceResourceOutput.getServiceID());
        System.out.println(registerAppServiceResourceOutput.getAction());
        System.out.println(registerAppServiceResourceOutput.getRetCode());
        System.out.println(registerAppServiceResourceOutput.getMessage());
        System.out.println("register app service resource end...");
        System.out.println("==================");
    }

    public static void printDescribeAppServiceResourcesOutput(AppService.DescribeAppServiceResourcesOutput describeAppServiceResourcesOutput){
        System.out.println("==================");
        System.out.println("describe app service resources start...");
        System.out.println(describeAppServiceResourcesOutput.getAction());
        System.out.println(describeAppServiceResourcesOutput.getRetCode());
        for (Types.AppServiceResourceModel serviceResource : describeAppServiceResourcesOutput.getServiceResourceSet()) {
            System.out.println("service id:" + serviceResource.getServiceID());
            System.out.println("service name:" + serviceResource.getServiceName());
            System.out.println("service provider:" + serviceResource.getServiceProvider());
            System.out.println("service location:" + serviceResource.getLocation());
            System.out.println("service request type:" + serviceResource.getRequestType());
            System.out.println("service version:" + serviceResource.getVersion());
            System.out.println("service group:" + serviceResource.getGroup());
            System.out.println("service label:" + serviceResource.getLabel());
            System.out.println("service url:" + serviceResource.getURL());
            System.out.println("service doc dir:" + serviceResource.getDocDir());
            System.out.println("service create time:" + serviceResource.getCreateTime());
            System.out.println("service status time:" + serviceResource.getStatusTime());
            System.out.println("service service status:" + serviceResource.getServiceStatus());
            System.out.println("service status:" + serviceResource.getStatus());
            String publishTime = "";
            if (serviceResource.getStatus().equals("published")){
                publishTime = serviceResource.getStatusTime();
            }
            System.out.println("service publish time:" + publishTime);
        }
        System.out.println("describe app service resources end...");
        System.out.println("==================");
    }

    public static void printAppServiceResourceOutput(AppService.ModifyAppServiceResourceOutput modifyAppServiceResourceOutput){
        System.out.println("==================");
        System.out.println("modify app service resource start...");
        System.out.println(modifyAppServiceResourceOutput.getAction());
        System.out.println(modifyAppServiceResourceOutput.getRetCode());
        System.out.println(modifyAppServiceResourceOutput.getMessage());
        System.out.println("modify app service resources end...");
        System.out.println("==================");
    }

     public static void printPublishAppServiceResourceOutput(AppService.PublishAppServiceResourceOutput publishAppServiceResourceOutput){
        System.out.println("==================");
        System.out.println("publish app service resource start...");
        System.out.println(publishAppServiceResourceOutput.getAction());
        System.out.println(publishAppServiceResourceOutput.getRetCode());
        System.out.println(publishAppServiceResourceOutput.getMessage());
        System.out.println(publishAppServiceResourceOutput.getServiceID());
        System.out.println("publish app service resources end...");
        System.out.println("==================");
    }

     public static void printRevokeAppServiceResourceOutput(AppService.RevokeAppServiceResourceOutput revokeAppServiceResourceOutput){
        System.out.println("==================");
        System.out.println("revoke app service resource start...");
        System.out.println(revokeAppServiceResourceOutput.getAction());
        System.out.println(revokeAppServiceResourceOutput.getRetCode());
        System.out.println(revokeAppServiceResourceOutput.getMessage());
        System.out.println(revokeAppServiceResourceOutput.getServiceID());
        System.out.println("revoke app service resources end...");
        System.out.println("==================");
    }

    public static void printUnregisterAppServiceResourceOutput(AppService.UnregisterAppServiceResourceOutput unregisterAppServiceResourceOutput){
        System.out.println("==================");
        System.out.println("unregister app service resource start...");
        System.out.println(unregisterAppServiceResourceOutput.getAction());
        System.out.println(unregisterAppServiceResourceOutput.getRetCode());
        System.out.println(unregisterAppServiceResourceOutput.getMessage());
        System.out.println("unregister app service resources end...");
        System.out.println("==================");
    }

    public static void main(String[] args) {
        EnvContext context = new EnvContext(
                "EVZWGKFNMYKXTMCLELXR",
                "7tVicwbURPVMpuf5rWl8rW8lagVUEpzT7hSi3GvS");
        context.setProtocol("http");
        context.setHost("api.alphacloud.com");
        context.setPort("7777");
        context.setZone("test");
        context.setApiLang("zh-cn"); // optional, set return message i18n, default to us-en
        AppService service = new AppService(context);

        // register app service resource
        AppService.RegisterAppServiceResourceInput registerAppServiceResourceInput = new AppService.RegisterAppServiceResourceInput();
        registerAppServiceResourceInput.setServiceName("name1");
        registerAppServiceResourceInput.setServiceProvider("provider1");
        registerAppServiceResourceInput.setLocation("loc1");
        registerAppServiceResourceInput.setRequestType(1);
        registerAppServiceResourceInput.setVersion("ver1");
        registerAppServiceResourceInput.setGroup("group1");
        registerAppServiceResourceInput.setLabel("label1");
        registerAppServiceResourceInput.setURL("url1");
        registerAppServiceResourceInput.setDocDir("doc1");
        try {
            AppService.RegisterAppServiceResourceOutput registerAppServiceResourceOutput = service.registerAppServiceResource(registerAppServiceResourceInput);
            assert registerAppServiceResourceOutput != null;
            assert registerAppServiceResourceOutput.getRetCode() == 0;
            printRegisterAppServiceResourceOutput(registerAppServiceResourceOutput);

            AppService.DescribeAppServiceResourcesInput describeAppServiceResourcesInput = new AppService.DescribeAppServiceResourcesInput();
            describeAppServiceResourcesInput.setServiceID(registerAppServiceResourceOutput.getServiceID());
            AppService.DescribeAppServiceResourcesOutput describeAppServiceResourcesOutput = service.describeAppServiceResources(describeAppServiceResourcesInput);
            assert describeAppServiceResourcesOutput != null;
            assert describeAppServiceResourcesOutput.getRetCode() == 0;
            printDescribeAppServiceResourcesOutput(describeAppServiceResourcesOutput);

            AppService.ModifyAppServiceResourceInput modifyAppServiceResourceInput = new AppService.ModifyAppServiceResourceInput();
            modifyAppServiceResourceInput.setServiceID(registerAppServiceResourceOutput.getServiceID());
            modifyAppServiceResourceInput.setServiceName("name2");
            modifyAppServiceResourceInput.setServiceProvider("provider2");
            modifyAppServiceResourceInput.setLocation("loc2");
            modifyAppServiceResourceInput.setVersion("ver2");
            modifyAppServiceResourceInput.setGroup("group2");
            modifyAppServiceResourceInput.setLabel("label2");
            modifyAppServiceResourceInput.setURL("url2");
            modifyAppServiceResourceInput.setDocDir("doc2");
            modifyAppServiceResourceInput.setServiceStatus("1");
            AppService.ModifyAppServiceResourceOutput modifyAppServiceResourceOutput = service.modifyAppServiceResource(modifyAppServiceResourceInput);
            assert modifyAppServiceResourceOutput != null;
            assert modifyAppServiceResourceOutput.getRetCode() == 0;
         //   printModifyAppServiceResourceOutput(modifyAppServiceResourceOutput);
            describeAppServiceResourcesOutput = service.describeAppServiceResources(describeAppServiceResourcesInput);
            assert describeAppServiceResourcesOutput != null;
            assert describeAppServiceResourcesOutput.getRetCode() == 0;
            printDescribeAppServiceResourcesOutput(describeAppServiceResourcesOutput);

            AppService.PublishAppServiceResourceInput publishAppServiceResourceInput = new AppService.PublishAppServiceResourceInput();
            publishAppServiceResourceInput.setServiceID(registerAppServiceResourceOutput.getServiceID());
            AppService.PublishAppServiceResourceOutput publishAppServiceResourceOutput = service.publishAppServiceResource(publishAppServiceResourceInput);
            assert publishAppServiceResourceOutput != null;
            assert publishAppServiceResourceOutput.getRetCode() == 0;
            printPublishAppServiceResourceOutput(publishAppServiceResourceOutput);
            describeAppServiceResourcesOutput = service.describeAppServiceResources(describeAppServiceResourcesInput);
            assert describeAppServiceResourcesOutput != null;
            assert describeAppServiceResourcesOutput.getRetCode() == 0;
            printDescribeAppServiceResourcesOutput(describeAppServiceResourcesOutput);

            AppService.RevokeAppServiceResourceInput revokeAppServiceResourceInput = new AppService.RevokeAppServiceResourceInput();
            revokeAppServiceResourceInput.setServiceID(registerAppServiceResourceOutput.getServiceID());
            AppService.RevokeAppServiceResourceOutput revokeAppServiceResourceOutput = service.revokeAppServiceResource(revokeAppServiceResourceInput);
            assert revokeAppServiceResourceOutput != null;
            assert revokeAppServiceResourceOutput.getRetCode() == 0;
            printRevokeAppServiceResourceOutput(revokeAppServiceResourceOutput);
            describeAppServiceResourcesOutput = service.describeAppServiceResources(describeAppServiceResourcesInput);
            assert describeAppServiceResourcesOutput != null;
            assert describeAppServiceResourcesOutput.getRetCode() == 0;
            printDescribeAppServiceResourcesOutput(describeAppServiceResourcesOutput);

            AppService.UnregisterAppServiceResourceInput unregisterAppServiceResourceInput = new AppService.UnregisterAppServiceResourceInput();
            unregisterAppServiceResourceInput.setServiceID(registerAppServiceResourceOutput.getServiceID());
            AppService.UnregisterAppServiceResourceOutput unregisterAppServiceResourceOutput = service.unregisterAppServiceResource(unregisterAppServiceResourceInput);
            assert unregisterAppServiceResourceOutput != null;
            assert unregisterAppServiceResourceOutput.getRetCode() == 0;
            printUnregisterAppServiceResourceOutput(unregisterAppServiceResourceOutput);
            describeAppServiceResourcesOutput = service.describeAppServiceResources(describeAppServiceResourcesInput);
            assert describeAppServiceResourcesOutput != null;
            assert describeAppServiceResourcesOutput.getRetCode() == 0;
            printDescribeAppServiceResourcesOutput(describeAppServiceResourcesOutput);
        } catch (QCException e) {
            e.printStackTrace();
        }
    }


}
