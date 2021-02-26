package paas.instance.test;

import com.qingcloud.sdk.config.EnvContext;
import com.qingcloud.sdk.exception.QCException;
import com.qingcloud.sdk.service.SimpleClusterService;
import com.qingcloud.sdk.service.Types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;




public class Test_1 {




    public static void main(String[] args) {
        HashMap<String, String> map_Version_ResourceType = new HashMap();
        map_Version_ResourceType.put("appv-y0dunpcc", "01002");
        map_Version_ResourceType.put("appv-fjjyg8ae", "01003");
        map_Version_ResourceType.put("appv-e97gz6wz", "01004");
        map_Version_ResourceType.put("appv-dy6k7pd0", "01006");
        map_Version_ResourceType.put("appv-wvbdsw6a", "02002");


        EnvContext context = new EnvContext(
                "UBMCZZDPMXHOFVXBKYPB",
                "UbVExcLt0RmS0Je8TBUfUr1AiHRllkaktC6osRox");
        context.setProtocol("https");
        context.setHost("api.qingcloud.com");
        context.setPort("443");
        context.setZone("pek3d");
        context.setApiLang("zh-cn"); // optional, set return message i18n, default to us-en
        SimpleClusterService service = new SimpleClusterService(context);

        SimpleClusterService.DescribeClustersInput describeClustersInput = new SimpleClusterService.DescribeClustersInput();
        List<String> clusters = new ArrayList<>();
        clusters.add("cl-hkisu3x1");
       /* clusters.add("cl-znco0nwb");
        clusters.add("cl-byu6bxkg");
        clusters.add("cl-19ufpdkb");
        clusters.add("cl-721rfhc8");
        clusters.add("cl-3vfp0usy");
        clusters.add("cl-wb9pvvjc");
        clusters.add("cl-w3q50eqx");*/
        describeClustersInput.setClusters(clusters);
        describeClustersInput.setVerbose(1);
        describeClustersInput.setStatus("active");

        try{
            SimpleClusterService.DescribeClustersOutput describeClustersOutput = service.describeClusters(describeClustersInput);
            for (Types.SimpleClusterModel cluster: describeClustersOutput.getClusterSet()) {
                System.out.println("instanceId: " + cluster.getClusterID());
                System.out.println("serviceName: " + cluster.getName());
                if (cluster.getAppVersion().equals("appv-v71be1fi")) {
                    System.out.println("serviceType: " + cluster.getTags().get(0).get("tag_name"));
                } else {
                    System.out.println("serviceType: " + map_Version_ResourceType.get(cluster.getAppVersion()));
                }
                System.out.println("cpuCores: " + cluster.getNodes().get(0).getCPU());
                System.out.println("memory: " + cluster.getNodes().get(0).getMemory());
                System.out.println("storage: " + cluster.getNodes().get(0).getStorageSize());
                if (cluster.getAppVersion().equals("appv-v71be1fi")) {
                    System.out.println("nodes: " + cluster.getRoleCount().get("slave"));
                } else {
                    for (Object count : cluster.getRoleCount().values()) {
                        System.out.println("nodes: " + count);
                        break;
                    }
                }
                System.out.println("serviceAPIUrls: " + cluster.getEndpoints());
            }
        } catch (QCException e) {
            e.printStackTrace();
        }
    }
}
