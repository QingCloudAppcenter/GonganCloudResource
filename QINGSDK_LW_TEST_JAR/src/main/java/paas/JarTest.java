package paas;

import paas.service.instance.*;
import paas.service.resource.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
        if (args.length < 2) {
            System.out.println("请指定请求的类型参数和方法参数!");
            System.exit(1);
        }
        String method = args[0];
        System.out.println("-----调用类为："+method);
        System.out.println("-----调用方法为："+args[1]);
        Properties properties = new Properties();
        // 使用InPutStream流读取properties文件
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(jarTest.getPath()+"src/main/resources/config.properties"));
            properties.load(bufferedReader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 获取token
        String constAccessToken = properties.getProperty("constAccessToken");
        System.out.println("获取token : ***** "+ constAccessToken +" *****");

        InstanceImpl instance= new InstanceImpl();
        ArrayList<String> validServiceTypes = new ArrayList(Arrays.asList(properties.getProperty("serviceTypes").split(",")));
        instance.setValidServceType(validServiceTypes);

        ResourceImpl resourceTest = new ResourceImpl();
        // 实例
        if(method.equals("instance")){
            System.out.println("---------  实例  start   ------------");
            // createInstance 创建
            if(args[1].equals("create")){
                System.out.println("------进入 createInstance 创建实例方法------");
                System.out.println("请求参数 ："+
                        "serviceType:"+ properties.getProperty("createServiceType")+
                        ",\nserviceName:"+ properties.getProperty("createServiceName")+
                        ",\ncompute:"+properties.getProperty("createCompute")+
                        ",\ncpuCores:"+Integer.valueOf(properties.getProperty("createCpuCores"))+
                        ",\nmemory:"+ properties.getProperty("createMemory")+
                        ",\nstorage:"+ properties.getProperty("createStorage")+
                        ",\nnodes:"+  properties.getProperty("createNodes")+
                        ",\naccessToken:"+ properties.getProperty("constAccessToken"));

                InstanceCreateResponse response = instance.create(
                        properties.getProperty("createServiceType"),
                        properties.getProperty("createServiceName"),
                        properties.getProperty("createCompute"),
                        Integer.valueOf(properties.getProperty("createCpuCores")),
                        Integer.valueOf(properties.getProperty("createMemory")),
                        Integer.valueOf(properties.getProperty("createStorage")),
                        Integer.valueOf(properties.getProperty("createNodes")),
                        constAccessToken);
                System.out.println("response: ------------------------");
                if (response.getReceiveStatus()!=1 || response.getParamsCheckResult()!=1) {
                    System.out.println("receiveStatus=" + response.getReceiveStatus());
                    System.out.println("paramsCheckResult=" + response.getParamsCheckResult());
                } else {
                    System.out.println("taskStatus=" + response.getTaskStatus());
                    System.out.println("errorMsg="+ response.getErrorMsg());
                    System.out.println("errorCode=" + response.getErrorCode());
                    System.out.println("serviceManageUrls=" + response.getServiceManageUrls());
                    System.out.println("serviceAPIUrls=" + response.getServiceAPIUrls());
                    System.out.println("instanceId=" + response.getInstanceId());
                    System.out.println("------------------------");
                }
            }
            // 删除
            else if(args[1].equals("delete")){
                System.out.println("------进入 deleteInstance删除实例方法------");
                System.out.println("请求参数 ："+"deleteInstanceId:"+ properties.getProperty("deleteInstanceId")+
                        ",\naccessToken:"+ properties.getProperty("constAccessToken"));
                InstanceDeleteResponse delete = instance.delete(properties.getProperty("deleteInstanceId"),constAccessToken);
                System.out.println("------------------------");
                System.out.println("taskStatus=" + delete.getTaskStatus());
                System.out.println("errorMsg="+ delete.getErrorMsg());
                System.out.println("errorCode=" + delete.getErrorCode());
                System.out.println("instanceId=" + delete.getInstanceId());
                System.out.println("------------------------");
            }
            // 查询 list
            else if(args[1].equals("list")){
                System.out.println("------进入 listInstance 查询实例方法------");
                System.out.println("请求参数 ："+
                        "\nserviceType:"+ properties.getProperty("instanceServiceType")+
                        ",\naccessToken:"+ properties.getProperty("constAccessToken"));
                InstanceListResponse listresponse = instance.list(properties.getProperty("instanceServiceType"),constAccessToken);
                System.out.println("response: ------------------------");
                System.out.println("taskStatus=" + listresponse.getTaskStatus());
                System.out.println("errorMsg="+ listresponse.getErrorMsg());
                System.out.println("serviceList=" + listresponse.getServiceList());
                System.out.println("errorCode=" + listresponse.getErrorCode());
                System.out.println("------------------------");
            }
            // 查询 query
            else if(args[1].equals("query")){
                System.out.println("------进入 queryInstance 查询实例方法------");
                System.out.println("请求参数 ："+
                        "instanceId:"+ properties.getProperty("queryInstanceId")+
                        ",\naccessToken:"+ properties.getProperty("constAccessToken")+
                        ",\naccessToken:"+ properties.getProperty("constAccessToken"));
                InstanceQueryResponse queryresponse = instance.query(properties.getProperty("queryInstanceId"),constAccessToken);
                System.out.println("response: ------------------------");
                System.out.println("taskStatus="+ queryresponse.getTaskStatus());
                System.out.println("serviceType="+ queryresponse.getServiceType());
                System.out.println("healthStatus=" + queryresponse.getHealthStatus());
                System.out.println("compute=" + queryresponse.getCompute());
                System.out.println("cpuCores=" + queryresponse.getCpuCores());
                System.out.println("memory=" + queryresponse.getMemory());
                System.out.println("storage=" + queryresponse.getStorage());
                System.out.println("nodes=" + queryresponse.getNodes());
                System.out.println("serviceManageUrls=" + queryresponse.getServiceManageUrls());
                System.out.println("serviceAPIUrls=" + queryresponse.getServiceAPIUrls());
                System.out.println("errorMsg="+ queryresponse.getErrorMsg());
                System.out.println("errorCode=" +queryresponse.getErrorCode());
                System.out.println("------------------------");
            }// 修改
            else if(args[1].equals("modify")){
                System.out.println("请求参数 ："
                        +"instanceId:"+  properties.getProperty("modifyInstanceId")+
                        ",\ninstanceServiceName:"+ properties.getProperty("modifyInstanceServiceName")+
                        ",\nserviceManagerURLs:"+properties.getProperty("modifyServiceManagerURLs")+
                        ",\nserviceAPIUrls:"+properties.getProperty("modifyServiceAPIUrls")+
                        ",\nnodes:"+ Integer.valueOf(properties.getProperty("modifyNodes"))+
                        ",\naccessToken:"+ properties.getProperty("constAccessToken"));

                InstanceModifyResponse modify = instance.modify(properties.getProperty("modifyInstanceId"),
                        properties.getProperty("modifyInstanceServiceName"),
                        properties.getProperty("modifyServiceManagerURLs"),
                        properties.getProperty("modifyServiceAPIUrls"),
                        Integer.valueOf(properties.getProperty("modifyNodes")),
                        constAccessToken);
                System.out.println("response: ------------------------");
                System.out.println("instanceId=" + modify.getInstanceId());
                System.out.println("taskStatus=" + modify.getTaskStatus());
                System.out.println("errorMsg="+ modify.getErrorMsg());
                System.out.println("errorCode=" + modify.getErrorCode());
                System.out.println("------------------------");

            }
            System.out.println("---------  实例  end   ------------");
        }
        //////////////////////////// 服务资源
        else{
            System.out.println("---------  服务资源  start   ------------");

            // register 注册
            if(args[1].equals("register")){
                System.out.println("------进入 register服务资源注册方法------");
                System.out.println("请求参数 ："+"serviceName:"+
                                properties.getProperty("serviceName")+
                        ",\nserviceProvider:"+ properties.getProperty("serviceProvider")+
                        ",\nlocation:"+properties.getProperty("location")+
                        ",\nrequsetType:"+Integer.valueOf(properties.getProperty("requsetType"))+
                        ",\nversion:"+ properties.getProperty("version")+
                        ",\ngroup:"+ properties.getProperty("group")+
                        ",\nlabel:"+  properties.getProperty("label")+
                        ",\nurl:"+ properties.getProperty("url")+
                        ",\ndocDir:"+ properties.getProperty("docDir")+
                        ",\naccessToken:"+ properties.getProperty("constAccessToken"));
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
                System.out.println("response: ------------------------");
                System.out.println("taskStatus=" + response.getTaskStatus());
                System.out.println("serviceId=" + response.getServiceId());
                System.out.println("errorMsg="+ response.getErrorMsg());
                System.out.println("errorCode=" + response.getErrorCode());
                System.out.println("------------------------");
            } // publish服务资源发布
            else if(args[1].equals("publish")){
                System.out.println("------进入 publish服务资源发布方法------");
                System.out.println("请求参数 ："+
                        "serviceId:"+ properties.getProperty("commonServiceId")+
                        ",\naccessToken:"+ properties.getProperty("constAccessToken"));
                ResourcePublishResponse response = resourceTest.publish(properties.getProperty("publishServiceId"),constAccessToken);
                //  任务执行状态 taskStatus 是 1 表示成功，0 表示失败
                System.out.println("response: ------------------------");
                System.out.println("taskStatus=" + response.getTaskStatus());
                System.out.println("serviceId=" + response.getServiceId());
                System.out.println("errorMsg="+ response.getErrorMsg());
                System.out.println("errorCode=" + response.getErrorCode());
                System.out.println("------------------------");

            } // revoke  撤销
            else if(args[1].equals("revoke")){
                System.out.println("------进入 revoke  撤销方法------");
                System.out.println("请求参数 ："+
                        "serviceId:"+ properties.getProperty("commonServiceId")+
                        ",\naccessToken:"+ properties.getProperty("constAccessToken"));
                ResourceRevokeResponse response  = resourceTest.revoke(properties.getProperty("revokeServiceId"), constAccessToken);
                System.out.println("response: ------------------------");
                System.out.println("taskStatus=" + response.getTaskStatus());
                System.out.println("serviceId=" + response.getServiceId());
                System.out.println("errorMsg="+ response.getErrorMsg());
                System.out.println("errorCode=" + response.getErrorCode());
                System.out.println("------------------------");
            } // query 查询
            else if(args[1].equals("query")){
                System.out.println("------进入  query 查询方法------");
                System.out.println("请求参数 ："+
                        "serviceId:"+ properties.getProperty("commonServiceId")+
                        ",\naccessToken:"+ properties.getProperty("constAccessToken"));
                ResourceQueryResponse response =  resourceTest.query(properties.getProperty("queryServiceId"), constAccessToken);
                System.out.println("response: ------------------------");
                System.out.println("taskStatus= "+response.getTaskStatus());
                System.out.println("serviceId= "+response.getServiceId());
                System.out.println("serviceName= "+response.getServiceName());
                System.out.println("serviceProvider= "+response.getServiceProvider());
                System.out.println("location= "+response.getLocation());
                System.out.println("requsetType= "+response.getRequsetType());
                System.out.println("version= "+response.getVersion());
                System.out.println("group= "+response.getGroup());
                System.out.println("label= "+response.getLabel());
                System.out.println("url= "+response.getUrl());
                System.out.println("docDir= "+response.getDocDir());
                System.out.println("releaseTime="+response.getReleaseTime());
                System.out.println("serviceStatus= "+response.getServiceStatus());
                System.out.println("errorMsg="+ response.getErrorMsg());
                System.out.println("errorCode=" + response.getErrorCode());
                System.out.println("------------------------");
            } // modify 修改
            else if(args[1].equals("modify")){
                System.out.println("------进入 modify 修改方法------");
                System.out.println("请求参数 ："+
                        "serviceId:"+ properties.getProperty("commonServiceId")+
                        ",\nserviceName:"+ properties.getProperty("modifyServiceName")+
                        ",\nserviceProvider:"+ properties.getProperty("modifyServiceProvider")+
                        ",\nlocation:"+properties.getProperty("modifyLocation")+
                        ",\nversion:"+ properties.getProperty("modifyVersion")+
                        ",\ngroup:"+ properties.getProperty("modifyGroup")+
                        ",\nlabel:"+  properties.getProperty("modifyLabel")+
                        ",\nurl:"+ properties.getProperty("modifyUrl")+
                        ",\ndocDir:"+ properties.getProperty("modifyDocDir")+
                        ",\nserviceStatus:"+ properties.getProperty("modifyServiceStatus")+
                        ",\naccessToken:"+ properties.getProperty("constAccessToken"));
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
                System.out.println("response: ------------------------");
                System.out.println("taskStatus: "+response.getTaskStatus());
                System.out.println("errorCode :"+response.getErrorCode());
                System.out.println("errorMsg : " +response.getErrorMsg());
                System.out.println("------------------------");
            } // unregister 注销
            else if(args[1].equals("unregister")){
                System.out.println("------进入unregister注销方法------");
                System.out.println("请求参数 ："+
                        "serviceId:"+ properties.getProperty("commonServiceId")+
                        ",\naccessToken:"+ properties.getProperty("constAccessToken"));
                ResourceUnregisterResponse response =  resourceTest.unregister(properties.getProperty("unregisterServiceId"),constAccessToken);
                System.out.println("response: ------------------------");
                System.out.println("taskStatus: "+response.getTaskStatus());
                System.out.println("errorCode :"+response.getErrorCode());
                System.out.println("errorMsg : " +response.getErrorMsg());
                System.out.println("------------------------");
            } // list 集合查询
            else if(args[1].equals("list")){
                System.out.println("------进入list集合查询---");
                System.out.println("请求参数 ："+
                        "serviceName:"+ properties.getProperty("list.serviceName")+
                        ",\nrequsetType:"+Integer.valueOf(properties.getProperty("list.requsetType"))+
                        ",\ngroup:"+ properties.getProperty("list.group")+
                        ",\nlabel:"+  properties.getProperty("list.label")+
                        ",\naccessToken:"+ properties.getProperty("constAccessToken"));
                ResourceListResponse response = resourceTest.list(
                        properties.getProperty("list.serviceName"),
                        Integer.valueOf(properties.getProperty("list.requsetType")),
                        properties.getProperty("list.group"),
                        properties.getProperty("list.label"),constAccessToken);
                System.out.println("response: ------------------------");
                System.out.println("serviceList: "+response.getServiceList());
                System.out.println("taskStatus: "+response.getTaskStatus());
                System.out.println("errorCode :"+response.getErrorCode());
                System.out.println("errorMsg : " +response.getErrorMsg());
                System.out.println("------------------------");

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
