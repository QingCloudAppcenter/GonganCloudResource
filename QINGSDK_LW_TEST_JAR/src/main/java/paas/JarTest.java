package paas;

import paas.common.utils.ContextHelper;
import paas.service.instance.*;
import paas.service.resource.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 *  *@description:
 *  *@title:
 *  *@author: zzr
 *  *@date: 
 *  
 */
public class JarTest {


    public static void main(String[] args) {
        JarTest jarTest = new JarTest();
        InstanceImpl instance= new InstanceImpl();
        ResourceImpl resourceTest = new ResourceImpl();
        String method = args[0];
        System.out.println("-----调用类为："+method);
        System.out.println("-----调用方法为："+args[1]);
        Properties properties = new Properties();
        // 使用InPutStream流读取properties文件
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(jarTest.getPath()+"/config.properties"));
            properties.load(bufferedReader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String constAccessToken = ContextHelper.constAccessToken;
        // 实例
        if(method.equals("instance")){
            System.out.println("---------  实例  start   ------------");
            // createInstance 创建
            if(args[1].equals("create")){
                System.out.println("------进入 createInstance 创建实例方法------");
                System.out.println("请求参数 ："+"createServiceType:"+
                        properties.getProperty("createServiceType")+
                        ",createServiceName:"+ properties.getProperty("createServiceName")+
                        ",createCompute:"+properties.getProperty("createCompute")+
                        ",createCpuCores:"+Integer.valueOf(properties.getProperty("createCpuCores"))+
                        ",createMemory:"+ properties.getProperty("createMemory")+
                        ",createStorage:"+ properties.getProperty("createStorage")+
                        ",createNodes:"+  properties.getProperty("createNodes"));
                InstanceCreateResponse response = instance.create(
                        properties.getProperty("createServiceType"),
                        properties.getProperty("createServiceName"),
                        properties.getProperty("createCompute"),
                        Integer.valueOf(properties.getProperty("createCpuCores")),
                        Integer.valueOf(properties.getProperty("createMemory")),
                        Integer.valueOf(properties.getProperty("createStorage")),
                        Integer.valueOf(properties.getProperty("createNodes")),
                        constAccessToken);
                System.out.println("------------------------");
                System.out.println("response.taskStatus=" + response.getTaskStatus());
                System.out.println("response.errorMsg="+ response.getErrorMsg());
                System.out.println("response.errorCode=" + response.getErrorCode());
                System.out.println("response.ServiceManageUrls=" + response.getServiceManageUrls());
                System.out.println("response.ServiceAPIUrls=" + response.getServiceAPIUrls());
                System.out.println("response.InstanceId=" + response.getInstanceId());
                System.out.println("------------------------");
            }
            // 删除
            else if(args[1].equals("delete")){
                System.out.println("------进入 deleteInstance删除实例方法------");
                System.out.println("请求参数 ："+"deleteInstanceId:"+ properties.getProperty("deleteInstanceId"));
                InstanceDeleteResponse delete = instance.delete(properties.getProperty("deleteInstanceId"),constAccessToken);
                System.out.println("------------------------");
                System.out.println("response.taskStatus=" + delete.getTaskStatus());
                System.out.println("response.errorMsg="+ delete.getErrorMsg());
                System.out.println("response.errorCode=" + delete.getErrorCode());
                System.out.println("------------------------");
            }
            // 查询 list
            else if(args[1].equals("list")){
                System.out.println("------进入 listInstance 查询实例方法------");
                System.out.println("请求参数 ："+"serviceType:"+ properties.getProperty("instanceServiceType"));
                InstanceListResponse listresponse = instance.list(properties.getProperty("instanceServiceType"),constAccessToken);
                System.out.println("------------------------");
                System.out.println("response.taskStatus=" + listresponse.getTaskStatus());
                System.out.println("response.errorMsg="+ listresponse.getErrorMsg());
                System.out.println("response.serviceList=" + listresponse.getServiceList());
                System.out.println("response.errorCode=" + listresponse.getErrorCode());
                System.out.println("------------------------");
            }
            // 查询 query
            else if(args[1].equals("query")){
                System.out.println("------进入 queryInstance 查询实例方法------");
                InstanceQueryResponse queryresponse =   instance.query(properties.getProperty("qureyInstanceid"),constAccessToken);
                System.out.println("------------------------");
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
                System.out.println("------------------------");
            }// 修改
            else if(args[1].equals("modify")){
                System.out.println("请求参数 ："
                        +"InstanceId:"+  properties.getProperty("modifyInstanceId")+
                        ",InstanceServiceName:"+ properties.getProperty("modifyInstanceServiceName")+
                        ",ServiceManagerURLs:"+properties.getProperty("modifyServiceManagerURLs")+
                        ",ServiceAPIUrls:"+Integer.valueOf(properties.getProperty("modifyServiceAPIUrls"))+
                        ",Nodes:"+ properties.getProperty("modifyNodes")+
                        ",MainRole:"+ properties.getProperty("modifyMainRole"));

                InstanceModifyResponse modify = instance.modify(properties.getProperty("modifyInstanceId"),
                        properties.getProperty("modifyInstanceServiceName"),
                        properties.getProperty("modifyServiceManagerURLs"),
                        properties.getProperty("modifyServiceAPIUrls"),
                        Integer.valueOf(properties.getProperty("modifyNodes")),
                        properties.getProperty("modifyMainRole"),
                        constAccessToken);
                System.out.println("------------------------");
                System.out.println("response.taskStatus=" + modify.getTaskStatus());
                System.out.println("response.errorMsg="+ modify.getErrorMsg());
                System.out.println("response.errorCode=" + modify.getErrorCode());
                System.out.println("------------------------");

            }
            System.out.println("---------  实例  end   ------------");
        } // 服务资源
        else{
            System.out.println("---------  服务资源  start   ------------");

            // register 注册
            if(args[1].equals("register")){
                System.out.println("------进入 register服务资源注册方法------");
                System.out.println("请求参数 ："+"serviceName:"+
                                properties.getProperty("serviceName")+
                        ",serviceProvider:"+ properties.getProperty("serviceProvider")+
                        ",location:"+properties.getProperty("location")+
                        ",requsetType:"+Integer.valueOf(properties.getProperty("requsetType"))+
                        ",version:"+ properties.getProperty("version")+
                        ",group:"+ properties.getProperty("group")+
                        ",label:"+  properties.getProperty("label")+
                        ",url:"+ properties.getProperty("url")+
                        " ,docDir:"+ properties.getProperty("docDir"));
                ResourceRegisterResponse response =  resourceTest.register(
                        properties.getProperty("serviceName"),
                        properties.getProperty("serviceProvider"),
                        properties.getProperty("location"),
                        Integer.valueOf(properties.getProperty("requsetType")),
                        properties.getProperty("version"),
                        properties.getProperty("group"),
                        properties.getProperty("label"),
                        properties.getProperty("url"),
                        properties.getProperty("docDir"),
                        constAccessToken);
                System.out.println("------------------------"); // 任务执行状态 taskStatus 是 1 表示成功，0 表示失败
                System.out.println("response.taskStatus=" + response.getTaskStatus());
                System.out.println("response.serviceId=" + response.getServiceId());
                System.out.println("response.errorMsg="+ response.getErrorMsg());
                System.out.println("response.errorCode=" + response.getErrorCode());
                System.out.println("------------------------");
            } // publish服务资源发布
            else if(args[1].equals("publish")){
                System.out.println("------进入 publish服务资源发布方法------");
                System.out.println("请求参数 ："+"serviceId:"+ properties.getProperty("publishServiceId"));
                ResourcePublishResponse response = resourceTest.publish(properties.getProperty("publishServiceId"),constAccessToken);
                //  任务执行状态 taskStatus 是 1 表示成功，0 表示失败
                System.out.println("------------------------");
                System.out.println("response.taskStatus=" + response.getTaskStatus());
                System.out.println("response.serviceId=" + response.getServiceId());
                System.out.println("response.errorMsg="+ response.getErrorMsg());
                System.out.println("response.errorCode=" + response.getErrorCode());
                System.out.println("------------------------");

            } // revoke  撤销
            else if(args[1].equals("revoke")){
                System.out.println("------进入 revoke  撤销方法------");
                ResourceRevokeResponse response  = resourceTest.revoke(properties.getProperty("revokeServiceId"), constAccessToken);
                System.out.println("------------------------");
                System.out.println("response.taskStatus=" + response.getTaskStatus());
                System.out.println("response.serviceId=" + response.getServiceId());
                System.out.println("response.errorMsg="+ response.getErrorMsg());
                System.out.println("response.errorCode=" + response.getErrorCode());
                System.out.println("------------------------");
            } // query 查询
            else if(args[1].equals("query")){
                System.out.println("------进入  query 查询方法------");
                ResourceQueryResponse response =  resourceTest.query(properties.getProperty("queryServiceId"), constAccessToken);
                System.out.println("------------------------");
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
                System.out.println("------------------------");
            } // modify 修改
            else if(args[1].equals("modify")){
                System.out.println("------进入 modify 修改方法------");
                System.out.println("请求参数 ："+
                        "ServiceId:"+ properties.getProperty("modifyServiceId")+
                        "serviceName:"+ properties.getProperty("modifyServiceName")+
                        ",serviceProvider:"+ properties.getProperty("modifyServiceProvider")+
                        ",location:"+properties.getProperty("modifyLocation")+
                        ",version:"+ properties.getProperty("modifyVersion")+
                        ",group:"+ properties.getProperty("modifyGroup")+
                        ",label:"+  properties.getProperty("modifyLabel")+
                        ",url:"+ properties.getProperty("modifyUrl")+
                        " ,docDir:"+ properties.getProperty("modifyDocDir")+
                        " ,ServiceStatus:"+ properties.getProperty("modifyServiceStatus"));
                ResourceModifyResponse response =   resourceTest.modify(properties.getProperty("modifyServiceId"),
                         properties.getProperty("modifyServiceName"),
                         properties.getProperty("modifyServiceProvider"),
                         properties.getProperty("modifyLocation"),
                         properties.getProperty("modifyVersion"),
                         properties.getProperty("modifyGroup"),
                         properties.getProperty("modifyLabel"),
                         properties.getProperty("modifyUrl"),
                         properties.getProperty("modifyDocDir"),
                         Integer.valueOf(properties.getProperty("modifyServiceStatus")),
                         constAccessToken);
                System.out.println("------------------------");
                System.out.println("TaskStatus: "+response.getTaskStatus());
                System.out.println("ErrorCode :"+response.getErrorCode());
                System.out.println("ErrorMsg : " +response.getErrorMsg());
                System.out.println("------------------------");
            } // unregister 注销
            else if(args[1].equals("unregister")){
                System.out.println("------进入unregister注销方法------");
                System.out.println("请求参数 ："+"serviceId:"+ properties.getProperty("unregisterServiceId"));
                ResourceUnregisterResponse response =  resourceTest.unregister(properties.getProperty("unregisterServiceId"),constAccessToken);
                System.out.println("------------------------");
                System.out.println("TaskStatus: "+response.getTaskStatus());
                System.out.println("ErrorCode :"+response.getErrorCode());
                System.out.println("ErrorMsg : " +response.getErrorMsg());
                System.out.println("------------------------");
            } // list 集合查询
            else if(args[1].equals("list")){
                System.out.println("------进入list集合查询---");
                System.out.println("请求参数 ："+"serviceName:"+
                        properties.getProperty("list.serviceName")+
                        ",requsetType:"+Integer.valueOf(properties.getProperty("list.requsetType"))+
                        ",group:"+ properties.getProperty("list.group")+
                        ",label:"+  properties.getProperty("list.label"));
                ResourceListResponse response = resourceTest.list(
                        properties.getProperty("list.serviceName"),
                        Integer.valueOf(properties.getProperty("list.requsetType")),
                        properties.getProperty("list.group"),
                        properties.getProperty("list.label"),constAccessToken);
                if(response.getServiceList()!=null){
                    System.out.println("ServiceList: "+response.getServiceList().toString());
                }
                System.out.println("TaskStatus: "+response.getTaskStatus());
                System.out.println("ErrorCode :"+response.getErrorCode());
                System.out.println("ErrorMsg : " +response.getErrorMsg());
            }
        }
    }
    /**
     * 获取根目录
     * @return
     */
    public  String getPath()
    {
        String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        if(System.getProperty("os.name").contains("dows"))
        {
            path = path.substring(1,path.length());
        }
        if(path.contains("jar"))
        {
            path = path.substring(0,path.lastIndexOf("."));
            return path.substring(0,path.lastIndexOf("/"));
        }
        return path.replace("target/classes/", "");
    }
}
