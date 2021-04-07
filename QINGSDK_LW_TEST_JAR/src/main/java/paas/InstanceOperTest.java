package paas;

/*import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;*/
import paas.common.utils.ContextHelper;
import paas.service.instance.*;

public class InstanceOperTest {

    //private Logger logger = LoggerFactory.getLogger(InstanceOperTest.class);
    //@Test
    public void createInstance(){

        InstanceImpl instance = new InstanceImpl();
        InstanceCreateResponse response =  instance.create("02002","Qaa","0010",//0010  4-32
                                    0,0,10,3, ContextHelper.constAccessToken);
        System.out.println(response);
        //logger.info("------------------------");

    }
    //@Test
    public void deleteInstance(){

        InstanceImpl instance = new InstanceImpl();// cl-3vfp0usy cl-19ufpdkb  cl-wb9pvvjc cl-721rfhc8 cl-znco0nwb  cl-byu6bxkg cl-w3q50eqx cl-hkisu3x1
        InstanceDeleteResponse delete = instance.delete("cl-721rfhc8", ContextHelper.constAccessToken);
        System.out.println(delete);
        //logger.info("------------------------");

    }

    //@Test
    public void listInstance() {

        InstanceImpl instance = new InstanceImpl();
        InstanceListResponse listresponse = instance.list("01001", ContextHelper.constAccessToken);
        //logger.info("------------------------");
        System.out.println("response.taskStatus=" + listresponse.getTaskStatus());
        System.out.println("response.errorMsg="+ listresponse.getErrorMsg());
        System.out.println("response.serviceList=" + listresponse.getServiceList());
        System.out.println("response.errorCode=" + listresponse.getErrorCode());
        //logger.info("------------------------");

    }

    //@Test
    public void queryInstance() {

        InstanceImpl instance = new InstanceImpl();
        InstanceQueryResponse queryresponse =  instance.query("cl-mkt9vpfu", ContextHelper.constAccessToken);

        //logger.info("------------------------");

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

        //logger.info("------------------------");

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
