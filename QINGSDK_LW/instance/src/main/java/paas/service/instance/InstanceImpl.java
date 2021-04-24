package paas.service.instance;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qingcloud.sdk.config.EnvContext;
import com.qingcloud.sdk.exception.QCException;
import com.qingcloud.sdk.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import paas.common.utils.*;

import paas.entity.CreateConf;
import paas.entity.CreateConfParam;
import paas.entity.MachineConf;

import java.io.IOException;
import java.util.*;

public class InstanceImpl implements IInstance {

    private Logger logger = LoggerFactory.getLogger(InstanceImpl.class);
    private ArrayList<String> validServceType = new ArrayList<>();

    public void setValidServceType(ArrayList<String> validServceType) {
        this.validServceType = validServceType;
    }


    @Override
    public InstanceCreateResponse create(String serviceType, String serviceName, String compute, int cpuCores, int memory, int storage, int nodes, String accessToken) {
        //返回结果
        InstanceCreateResponse instanceCreateResponse = new InstanceCreateResponse();
        if (!this.validServceType.contains(serviceType)){
            instanceCreateResponse.setTaskStatus("0");
            instanceCreateResponse.setReceiveStatus(0);
            instanceCreateResponse.setParamsCheckResult(0);
            instanceCreateResponse.setErrorCode(BusinessErrorCode.ILLEGAL_SERVICE_TYPE.getValue());
            instanceCreateResponse.setErrorMsg(BusinessErrorCode.ILLEGAL_SERVICE_TYPE.getDesc());
            return instanceCreateResponse;
        }

        if(serviceName == null || "".equals(serviceName) || accessToken == null || "".equals(accessToken)) {
            logger.info("服务实例唯一标识或用户令牌 不可为空");
            instanceCreateResponse.setTaskStatus("0");
            instanceCreateResponse.setReceiveStatus(0);
            instanceCreateResponse.setParamsCheckResult(0);
            instanceCreateResponse.setErrorCode(BusinessErrorCode.ILLEGAL_SERVICE_TYPE.getValue());
            instanceCreateResponse.setErrorMsg(BusinessErrorCode.ILLEGAL_SERVICE_TYPE.getDesc());
            return instanceCreateResponse;
        }
        if (serviceName.length() > 1024 || serviceType.length() > 1024 || compute.length() > 128 || accessToken.length() > 128) {
            instanceCreateResponse.setTaskStatus("0");
            instanceCreateResponse.setReceiveStatus(0);
            instanceCreateResponse.setParamsCheckResult(0);
            instanceCreateResponse.setErrorCode(BusinessErrorCode.ILLEGAL_SERVICE_TYPE.getValue());
            instanceCreateResponse.setErrorMsg("参数不合法");
            return instanceCreateResponse;
        }

        boolean b = checkMemory(serviceType, compute, memory);
        //判断内存限制 02002 ，01006 内存只能为  [2048, 4096, 6144, 8192]
        if (b){
            instanceCreateResponse.setTaskStatus("0");
            instanceCreateResponse.setReceiveStatus(0);
            instanceCreateResponse.setParamsCheckResult(0);
            instanceCreateResponse.setErrorCode(BusinessErrorCode.MEMORY_SIZE.getValue());
            instanceCreateResponse.setErrorMsg("memory size: " + BusinessErrorCode.MEMORY_SIZE.getDesc());
            logger.debug("内存必须在[2，4，6，8]GB范围中 " );
            return instanceCreateResponse;
        }

        b = checkCpu(serviceType, compute, cpuCores);
        if (b){
            instanceCreateResponse.setTaskStatus("0");
            instanceCreateResponse.setReceiveStatus(0);
            instanceCreateResponse.setParamsCheckResult(0);
            instanceCreateResponse.setErrorCode(BusinessErrorCode.MEMORY_SIZE.getValue());
            instanceCreateResponse.setErrorMsg("cpu cores 不合法");
            logger.debug("cpu cores 不合法" );
            return instanceCreateResponse;
        }

        //获取EnvContent的参数内容
        EnvContext context = ContextHelper.getEnvContext(accessToken);
        if (context == null) {
            instanceCreateResponse.setTaskStatus("0");
            instanceCreateResponse.setReceiveStatus(0);
            instanceCreateResponse.setParamsCheckResult(0);
            instanceCreateResponse.setErrorCode(BusinessErrorCode.UNKNOWN_SERVICETYPE_ERROR.getValue());
            instanceCreateResponse.setErrorMsg("accessToken 不合法");
            return instanceCreateResponse;
        }

        AppService appService = new AppService(context);
        try {
            //根据serviceType获取conf信息
            CreateConf createConf = getConfType(serviceType,serviceName,compute,cpuCores,memory,storage,nodes);
            if (createConf==null){
                instanceCreateResponse.setTaskStatus("0");
                instanceCreateResponse.setReceiveStatus(0);
                instanceCreateResponse.setParamsCheckResult(0);
                instanceCreateResponse.setErrorCode(BusinessErrorCode.UNKNOWN_SERVICETYPE_ERROR.getValue());
                instanceCreateResponse.setErrorMsg("请检查："+serviceType+"没有该编号的资源信息");
                logger.debug("请检查："+serviceType+"没有该编号的资源信息");
                return instanceCreateResponse;
            }

            //设置请求参数
            AppService.DeployAppVersionInput deployAppVersionInput = new AppService.DeployAppVersionInput();
            deployAppVersionInput.setVersionID(createConf.getVersion());
            String ConfString = JSONObject.toJSONString(createConf);
            logger.info("创建实例请求的版本ID（将要部署应用的版本ID）："+createConf.getVersion());
            logger.debug("集群的配置信息："+ConfString);
            deployAppVersionInput.setConf(ConfString);
            //发送请求
            AppService.DeployAppVersionOutput deployAppVersionOutput = appService.deployAppVersion(deployAppVersionInput);
            logger.debug("创建实例请求返回结果（DeployAppVersionOutput）："+JSONObject.toJSONString(deployAppVersionOutput));
            if (deployAppVersionOutput.getRetCode()==0) {
                logger.info("创建实例请求 :  成功 ");
                instanceCreateResponse.setInstanceId(deployAppVersionOutput.getClusterID());
            }else if (deployAppVersionOutput.getRetCode()==-1){
                logger.error("创建实例请求失败: "+deployAppVersionOutput.getMessage());
                instanceCreateResponse.setTaskStatus("0");
                instanceCreateResponse.setReceiveStatus(0);
                instanceCreateResponse.setErrorCode(deployAppVersionOutput.getRetCode());
                instanceCreateResponse.setErrorMsg(deployAppVersionOutput.getMessage());
                return instanceCreateResponse;
            }else {
                logger.error("创建实例请求失败: "+deployAppVersionOutput.getMessage());
                instanceCreateResponse.setTaskStatus("0");
                instanceCreateResponse.setReceiveStatus(0);
                instanceCreateResponse.setErrorCode(deployAppVersionOutput.getRetCode());
                instanceCreateResponse.setErrorMsg(deployAppVersionOutput.getMessage());
                return instanceCreateResponse;
            }
            //由于01001、02001、02003都对应appv-v71be1fi，所以这里在创建这三种服务实例时，需要使用一个tag来标记这个服务实例的实际服务类型。
            if (serviceType.equals("01001") ||serviceType.equals("02001") ||serviceType.equals("02003") ||serviceType.equals("01005")||serviceType.equals("02004")){
                logger.info("01001、02001、02003  三种服务实例时，需要使用一个tag来标记这个服务实例");
                TagService tagService = new TagService(context);
                TagService.DescribeTagsInput describeTagsInput = new TagService.DescribeTagsInput();
                describeTagsInput.setSearchWord(serviceType);
                describeTagsInput.setVerbose(1);
                TagService.DescribeTagsOutput describeTagsOutput = tagService.describeTags(describeTagsInput);
                TagService.AttachTagsInput attachTagsInput = new TagService.AttachTagsInput();
                Types.ResourceTagPairModel resourceTagPairModel = new Types.ResourceTagPairModel();
                resourceTagPairModel.setResourceType("cluster");
                resourceTagPairModel.setResourceID(deployAppVersionOutput.getClusterID());
                resourceTagPairModel.setTagID(describeTagsOutput.getTagSet().get(0).getTagID());
                ArrayList<Types.ResourceTagPairModel> ResourceTagPairModelList = new ArrayList<>();
                ResourceTagPairModelList.add(resourceTagPairModel);
                attachTagsInput.setResourceTagPairs(ResourceTagPairModelList);
                TagService.AttachTagsOutput attachTagsOutput = tagService.attachTags(attachTagsInput);
                logger.info("attachTagsOutput: {}", attachTagsOutput);
                logger.info("01001、02001、02003  三种服务实例tag 标记成功");
            }


            //查询任务执行状态
            JobService.DescribeJobsOutput describeJobsOutput =null;
            String status ="pending";

            //根据任务id查询创建进度
            String jobID = deployAppVersionOutput.getJobID();
            logger.info("查询创建进度,任务id为："+jobID);
            List jobIDList = new ArrayList<String>();
            jobIDList.add(jobID);
            JobService.DescribeJobsInput describeJobsInput = new JobService.DescribeJobsInput();
            describeJobsInput.setJobs(jobIDList);
            JobService jobService = new JobService(context);
            while ("pending".equals(status)||"working".equals(status)){
                //发送请求
                describeJobsOutput = jobService.describeJobs(describeJobsInput);
                logger.debug("发送请求中;结果为："+JSONObject.toJSONString(describeJobsOutput));
                if (describeJobsOutput.getRetCode()!=0) {
                    logger.info("任务id查询创建进度 :  失败 ");
                    instanceCreateResponse.setTaskStatus("0");
                    instanceCreateResponse.setErrorCode(1101);
                    instanceCreateResponse.setErrorMsg("查询任务执行状态；错误信息为："+describeJobsOutput.getMessage());
                    return instanceCreateResponse;
                }
                Types.JobModel jobModel = describeJobsOutput.getJobSet().get(0);
                //状态有   pending：等待被执行      working：执行中  failed：执行失败     successful：执行成功
                status = jobModel.getStatus();
                logger.info("任务{}查询创建进度 :  成功 ！状态为：{}", jobID, status);
                if ("pending".equals(status)||"working".equals(status)){
                    Thread.sleep(5000);
                }
            }
            if ("failed".equals(status)){
                instanceCreateResponse.setTaskStatus("0");
                instanceCreateResponse.setErrorCode(1101);
                instanceCreateResponse.setErrorMsg("任务创建实例失败");
                logger.info("查询任务执行中任务创建实例失败；错误信息为："+describeJobsOutput.getMessage());
                return instanceCreateResponse;
            } else if ("successful".equals(status)){
                instanceCreateResponse.setTaskStatus("1");
                logger.info("实例创建成功！！");
            }
            logger.info("查询服务接口url地址列表：参数为"+createConf.getVersion());
            //服务接口url地址列表
            SimpleClusterService clusterService = new SimpleClusterService(context);
            SimpleClusterService.DescribeClustersInput describeClustersInput = new SimpleClusterService.DescribeClustersInput();
            List clustersList = new ArrayList<String>();
            clustersList.add(deployAppVersionOutput.getClusterID());
            describeClustersInput.setClusters(clustersList);
            describeClustersInput.setVerbose(1);
            describeClustersInput.setStatus("active");
            SimpleClusterService.DescribeClustersOutput describeClustersOutput = clusterService.describeClusters(describeClustersInput);
            logger.debug("查询服务接口url地址列表；返回结果为："+JSONObject.toJSONString(describeClustersOutput));
            if (describeClustersOutput.getRetCode()!=0) {
                logger.info("获取服务接口url地址列表失败");
                instanceCreateResponse.setServiceAPIUrls("获取服务接口url地址列表失败");
            }
            List<Types.SimpleClusterModel> clusterSet = describeClustersOutput.getClusterSet();
            if (clusterSet.size()>0) {
                if (serviceType.equals("01006")) {
                    List<JSONObject> list = new ArrayList<>();
                    for (Types.SimpleClusterNodeModel node: clusterSet.get(0).getNodes()) {
                        JSONObject object = new JSONObject();
                        object.put("ip", node.getPrivateIp());
                        object.put("hostname", node.getNodeId());
                        object.put("port", "9200");
                        list.add(object);
                    }
                    instanceCreateResponse.setServiceAPIUrls(list.toString());
                    logger.info("服务接口url地址列表" + list.toString());
                } else if (serviceType.equals("01005") || serviceType.equals("02004")) {
                    List<JSONObject> list = new ArrayList<>();
                    for (Types.SimpleClusterNodeModel node: clusterSet.get(0).getNodes()) {
                        JSONObject object = new JSONObject();
                        object.put("ip", node.getPrivateIp());
                        object.put("hostname", node.getNodeId());
                        object.put("port", "7474");
                        list.add(object);
                        instanceCreateResponse.setServiceManageUrls("http://" + node.getPrivateIp() + ":7474");
                    }
                    instanceCreateResponse.setServiceAPIUrls(list.toString());
                    logger.info("服务接口url地址列表" + list.toString());
                } else if (serviceType.equals("01002")) {
                    List<JSONObject> apiList = new ArrayList<>();
                    List<String> manageList = new ArrayList<>();
                    for (Types.SimpleClusterNodeModel node: clusterSet.get(0).getNodes()) {
                        if (node.getRole().equals("hbase-master")) {
                            JSONObject object = new JSONObject();
                            object.put("ip", node.getPrivateIp());
                            object.put("hostname", node.getNodeId());
                            object.put("port", "8000");
                            apiList.add(object);
                            manageList.add("http://" + node.getPrivateIp() + ":16010");
                        } else if (node.getRole().equals("hbase-hdfs-master")) {
                            JSONObject object = new JSONObject();
                            object.put("ip", node.getPrivateIp());
                            object.put("hostname", node.getNodeId());
                            object.put("port", "9000");
                            apiList.add(object);
                            manageList.add("http://" + node.getPrivateIp() + ":50070");
                        }
                    }
                    instanceCreateResponse.setServiceAPIUrls(apiList.toString());
                    instanceCreateResponse.setServiceManageUrls(manageList.toString());
                } else if (serviceType.equals("01001") ||serviceType.equals("02001") ||serviceType.equals("02003")) {
                    List<JSONObject> apiList = new ArrayList<>();
                    List<String> manageList = new ArrayList<>();
                    for (Types.SimpleClusterNodeModel node: clusterSet.get(0).getNodes()) {
                        if (node.getRole().equals("hdfs-master")) {
                            JSONObject object = new JSONObject();
                            object.put("ip", node.getPrivateIp());
                            object.put("hostname", node.getNodeId());
                            object.put("port", "9000");
                            apiList.add(object);
                            manageList.add("http://" + node.getPrivateIp() + ":50070");
                        } else if (node.getRole().equals("yarn-master")) {
                            JSONObject object = new JSONObject();
                            object.put("ip", node.getPrivateIp());
                            object.put("hostname", node.getNodeId());
                            object.put("port", "8082");
                            apiList.add(object);
                            manageList.add("http://" + node.getPrivateIp() + ":8088");
                        }
                    }
                    instanceCreateResponse.setServiceAPIUrls(apiList.toString());
                    instanceCreateResponse.setServiceManageUrls(manageList.toString());
                } else {
                    Map endpoints = clusterSet.get(0).getEndpoints();
                    instanceCreateResponse.setServiceAPIUrls(JSONObject.toJSONString(endpoints));
                    logger.info("服务接口url地址列表"+endpoints.toString());
                }
            }else {
                logger.info("获取服务接口url地址列表失败");
                instanceCreateResponse.setServiceAPIUrls("获取服务接口url地址列表失败");
            }
        } catch (Exception e) {
            logger.error("程序错误: "+e.getMessage());
            instanceCreateResponse.setTaskStatus("0");
            instanceCreateResponse.setReceiveStatus(0);
            instanceCreateResponse.setParamsCheckResult(0);
            instanceCreateResponse.setErrorCode(BusinessErrorCode.ERROR.getValue());
            instanceCreateResponse.setErrorMsg(BusinessErrorCode.ERROR.getDesc());
        }
        return instanceCreateResponse;
    }

