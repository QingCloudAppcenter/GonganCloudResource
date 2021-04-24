package paas.service.instance;

import paas.common.response.Response;

public class InstanceQueryResponse extends Response {
    private String serviceType="";
    private String healthStatus="";
    private String compute="";
    private String cpuCores="";
    private String memory="";
    private String storage="";
    private int nodes;
    private String serviceManageUrls="";
    private String serviceAPIUrls="";


    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public String getCompute() {
        return compute;
    }

    public void setCompute(String compute) {
        this.compute = compute;
    }

    public String getCpuCores() {
        return cpuCores;
    }

    public void setCpuCores(String cpuCores) {
        this.cpuCores = cpuCores;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public int getNodes() {
        return nodes;
    }

    public void setNodes(int nodes) {
        this.nodes = nodes;
    }

    public String getServiceManageUrls() {
        return serviceManageUrls;
    }

    public void setServiceManageUrls(String serviceManageUrls) {
        this.serviceManageUrls = serviceManageUrls;
    }

    public String getServiceAPIUrls() {
        return serviceAPIUrls;
    }

    public void setServiceAPIUrls(String serviceAPIUrls) {
        this.serviceAPIUrls = serviceAPIUrls;
    }



}
