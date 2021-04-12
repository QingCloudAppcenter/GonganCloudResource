package paas.instance.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import paas.common.utils.ContextHelper;
import paas.service.instance.*;

import java.util.ArrayList;
import java.util.List;

public class InstanceOperTest {

    private Logger logger = LoggerFactory.getLogger(InstanceOperTest.class);
    @Test
    public void createInstance(){

        InstanceImpl instance = new InstanceImpl();
      /*  01001 QingMR  02001、02003  内存在[4~32]G之间
        01002 HBase
        01003 QingCloud MySQL Plus
        01004 Redis Standalone
        01006 ELK 内存只能为  [2048, 4096, 6144, 8192]
        02002 Storm 内存只能为  [2048, 4096, 6144, 8192]*/
        InstanceCreateResponse response =  instance.create("01003","QingCloud MySQL Plus",null,//0010  4-32
                                    2,2,10,3,ContextHelper.constAccessToken);
        logger.info("------------------------");
        System.out.println("response.taskStatus=" + response.getTaskStatus());
        System.out.println("response.errorMsg="+ response.getErrorMsg());
        System.out.println("response.errorCode=" + response.getErrorCode());
        System.out.println("response.ServiceManageUrls=" + response.getServiceManageUrls());
        System.out.println("response.ServiceAPIUrls=" + response.getServiceAPIUrls());
        System.out.println("response.InstanceId=" + response.getInstanceId());
        logger.info("------------------------");


    }
    @Test
    public void deleteInstance(){

        InstanceImpl instance = new InstanceImpl();// cl-3vfp0usy cl-19ufpdkb  cl-wb9pvvjc cl-721rfhc8 cl-znco0nwb  cl-byu6bxkg cl-w3q50eqx cl-hkisu3x1
        InstanceDeleteResponse delete = instance.delete("cl-pktza80m", ContextHelper.constAccessToken);
        logger.info("------------------------");
        System.out.println("response.taskStatus=" + delete.getTaskStatus());
        System.out.println("response.errorMsg="+ delete.getErrorMsg());
        System.out.println("response.errorCode=" + delete.getErrorCode());
        logger.info("------------------------");

    }
    /**
     * 服务资源获取资源列表
     */
    @Test
    public void listInstance() {

        InstanceImpl instance = new InstanceImpl();
        /**
         *服务资源获取资源列表
         * @param serviceType      服务类型       是
         * @param accessToken      用户身份令牌      是   支持 OAuth、LDAP 等身份认证协议的TOKEN。
         * @return
         */
        InstanceListResponse listresponse = instance.list("01002",ContextHelper.constAccessToken);
        logger.info("------------------------");
        System.out.println("response.taskStatus=" + listresponse.getTaskStatus());
        System.out.println("response.errorMsg="+ listresponse.getErrorMsg());
        System.out.println("response.serviceList=" + listresponse.getServiceList());
        System.out.println("response.errorCode=" + listresponse.getErrorCode());
        logger.info("------------------------");

    }
    /**
     * 服务实例查询
     */
    @Test
    public void testList(){
        List list = new ArrayList();
        for(int i=0;i<100;i++){
            list.add(i+"----");
        }
    }

    @Test
    public void queryInstance() {

        InstanceImpl instance = new InstanceImpl();

        /**
         *服务实例查询
         * @param instanceId      服务资源Id       是
         * @param accessToken      用户身份令牌      是   支持 OAuth、LDAP 等身份认证协议的TOKEN。
         * @return
         */
        InstanceQueryResponse queryresponse =  instance.query("cl-f3cbie8s",ContextHelper.constAccessToken);

        logger.info("------------------------");
        System.out.println("response.taskStatus="+ queryresponse.getTaskStatus());
        System.out.println("response.serviceType="+ queryresponse.getServiceType());
        System.out.println("response.errorMsg="+ queryresponse.getErrorMsg());
        System.out.println("response.healthStatus=" + queryresponse.getHealthStatus());
        System.out.println("response.compute=" + queryresponse.getCompute());
        System.out.println("response.cpuCores=" + queryresponse.getCpuCores());
        System.out.println("response.memory=" + queryresponse.getMemory());
        System.out.println("response.storage=" + queryresponse.getStorage());
        System.out.println("response.nodes=" + queryresponse.getNodes());
        System.out.println("response.serviceManageUrls=" + queryresponse.getServiceManageUrls());
        System.out.println("response.serviceAPIUrls=" + queryresponse.getServiceAPIUrls());
        System.out.println("response.errorCode=" +queryresponse.getErrorCode());

        logger.info("------------------------");

    }

    @Test
    public void modifyInstance(){

        InstanceImpl instance = new InstanceImpl();// cl-3vfp0usy cl-19ufpdkb  cl-wb9pvvjc cl-721rfhc8 cl-znco0nwb  cl-byu6bxkg cl-w3q50eqx cl-hkisu3x1
        InstanceModifyResponse modify = instance.modify("cl-pktza80m","Storm1","","",2, ContextHelper.constAccessToken);
        logger.info("------------------------");
        System.out.println("response.taskStatus=" + modify.getTaskStatus());
        System.out.println("response.errorMsg="+ modify.getErrorMsg());
        System.out.println("response.errorCode=" + modify.getErrorCode());
        logger.info("------------------------");

    }

    /*@Test
    最原始版的代码...
    public void testDescribeInstances() {
        EnvContext context = new EnvContext("UBMCZZDPMXHOFVXBKYPB", "UbVExcLt0RmS0Je8TBUfUr1AiHRllkaktC6osRox");
        context.setProtocol("https");
        context.setHost("api.qingcloud.com");
        context.setPort("443");
        context.setZone("pek3b");
        context.setApiLang("zh-cn"); // optional, set return message i18n, default to us-en
        InstanceService service = new InstanceService(context);

        InstanceService.DescribeInstancesInput input = new InstanceService.DescribeInstancesInput();
        input.setLimit(1);

        try {
            InstanceService.DescribeInstancesOutput output = service.describeInstances(input);
            System.out.println(output.getRetCode());
            for (Types.InstanceModel model : output.getInstanceSet()) {
                System.out.println("==================");
                System.out.println(model.getInstanceID());
                System.out.println(model.getInstanceName());
                System.out.println(model.getImage().getImageID());
                for (Types.NICVxNetModel vxNetModel : model.getVxNets()) {
                    System.out.println("==================");
                    System.out.println(vxNetModel.getVxNetID());
                    System.out.println(vxNetModel.getVxNetType());
                }
            }
        } catch (QCException e) {
            e.printStackTrace();
        }
    }*/
}