    /**
     * 判断内存限制 02002 ，01006 内存只能为  [2048, 4096, 6144, 8192]
     * @return boolean true 为不在范围内，false为在范围
     */
    private boolean checkMemory(String serviceType, String compute,int memory) {
        try {
            if ("02002".equals(serviceType) || "01006".equals(serviceType)) {
                if (compute != null && !"".equals(compute)) {
                    String cpu_memory = ParaConstant.CPUMEMOERYMAP.get(compute);
                    memory = Integer.parseInt(cpu_memory.split("-")[1]);
                }
                switch (memory) {
                    case 2:
                    case 4:
                    case 6:
                    case 8:
                        return false;
                    default:
                        return true;
                }
            }
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    private boolean checkCpu(String serviceType, String compute,int cpu) {
        try {
            if ("01004".equals(serviceType)) {
                if (compute != null && !"".equals(compute)) {
                    String cpu_memory = ParaConstant.CPUMEMOERYMAP.get(compute);
                    cpu = Integer.parseInt(cpu_memory.split("-")[0]);
                }
                switch (cpu) {
                    case 2:
                        return false;
                    default:
                        return true;
                }
            }
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    /**
     * 根据serviceType 获取不同的conf信息
     * @param serviceType
     * @param serviceName
     * @param compute
     * @param cpuCores
     * @param memory
     * @param storage
     * @param nodes
     * @return
     */
    private CreateConf getConfType(String serviceType, String serviceName, String compute, int cpuCores, int memory, int storage, int nodes) throws CloneNotSupportedException {
        //设置复用信息
        MachineConf basiceConf = new MachineConf();
        if (compute != null && !"".equals(compute)){
            String cpu_memory = ParaConstant.CPUMEMOERYMAP.get(compute);
            if (cpu_memory==null&&cpuCores==0&&memory==0){
                return null;
            }
            String[] split = cpu_memory.split("-");
            basiceConf.setCpu(Integer.parseInt(split[0]));
            basiceConf.setMemory(Integer.parseInt(split[1])*1024);
        }else {
            if (cpuCores!=0&&memory!=0){
                basiceConf.setCpu(cpuCores);
                basiceConf.setMemory(memory*1024);
            }
        }
        basiceConf.setVolume_size(storage);
        basiceConf.setCount(nodes);

        //设置 cluster 的基础信息
        Map<String, Object> cluster = new HashMap<>();
        cluster.put("name",serviceName);
        cluster.put("description","");
        String vxnet = PropertiesUtils.getPropertiesValue(PropertiesUtils.CONFIG, "vxnet");
        String sgId = PropertiesUtils.getPropertiesValue(PropertiesUtils.CONFIG, "security_group");
        int INSTANCE_CLASS = Integer.parseInt(
                PropertiesUtils.getPropertiesValue(PropertiesUtils.CONFIG, "instance_class"));
        int VOLUME_CLASS = Integer.parseInt(
                PropertiesUtils.getPropertiesValue(PropertiesUtils.CONFIG, "volume_class"));
        String zone = PropertiesUtils.getPropertiesValue(PropertiesUtils.CONFIG, "zone");
        int masterInstanceClass = INSTANCE_CLASS;
        int masterVolumeClass = VOLUME_CLASS;

        cluster.put("vxnet",vxnet);
        cluster.put("security_group_id",sgId);

        //判断类型
        switch (serviceType){
            //分布式文件存储(01001) ,离线计算(02001) 内存计算(02003)
            case "01001":
            case "02001":
            case "02003":
                CreateConf conf001 = CreateConfParam.getConf001();
                cluster.put("global_uuid","07192219899460251");
                MachineConf hdfsMaster = new MachineConf(basiceConf.getCpu(),basiceConf.getMemory(),1, masterInstanceClass, masterVolumeClass,basiceConf.getVolume_size());
                cluster.put("hdfs-master",hdfsMaster);
                MachineConf yarnMaster = new MachineConf(basiceConf.getCpu(),basiceConf.getMemory(),1, masterInstanceClass, masterVolumeClass,basiceConf.getVolume_size());
                cluster.put("yarn-master",yarnMaster);
                MachineConf slave = basiceConf.clone();
                slave.setInstance_class(INSTANCE_CLASS);
                slave.setVolume_class(VOLUME_CLASS);
                slave.setCount(basiceConf.getCount() - 2);
                cluster.put("slave",slave);
                MachineConf bigdataClient = new MachineConf(basiceConf.getCpu(),basiceConf.getMemory(),0, 101, 100,basiceConf.getVolume_size());
                cluster.put("bigdata-client",bigdataClient);
                conf001.setCluster(cluster);
                conf001.setZone(zone);
                return conf001;
            // 分布式列式存储
            case "01002":
                String zkService01002 = PropertiesUtils.getPropertiesValue(PropertiesUtils.CONFIG, "zk_service01002");
                CreateConf conf01002 = CreateConfParam.getConf01002();
                cluster.put("global_uuid","77192219899460699");
                cluster.put("zk_service",zkService01002);
                MachineConf hbaseMaster =new MachineConf(basiceConf.getCpu(),basiceConf.getMemory(),1, masterInstanceClass, masterVolumeClass,basiceConf.getVolume_size());
                cluster.put("hbase-master",hbaseMaster);
                MachineConf hbaseClient = new MachineConf(basiceConf.getCpu(),basiceConf.getMemory(),0, masterInstanceClass, masterVolumeClass,basiceConf.getVolume_size());
                cluster.put("hbase-client",hbaseClient);
                MachineConf hbaseHdfsMaster = new MachineConf(basiceConf.getCpu(),basiceConf.getMemory(),1, masterInstanceClass, masterVolumeClass,basiceConf.getVolume_size());
                cluster.put("hbase-hdfs-master",hbaseHdfsMaster);
                MachineConf hbaseSlave =basiceConf.clone();
                hbaseSlave.setVolume_class(VOLUME_CLASS);
                hbaseSlave.setInstance_class(INSTANCE_CLASS);
                hbaseSlave.setCount(basiceConf.getCount() - 2);
                cluster.put("hbase-slave",hbaseSlave);
                conf01002.setCluster(cluster);
                conf01002.setZone(zone);
                return conf01002;
            // 关系型数据库
            case "01003":
                CreateConf conf01003 = CreateConfParam.getConf01003();
                cluster.put("global_uuid","17192219899460880");
                cluster.put("auto_backup_time","-1");
                MachineConf nodes1 = basiceConf.clone();
                nodes1.setInstance_class(INSTANCE_CLASS);
                cluster.put("nodes",nodes1);
                conf01003.setCluster(cluster);
                conf01003.setZone(zone);
                return conf01003;
            // 分布式内存数据库
            case "01004":
                CreateConf conf01004 = CreateConfParam.getConf01004();
                cluster.put("global_uuid","06192219899460308");
                cluster.put("auto_backup_time","-1");
                MachineConf node = basiceConf.clone();
                node.setCpu(2);
                node.setInstance_class(INSTANCE_CLASS);
                cluster.put("node",node);
                conf01004.setCluster(cluster);
                conf01004.setZone(zone);
                return conf01004;

            // 图数据库
            case "02004":
            case "01005":
                CreateConf conf01005 = CreateConfParam.getConf01005();
                cluster.put("global_uuid","17192219899460880");
                cluster.put("auto_backup_time","-1");
                MachineConf neo4jNodes = basiceConf.clone();
                neo4jNodes.setInstance_class(INSTANCE_CLASS);
                neo4jNodes.setVolume_class(VOLUME_CLASS);
                cluster.put("nodes",neo4jNodes);
                conf01005.setCluster(cluster);
                conf01005.setZone(zone);
                return conf01005;
            // 关系型数据库
            case "01006":
                CreateConf conf01006 = CreateConfParam.getConf01006();
                cluster.put("global_uuid","96192219899460160");
                MachineConf esNode = basiceConf.clone();
                esNode.setInstance_class(INSTANCE_CLASS);
                esNode.setVolume_class(VOLUME_CLASS);
                cluster.put("es_node",esNode);
                MachineConf esMasterNode = new MachineConf(2,4096,0,masterInstanceClass, masterVolumeClass,90);
                cluster.put("es_master_node",esMasterNode);
                MachineConf esNode2 = new MachineConf(2,4096,0,masterInstanceClass, masterVolumeClass,90);
                cluster.put("es_node_2",esNode2);
                MachineConf esNode3 = new MachineConf(2,4096,0,masterInstanceClass, masterVolumeClass,300);
                cluster.put("es_node_3",esNode3);
                MachineConf lstNode = new MachineConf(2,4096,0,masterInstanceClass, masterVolumeClass,30);
                cluster.put("lst_node",lstNode);
                MachineConf kbnNode = new MachineConf(2,4096,0,0,0,0);
                cluster.put("kbn_node",kbnNode);
                conf01006.setCluster(cluster);
                conf01006.setZone(zone);
                return conf01006;
            // 流式计算
            case "02002":
                String zkService02002 = PropertiesUtils.getPropertiesValue(PropertiesUtils.CONFIG, "zk_service02002");
                CreateConf conf02002 = CreateConfParam.getConf02002();
                cluster.put("global_uuid","66192219899461237");
                cluster.put("zk_service",zkService02002);
                MachineConf master = new MachineConf(basiceConf.getCpu(),basiceConf.getMemory(),1,masterInstanceClass, masterVolumeClass,basiceConf.getVolume_size());
                cluster.put("master",master);
                MachineConf slave1 = basiceConf.clone();
                slave1.setInstance_class(INSTANCE_CLASS);
                slave1.setVolume_class(VOLUME_CLASS);
                slave1.setCount(basiceConf.getCount() - 1);
                cluster.put("slave",slave1);
                MachineConf rpc = new MachineConf(1,1024,0,masterInstanceClass,masterVolumeClass,10);
                cluster.put("rpc",rpc);
                MachineConf client = new MachineConf(1,1024,0,masterInstanceClass,masterVolumeClass,10);
                cluster.put("client",client);
                conf02002.setCluster(cluster);
                conf02002.setZone(zone);
                return conf02002;
            default:return null;
        }
    }

    @Override
    public InstanceDeleteResponse delete(String instanceId, String accessToken) {

        InstanceDeleteResponse instanceDeleteResponse = new InstanceDeleteResponse();
        instanceDeleteResponse.setInstanceId(instanceId);
        EnvContext context = ContextHelper.getEnvContext(accessToken);
        if (context == null) {
            instanceDeleteResponse.setTaskStatus("0");
            instanceDeleteResponse.setErrorCode(BusinessErrorCode.UNKNOWN_SERVICETYPE_ERROR.getValue());
            instanceDeleteResponse.setErrorMsg("accessToken 不合法");
            return instanceDeleteResponse;
        }

        if (accessToken.length() > 128) {
            instanceDeleteResponse.setTaskStatus("0");
            instanceDeleteResponse.setErrorCode(BusinessErrorCode.ILLEGAL_SERVICE_TYPE.getValue());
            instanceDeleteResponse.setErrorMsg("参数不合法");
            return instanceDeleteResponse;
        }

        ClusterService clusterService = new ClusterService(context);
        ClusterService.DeleteClustersInput deleteClustersInput = new ClusterService.DeleteClustersInput();
        logger.info("发起删除集群任务; id为："+instanceId);
        List instanceIdList = new ArrayList<String>();
        instanceIdList.add(instanceId);
        deleteClustersInput.setClusters(instanceIdList);
        deleteClustersInput.setForce(1);

        try {
            ClusterService.DeleteClustersOutput deleteClustersOutput = clusterService.deleteClusters(deleteClustersInput);
            logger.debug("发起删除集群任务请求返回结果为："+JSONObject.toJSONString(deleteClustersOutput));
            if (deleteClustersOutput.getRetCode() !=0){
                instanceDeleteResponse.setTaskStatus("0");
                instanceDeleteResponse.setErrorCode(deleteClustersOutput.getRetCode());
                instanceDeleteResponse.setErrorMsg("发起删除集群任务；错误信息为："+deleteClustersOutput.getMessage());
                logger.info("发起删除集群任务失败；错误信息为："+deleteClustersOutput.getMessage());
                return instanceDeleteResponse;
            }

            //查询任务执行状态
            JobService.DescribeJobsOutput describeJobsOutput =null;
            String status ="pending";
            String jobID = deleteClustersOutput.getJobIDs().get(instanceId).toString();
            logger.info("任务id查询创建进度,任务id为："+jobID);
            List jobIDList = new ArrayList<String>();
            jobIDList.add(jobID);
            JobService.DescribeJobsInput describeJobsInput = new JobService.DescribeJobsInput();
            describeJobsInput.setJobs(jobIDList);
            JobService jobService = new JobService(context);
            while ("pending".equals(status)||"working".equals(status)){
                //发送请求
                describeJobsOutput = jobService.describeJobs(describeJobsInput);
                logger.debug("发送请求中;结果为："+JSONObject.toJSONString(describeJobsOutput));
                if (describeJobsOutput.getRetCode()!=0) {
                    logger.info("任务id查询删除集群进度 :  失败 ");
                    instanceDeleteResponse.setTaskStatus("0");
                    instanceDeleteResponse.setErrorCode(1103);
                    instanceDeleteResponse.setErrorMsg("查询任务执行状态；错误信息为："+describeJobsOutput.getMessage());
                    logger.error("查询任务执行状态；错误信息为："+describeJobsOutput.getMessage());
                    return instanceDeleteResponse;
                }
                Types.JobModel jobModel = describeJobsOutput.getJobSet().get(0);
                //状态有   pending：等待被执行      working：执行中  failed：执行失败     successful：执行成功
                status = jobModel.getStatus();
                logger.info("任务id查询删除集群进度 :  成功 ！状态为："+status);
                if ("pending".equals(status)||"working".equals(status)){
                    Thread.sleep(5000);
                }
            }
            if ("failed".equals(status)){
                instanceDeleteResponse.setTaskStatus("0");
                instanceDeleteResponse.setErrorCode(1102);
                instanceDeleteResponse.setErrorMsg("查询任务执行中任务删除集群失败；错误信息为："+describeJobsOutput.getMessage());
                logger.info("查询任务执行中任务删除集群失败；错误信息为："+describeJobsOutput.getMessage());
                return instanceDeleteResponse;
            }else if ("successful".equals(status)){
                instanceDeleteResponse.setTaskStatus("1");
                logger.info("删除集群成功！！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("程序错误"+e.getMessage());
            instanceDeleteResponse.setTaskStatus("0");
            instanceDeleteResponse.setErrorCode(BusinessErrorCode.ERROR.getValue());
            instanceDeleteResponse.setErrorMsg(BusinessErrorCode.ERROR.getDesc());
        }
        return instanceDeleteResponse;
    }

    @Override
    public InstanceModifyResponse modify(String instanceId, String serviceName, String serviceManagerURLs, String serviceAPIUrls, int nodes, String accessToken) {
        InstanceModifyResponse response =new InstanceModifyResponse();
        response.setInstanceId(instanceId);
        EnvContext context = ContextHelper.getEnvContext(accessToken);
        if (context == null) {
            response.setTaskStatus("0");
            response.setErrorCode(BusinessErrorCode.UNKNOWN_SERVICETYPE_ERROR.getValue());
            response.setErrorMsg("accessToken 不合法");
            return response;
        }

        if (serviceName.length() > 1024 || accessToken.length() > 128) {
            response.setTaskStatus("0");
            response.setErrorCode(BusinessErrorCode.ILLEGAL_SERVICE_TYPE.getValue());
            response.setErrorMsg("参数不合法");
            return response;
        }

        ClusterService clusterService = new ClusterService(context);

        if(serviceName == null || "".equals(serviceName) || accessToken == null || "".equals(accessToken)) {
            logger.info("服务实例唯一标识或用户令牌 不可为空");
            response.setTaskStatus("0");
            response.setErrorCode(BusinessErrorCode.NULL_REQUIED_PARA_ERROR.getValue());
            response.setErrorMsg(BusinessErrorCode.NULL_REQUIED_PARA_ERROR.getDesc());
            return response;
        }

        SimpleClusterService simpleClusterService = new SimpleClusterService(context);

        SimpleClusterService.DescribeClustersInput describeClustersInput = new SimpleClusterService.DescribeClustersInput();
        List clustersList = new ArrayList<String>();
        clustersList.add(instanceId);
        describeClustersInput.setClusters(clustersList);
        describeClustersInput.setVerbose(1);
        describeClustersInput.setStatus("active");
        SimpleClusterService.DescribeClustersOutput output = null;
        try {
            output = simpleClusterService.describeClusters(describeClustersInput);
        } catch (QCException e) {
            logger.error("程序错误"+e.getMessage());
            response.setErrorCode(0);
            response.setTaskStatus("0");
            response.setErrorMsg("程序错误"+e.getMessage());
            return response;
        }
        /**青云接口异常情况处理 */
        if(output == null || output.getRetCode() != 0){
            logger.info("服务实例唯一标识或用户令牌 不可为空");
            response.setTaskStatus("0");
            response.setErrorCode(BusinessErrorCode.NULL_REQUIED_PARA_ERROR.getValue());
            response.setErrorMsg(BusinessErrorCode.NULL_REQUIED_PARA_ERROR.getDesc());
            return response;
        }

        /**从output中获取服务实例集合**/
        List<Types.SimpleClusterModel> clusterModelList = output.getClusterSet();

        /**异常情况处理 */
        if(clusterModelList == null || clusterModelList.size() == 0){
            logger.info("服务实例唯一标识或用户令牌 不可为空");
            response.setTaskStatus("0");
            response.setErrorCode(BusinessErrorCode.NULL_REQUIED_PARA_ERROR.getValue());
            response.setErrorMsg("实例 ID 不存在：" + instanceId);
            return response;
        }
        /**从集合中获取clusterModel**/
        Types.SimpleClusterModel clusterModel = clusterModelList.get(0);
        String serviceType;

        /** 01 获取服务类型 **/
        if(clusterModel.getAppVersion().equals("appv-v71be1fi")){
            /**异常情况处理 */
            if(clusterModel == null || clusterModel.getTags() == null  || clusterModel.getTags().size() == 0){
                logger.info("服务实例唯一标识或用户令牌 不可为空");
                response.setTaskStatus("0");
                response.setErrorCode(BusinessErrorCode.NULL_REQUIED_PARA_ERROR.getValue());
                response.setErrorMsg(BusinessErrorCode.NULL_REQUIED_PARA_ERROR.getDesc());
                return response;
            }
            serviceType = clusterModel.getTags().get(0).get("tag_name").toString();
        }else{
            /**异常情况处理 */
            if(!ParaConstant.Q2G_SERVICETYPEMAP.containsKey(clusterModel.getAppVersion())||
                    ParaConstant.Q2G_SERVICETYPEMAP.get(clusterModel.getAppVersion()) == null ){
                logger.info("服务实例唯一标识或用户令牌 不可为空");
                response.setTaskStatus("0");
                response.setErrorCode(BusinessErrorCode.NULL_REQUIED_PARA_ERROR.getValue());
                response.setErrorMsg(BusinessErrorCode.NULL_REQUIED_PARA_ERROR.getDesc());
                return response;
            }
            serviceType = ParaConstant.Q2G_SERVICETYPEMAP.get(clusterModel.getAppVersion());
        }
        logger.info("服务类型："+serviceType);

        /**异常情况处理 */
        if(!ParaConstant.SERVICETYPE_MAINROLE_MAP.containsKey(serviceType)||
                ParaConstant.SERVICETYPE_MAINROLE_MAP.get(serviceType) == null ){
            logger.info("服务实例唯一标识或用户令牌 不可为空");
            response.setTaskStatus("0");
            response.setErrorCode(BusinessErrorCode.NULL_REQUIED_PARA_ERROR.getValue());
            response.setErrorMsg(BusinessErrorCode.NULL_REQUIED_PARA_ERROR.getDesc());
            return response;
        }
        String mainRole = ParaConstant.SERVICETYPE_MAINROLE_MAP.get(serviceType);
        logger.info("主角色：" + mainRole);
        List<Types.SimpleClusterNodeModel> clusterNodeModelList= clusterModel.getNodes();
        /**异常情况处理 */
        if(clusterNodeModelList == null || clusterNodeModelList.size() == 0){
            logger.info("服务实例唯一标识或用户令牌 不可为空");
            response.setTaskStatus("0");
            response.setErrorCode(BusinessErrorCode.NULL_REQUIED_PARA_ERROR.getValue());
            response.setErrorMsg(BusinessErrorCode.NULL_REQUIED_PARA_ERROR.getDesc());
            return response;
        }
//        int originRoleCount = 0;
        int originCount = 0;
        for(Types.SimpleClusterNodeModel clusterNodeModel : clusterNodeModelList){
            String role = clusterNodeModel.getRole();
            /**获取主角色的CPU、内存、磁盘规格，并计算出计算规格*/
//            if(role.equals(mainRole)) {
//                originRoleCount = originRoleCount + 1;
//            }
            originCount = originCount + 1;
        }
       //添加节点
        if(nodes > originCount){
            try {
                ClusterService.AddClusterNodesInput addClusterNodesInput = new ClusterService.AddClusterNodesInput();
                addClusterNodesInput.setCluster(instanceId);
                addClusterNodesInput.setNodeCount(nodes-originCount);
                addClusterNodesInput.setNodeRole(mainRole);
                ClusterService.AddClusterNodesOutput addOutput = clusterService.addClusterNodes(addClusterNodesInput);
                if (addOutput.getMessage()!=null){
                    logger.debug("服务实例添加节点发步失败！！！");
                    response.setErrorCode(addOutput.getRetCode());
                    response.setErrorMsg(addOutput.getMessage());
                    response.setTaskStatus("0");
                    return response;
                }
                //查询任务执行状态
                JobService.DescribeJobsOutput describeJobsOutput =null;
                String status ="pending";
                //根据任务id查询创建进度
                String jobID = addOutput.getJobID();
                logger.info("任务id查询创建节点进度,任务id为："+jobID);
                List jobIDList = new ArrayList<String>();
                jobIDList.add(jobID);
                JobService.DescribeJobsInput describeJobsInput = new JobService.DescribeJobsInput();
                describeJobsInput.setJobs(jobIDList);
                JobService jobService = new JobService(context);
                while ("pending".equals(status)||"working".equals(status)){
                    //发送请求
                    describeJobsOutput = jobService.describeJobs(describeJobsInput);
                    logger.debug("发送请求中;结果为："+JSONObject.toJSONString(describeJobsOutput));
                    if (describeJobsOutput.getRetCode()!=0) {
                        logger.info("任务id查询创建节点进度 :  失败 ");
                        response.setTaskStatus("0");
                        response.setErrorCode(1104);
                        response.setErrorMsg("创建节点任务执行状态；错误信息为："+describeJobsOutput.getMessage());
                        return response;
                    }
                    Types.JobModel jobModel = describeJobsOutput.getJobSet().get(0);
                    //状态有   pending：等待被执行      working：执行中  failed：执行失败     successful：执行成功
                    status = jobModel.getStatus();
                    logger.info("任务id查询创建节点进度 :  成功 ！状态为："+status);
                    if ("pending".equals(status)||"working".equals(status)){
                        Thread.sleep(5000);
                    }
                }
                if ("failed".equals(status)){
                    response.setTaskStatus("0");
                    response.setErrorCode(1105);
                    response.setErrorMsg("查询任务执行中任务创建节点失败；错误信息为："+describeJobsOutput.getMessage());
                    logger.info("查询任务执行中任务创建节点失败；错误信息为："+describeJobsOutput.getMessage());
                    return response;
                }else if ("successful".equals(status)){
                    response.setTaskStatus("1");
                    logger.info("节点创建成功！！");
                }
            }catch (Exception e){
                logger.error("程序错误"+e.getMessage());
                response.setErrorCode(0);
                response.setTaskStatus("0");
                response.setErrorMsg("程序错误"+e.getMessage());
                return response;
            }

        } else if (nodes == 0 || nodes == originCount){
            // do nothing
//            logger.info("服务实例集群规模 不可为空或小于0,main_role参数不能为空");
//            response.setTaskStatus(5000);
//            response.setErrorCode(BusinessErrorCode.NULL_REQUIED_PARA_ERROR.getValue());
//            response.setErrorMsg(BusinessErrorCode.NULL_REQUIED_PARA_ERROR.getDesc());
        } else {
            response.setTaskStatus("0");
            response.setErrorCode(BusinessErrorCode.NULL_REQUIED_PARA_ERROR.getValue());
            response.setErrorMsg("节点不能为负数或小于原集群规模");
            return response;
        }

        ClusterService.ModifyClusterAttributesInput modifyClusterAttributesInput = new ClusterService.ModifyClusterAttributesInput();
        modifyClusterAttributesInput.setCluster(instanceId);
        modifyClusterAttributesInput.setName(serviceName);
        try {
            ClusterService.ModifyClusterAttributesOutput attributesOutput = clusterService.modifyClusterAttributes(modifyClusterAttributesInput);
            if (attributesOutput != null && attributesOutput.getRetCode() == 0) {
                logger.debug("服务实例修改发布成功！！！");
                response.setErrorCode(output.getRetCode());
                response.setErrorMsg(output.getMessage());
                response.setTaskStatus("1");
            } else {
                logger.debug("服务实例修改发失败！！！");
                response.setErrorCode(output.getRetCode());
                response.setErrorMsg(output.getMessage());
                response.setTaskStatus("0");
                return response;
            }
        }catch (Exception e){
            logger.error("程序错误"+e.getMessage());
            response.setErrorCode(0);
            response.setTaskStatus("0");
            response.setErrorMsg("程序错误"+e.getMessage());
            return response;
        }

        return response;
    }
    /**
     *服务资源获取资源列表
     * @param serviceType      服务类型       是
     * @param accessToken      用户身份令牌      是   支持 OAuth、LDAP 等身份认证协议的TOKEN。
     * @return
     */
    @Override
    public InstanceListResponse list(String serviceType, String accessToken) {

        InstanceListResponse instanceListResponse = new InstanceListResponse();
        //获取EnvContent的参数内容
        EnvContext context = ContextHelper.getEnvContext(accessToken);
        if (context == null) {
            instanceListResponse.setTaskStatus("0");
            instanceListResponse.setErrorCode(BusinessErrorCode.UNKNOWN_SERVICETYPE_ERROR.getValue());
            instanceListResponse.setErrorMsg("accessToken 不合法");
            return instanceListResponse;
        }

        if (serviceType.length() > 1024 || accessToken.length() > 128) {
            instanceListResponse.setTaskStatus("0");
            instanceListResponse.setErrorCode(BusinessErrorCode.ILLEGAL_SERVICE_TYPE.getValue());
            instanceListResponse.setErrorMsg("参数不合法");
            return instanceListResponse;
        }

        SimpleClusterService clusterService = new SimpleClusterService(context);
        TagService tagService = new TagService(context);
        SimpleClusterService.DescribeClustersInput describeClustersInput = new SimpleClusterService.DescribeClustersInput();
        TagService.DescribeTagsInput describeTagsInput = new TagService.DescribeTagsInput();
        List serviceTypeList = new ArrayList<String>();
        List<String> clusterIdList = new ArrayList<String>();

        try {
            if(serviceType == null || accessToken == null){
                instanceListResponse.setTaskStatus("0");
                instanceListResponse.setErrorCode(BusinessErrorCode.NULL_REQUIED_PARA_ERROR.getValue());
                instanceListResponse.setErrorMsg(BusinessErrorCode.NULL_REQUIED_PARA_ERROR.getDesc());
                return instanceListResponse;
            }
            if (!this.validServceType.contains(serviceType)){
                instanceListResponse.setTaskStatus("0");
                instanceListResponse.setErrorCode(BusinessErrorCode.ILLEGAL_SERVICE_TYPE.getValue());
                instanceListResponse.setErrorMsg(BusinessErrorCode.ILLEGAL_SERVICE_TYPE.getDesc());
                return instanceListResponse;
            }

            instanceListResponse.setTaskStatus("0");
            instanceListResponse.setServiceList("");
           if(!serviceType.equals("01001") && !serviceType.equals("02001") &&  !serviceType.equals("02003") && !serviceType.equals("02004" )&& !serviceType.equals("01005") ){
               /**除了01001、02001、02003的serviceType处理 **/
               serviceTypeList.add(ParaConstant.G2Q_SERVICETYPEMAP.get(serviceType));
               describeClustersInput.setAppVersions(serviceTypeList);
               describeClustersInput.setVerbose(1);
               describeClustersInput.setStatus("active");
               SimpleClusterService.DescribeClustersOutput output = clusterService.describeClusters(describeClustersInput);
               /**青云接口异常情况处理 */
               if(output == null || output.getRetCode() != 0){
                   instanceListResponse.setTaskStatus("0");
                   instanceListResponse.setErrorCode(output.getRetCode());
                   instanceListResponse.setErrorMsg(output.getMessage());
                   return instanceListResponse;
               }


               List<Types.SimpleClusterModel> clusterModelList =output.getClusterSet();
               /**异常情况处理 */
               if(clusterModelList == null || clusterModelList.size() == 0){
                   instanceListResponse.setTaskStatus("1");
                   instanceListResponse.setErrorCode(0);
                   instanceListResponse.setErrorMsg("");
                   instanceListResponse.setServiceList(clusterIdList.toString());
                   return instanceListResponse;
               }
               logger.info("查询服务实例集合，其数量为："+output.getClusterSet().size());
               for (Types.SimpleClusterModel clusterModel : clusterModelList) {
                   clusterIdList.add(clusterModel.getClusterID());
               }
               logger.info("服务实例ID组合："+clusterIdList);

           }else {
               /**针对01001、02001、02003的serviceType处理 **/
               describeTagsInput.setSearchWord(serviceType);
               describeTagsInput.setVerbose(1);
               TagService.DescribeTagsOutput describeTagsOutput = tagService.describeTags(describeTagsInput);

               /**青云接口异常情况处理 */
               if(describeTagsOutput == null || describeTagsOutput.getRetCode() != 0){
                   instanceListResponse.setTaskStatus("0");
                   instanceListResponse.setErrorCode(describeTagsOutput.getRetCode());
                   instanceListResponse.setErrorMsg(describeTagsOutput.getMessage());
                   return instanceListResponse;
               }

               /**异常情况处理 */
               if(describeTagsOutput.getTagSet() == null ||
                       describeTagsOutput.getTagSet().size() == 0 ||
                       describeTagsOutput.getTagSet().get(0).getResourceTagPairs()== null||
                       describeTagsOutput.getTagSet().get(0).getResourceTagPairs().size() == 0){
                   instanceListResponse.setTaskStatus("1");
                   instanceListResponse.setErrorCode(0);
                   instanceListResponse.setErrorMsg("");
                   instanceListResponse.setServiceList(clusterIdList.toString());
                   return instanceListResponse;
               }
               logger.info("查询服务实例集合，其数量为："+describeTagsOutput.getTagSet().get(0).getResourceTagPairs().size());
               for (Types.ResourceTagPairModel resourceTagPair : describeTagsOutput.getTagSet().get(0).getResourceTagPairs()) {
                   clusterIdList.add(resourceTagPair.getResourceID());
               }
               logger.info("服务实例ID组合："+clusterIdList);
           }

            instanceListResponse.setTaskStatus("1");
            instanceListResponse.setErrorCode(0);
            instanceListResponse.setErrorMsg("");
            instanceListResponse.setServiceList(clusterIdList.toString());

        } catch (Exception e) {
            logger.error("程序错误"+e.getMessage());
            instanceListResponse.setTaskStatus("0");
            instanceListResponse.setErrorCode(5000);
            instanceListResponse.setErrorMsg("程序错误");
        }

        return instanceListResponse;

    }

    /**
     *服务实例查询
     * @param instanceId      服务资源Id       是
     * @param accessToken      用户身份令牌      是   支持 OAuth、LDAP 等身份认证协议的TOKEN。
     * @return
     */
    @Override
    public InstanceQueryResponse query(String instanceId, String accessToken) {
        String serviceType = "";  //服务类型
        String mainRole = "";     //主角色
        String healthStatus = ""; //健康状态
        String compute = "";      //计算规格
        String cpuCores = "";     //CPU
        String memory = "";       //内存
        String storage = "";      //磁盘规格
        Integer nodes = 0;        //集群规模
        String serviceManageUrls = "";  //服务管理url地址
        String serviceAPIUrls = "";     //服务接口url地址列表
        List<String> clusters = new ArrayList<String>();
        List<Map> serviceAPIUrlsMapList = new ArrayList<Map>();

        InstanceQueryResponse instanceQueryResponse = new InstanceQueryResponse();

        //获取EnvContent的参数内容
        EnvContext context = ContextHelper.getEnvContext(accessToken);
        if (context == null) {
            instanceQueryResponse.setTaskStatus("0");
            instanceQueryResponse.setErrorCode(BusinessErrorCode.UNKNOWN_SERVICETYPE_ERROR.getValue());
            instanceQueryResponse.setErrorMsg("accessToken 不合法");
            return instanceQueryResponse;
        }

        if (accessToken.length() > 128) {
            instanceQueryResponse.setTaskStatus("0");
            instanceQueryResponse.setErrorCode(BusinessErrorCode.ILLEGAL_SERVICE_TYPE.getValue());
            instanceQueryResponse.setErrorMsg("参数不合法");
            return instanceQueryResponse;
        }

        SimpleClusterService clusterService  = new SimpleClusterService(context);
        SimpleClusterService.DescribeClustersInput describeClustersInput = new SimpleClusterService.DescribeClustersInput();

        clusters.add(instanceId);
        describeClustersInput.setClusters(clusters);
        describeClustersInput.setVerbose(1);
        describeClustersInput.setStatus("active");
        try{
            if(instanceId == null || accessToken == null){
                instanceQueryResponse.setTaskStatus("0");
                instanceQueryResponse.setErrorCode(BusinessErrorCode.NULL_REQUIED_PARA_ERROR.getValue());
                instanceQueryResponse.setErrorMsg(BusinessErrorCode.NULL_REQUIED_PARA_ERROR.getDesc());
                return instanceQueryResponse;
            }

            SimpleClusterService.DescribeClustersOutput output =  clusterService.describeClusters(describeClustersInput);
            /**青云接口异常情况处理 */
            if(output == null || output.getRetCode() != 0){
                instanceQueryResponse.setTaskStatus("0");
                instanceQueryResponse.setErrorCode(output.getRetCode());
                instanceQueryResponse.setErrorMsg(output.getMessage());
                return instanceQueryResponse;
            }

            /**从output中获取服务实例集合**/
            List<Types.SimpleClusterModel> clusterModelList = output.getClusterSet();

            /**异常情况处理 */
            if(clusterModelList == null || clusterModelList.size() == 0){
                instanceQueryResponse.setTaskStatus("0");
                instanceQueryResponse.setErrorCode(BusinessErrorCode.NULL_CLUSTER_MODEL_ERROR.getValue());
                instanceQueryResponse.setErrorMsg("实例 ID 不存在：" + instanceId);
                return instanceQueryResponse;
            }
            /**从集合中获取clusterModel**/
            Types.SimpleClusterModel clusterModel = clusterModelList.get(0);

            /** 01 获取服务类型 **/
            if(clusterModel.getAppVersion().equals("appv-v71be1fi")){
                /**异常情况处理 */
                if(clusterModel == null || clusterModel.getTags() == null  || clusterModel.getTags().size() == 0){
                    instanceQueryResponse.setTaskStatus("0");
                    instanceQueryResponse.setErrorCode(BusinessErrorCode.NULL_CLUSTER_TAGS_ERROR.getValue());
                    instanceQueryResponse.setErrorMsg(BusinessErrorCode.NULL_CLUSTER_TAGS_ERROR.getDesc());
                    return instanceQueryResponse;
                }
                 serviceType = clusterModel.getTags().get(0).get("tag_name").toString();
            }else{
                /**异常情况处理 */
                if(!ParaConstant.Q2G_SERVICETYPEMAP.containsKey(clusterModel.getAppVersion())||
                   ParaConstant.Q2G_SERVICETYPEMAP.get(clusterModel.getAppVersion()) == null ){
                    instanceQueryResponse.setTaskStatus("0");
                    instanceQueryResponse.setErrorCode(BusinessErrorCode.UNKNOWN_SERVICETYPE_ERROR.getValue());
                    instanceQueryResponse.setErrorMsg(BusinessErrorCode.UNKNOWN_SERVICETYPE_ERROR.getDesc());
                    return instanceQueryResponse;
                }
                 serviceType = ParaConstant.Q2G_SERVICETYPEMAP.get(clusterModel.getAppVersion());
            }
            logger.info("服务类型："+serviceType);

            /**异常情况处理 */
            if(!ParaConstant.SERVICETYPE_MAINROLE_MAP.containsKey(serviceType)||
                    ParaConstant.SERVICETYPE_MAINROLE_MAP.get(serviceType) == null ){
                instanceQueryResponse.setTaskStatus("0");
                instanceQueryResponse.setErrorCode(BusinessErrorCode.NULL_MAINROLE_ERROR.getValue());
                instanceQueryResponse.setErrorMsg(BusinessErrorCode.NULL_MAINROLE_ERROR.getDesc());
                return instanceQueryResponse;
            }
            mainRole = ParaConstant.SERVICETYPE_MAINROLE_MAP.get(serviceType);
            logger.info("主角色："+mainRole);

            /** 02 获取CPU规格、内存规格、磁盘规格、计算规格 **/
            List<Types.SimpleClusterNodeModel> clusterNodeModelList= clusterModel.getNodes();
            /**异常情况处理 */
            if(clusterNodeModelList == null  || clusterNodeModelList.size() == 0) {
                instanceQueryResponse.setTaskStatus("0");
                instanceQueryResponse.setErrorCode(BusinessErrorCode.NULL_CLUSTER_NODES_ERROR.getValue());
                instanceQueryResponse.setErrorMsg(BusinessErrorCode.NULL_CLUSTER_NODES_ERROR.getDesc());
                return instanceQueryResponse;
            }

            int total_size = clusterNodeModelList.size();
            int unhealthy_size = 0;
            double unhealty_percent =  0.0;
            boolean isExistMainRole = false;
            for(Types.SimpleClusterNodeModel clusterNodeModel : clusterNodeModelList){
                if(!isExistMainRole){
                    String role = clusterNodeModel.getRole();
                    /**获取主角色的CPU、内存、磁盘规格，并计算出计算规格*/
                    if(role.equals(mainRole)){
                        /**02-1 获取CPU规格**/
                        cpuCores = String.valueOf(clusterNodeModel.getCPU());
                        logger.info("CPU规格："+cpuCores);
                        /**02-3 获取内存规格**/
                        memory = String.valueOf(clusterNodeModel.getMemory()/1024);
                        logger.info("内存规格："+memory+"G");
                        /**02-4 获取磁盘规格**/
                        storage = String.valueOf(clusterNodeModel.getStorageSize());
                        logger.info("磁盘规格："+storage+"G");
                        /**02-5 根据映射表获取计算规格*/
                        if(ParaConstant.CALSPECIFYMAP.get(cpuCores+"-"+memory) == null){
                            logger.info("未找到对应的计算规格");
                        }else{
                            compute =  ParaConstant.CALSPECIFYMAP.get(cpuCores+"-"+memory);
                            logger.info("计算规格："+compute);
                        }
                        isExistMainRole = true;
                    }
                }
                if(clusterNodeModel.getHealthStatus().equals("unhealthy")){
                    unhealthy_size ++;
                }
            }

            /**异常情况处理 */
            if(!isExistMainRole){
                instanceQueryResponse.setTaskStatus("0");
                instanceQueryResponse.setErrorCode(BusinessErrorCode.NOTEXIST_CLUSTER_MAINROLE_ERROR.getValue());
                instanceQueryResponse.setErrorMsg(BusinessErrorCode.NOTEXIST_CLUSTER_MAINROLE_ERROR.getDesc());
                return  instanceQueryResponse;
            }

              //03 获取健康状态
            unhealty_percent = unhealthy_size/total_size;
            logger.info("健康节点数量占比："+unhealty_percent);
            if(unhealty_percent == 0){
                healthStatus = "0";
            }else if (unhealty_percent <= 0.5){
                healthStatus = "1" ;
            }else{
                healthStatus = "2" ;
            }
            logger.info("健康状态为："+healthStatus);


            /** 04 获取集群规模 **/
            //异常处理
            if(clusterModel.getRoleCount() == null){
                instanceQueryResponse.setTaskStatus("0");
                instanceQueryResponse.setErrorCode(BusinessErrorCode.NULL_CLUSTER_SIZE.getValue());
                instanceQueryResponse.setErrorMsg(BusinessErrorCode.NULL_CLUSTER_SIZE.getDesc());
                return instanceQueryResponse;
            }
            Map roleCount = clusterModel.getRoleCount();

            for (Object role: roleCount.keySet()) {
                nodes += Integer.valueOf(roleCount.get(role).toString());
            }
//            nodes = Integer.valueOf(roleCount.get(mainRole).toString());
            logger.info("集群规模为："+nodes);

            /** 05 获取服务管理地址**/
            logger.info("服务管理地址暂时置空");

            /** 06 获取服务接口url地址列表**/
            if (serviceType.equals("01006")) {
                List<JSONObject> list = new ArrayList<>();
                for (Types.SimpleClusterNodeModel node: clusterModel.getNodes()) {
                    JSONObject object = new JSONObject();
                    object.put("ip", node.getPrivateIp());
                    object.put("hostname", node.getNodeId());
                    object.put("port", "9200");
                    list.add(object);
                }
                serviceAPIUrls = list.toString();
            } else if (serviceType.equals("01005") || serviceType.equals("02004")) {
                List<JSONObject> list = new ArrayList<>();
                for (Types.SimpleClusterNodeModel node: clusterModel.getNodes()) {
                    JSONObject object = new JSONObject();
                    object.put("ip", node.getPrivateIp());
                    object.put("hostname", node.getNodeId());
                    object.put("port", "7474");
                    list.add(object);
                    serviceManageUrls = "http://" + node.getPrivateIp() + ":7474";
                }
                serviceAPIUrls = list.toString();
            } else if (serviceType.equals("01002")) {
                List<JSONObject> apiList = new ArrayList<>();
                List<String> manageList = new ArrayList<>();
                for (Types.SimpleClusterNodeModel node: clusterModel.getNodes()) {
                    if (node.getRole().equals("hbase-master")) {
                        JSONObject object = new JSONObject();
                        object.put("ip", node.getPrivateIp());
                        object.put("hostname", node.getNodeId());
                        object.put("port", "8000");
                        apiList.add(object);
                        manageList.add("http://" + node.getPrivateIp() + ":16010");
                    } else if (node.getRole().equals("hbase-hdfs-master")) {
                        JSONObject object = new JSONObject();
                        object.put("ip", node.getPrivateIp());
                        object.put("hostname", node.getNodeId());
                        object.put("port", "9000");
                        apiList.add(object);
                        manageList.add("http://" + node.getPrivateIp() + ":50070");
                    }
                }
                serviceAPIUrls = apiList.toString();
                serviceManageUrls = manageList.toString();
            } else if (serviceType.equals("01001") ||serviceType.equals("02001") ||serviceType.equals("02003")) {
                List<JSONObject> apiList = new ArrayList<>();
                List<String> manageList = new ArrayList<>();
                for (Types.SimpleClusterNodeModel node: clusterModel.getNodes()) {
                    if (node.getRole().equals("hdfs-master")) {
                        JSONObject object = new JSONObject();
                        object.put("ip", node.getPrivateIp());
                        object.put("hostname", node.getNodeId());
                        object.put("port", "9000");
                        apiList.add(object);
                        manageList.add("http://" + node.getPrivateIp() + ":50070");
                    } else if (node.getRole().equals("yarn-master")) {
                        JSONObject object = new JSONObject();
                        object.put("ip", node.getPrivateIp());
                        object.put("hostname", node.getNodeId());
                        object.put("port", "8082");
                        apiList.add(object);
                        manageList.add("http://" + node.getPrivateIp() + ":8088");
                    }
                }
                serviceAPIUrls = apiList.toString();
                serviceManageUrls = manageList.toString();
            } else {
                Map serviceAPIUrlsMap = clusterModel.getEndpoints();
                JSONArray jsonArray = new JSONArray();
                JSONObject parentObj = new JSONObject();
                for (Object key : serviceAPIUrlsMap.keySet()) {
                    JSONObject childObj = new JSONObject();
                    Map<String, String> childMap = (Map<String, String>) serviceAPIUrlsMap.get(key);//
                    for (String childkey : childMap.keySet()) {

                        Object childval = childMap.get(childkey);
                        childObj.put(childkey, childval);
                    }
                    parentObj.put(key.toString(), childObj);

                }
                jsonArray.add(parentObj);

                serviceAPIUrls = jsonArray.toString();
            }
            logger.info("服务接口URL地址列表："+serviceAPIUrls);

            instanceQueryResponse.setTaskStatus("1");
            instanceQueryResponse.setServiceType(serviceType);
            instanceQueryResponse.setHealthStatus(healthStatus);
            instanceQueryResponse.setCompute(compute);
            instanceQueryResponse.setCpuCores(cpuCores);
            instanceQueryResponse.setMemory(memory);
            instanceQueryResponse.setStorage(storage);
            instanceQueryResponse.setNodes(nodes);
            instanceQueryResponse.setServiceManageUrls(serviceManageUrls);
            instanceQueryResponse.setServiceAPIUrls(serviceAPIUrls);
            instanceQueryResponse.setErrorCode(0);
            instanceQueryResponse.setErrorMsg("");
            logger.info("完成InstanceQueryResponse赋值");

        }catch (Exception e){
            //e.printStackTrace();
            logger.error("程序错误"+e.getMessage());
            instanceQueryResponse.setTaskStatus("0");
            instanceQueryResponse.setErrorCode(5000);
            instanceQueryResponse.setErrorMsg("程序错误");

        }
        return instanceQueryResponse;
    }
}
