package paas.service.instance;

import paas.common.response.Response;

public class InstanceQueryResponse extends Response {
    private String serviceType;
    private int healthStatus;
    private String compute;
    private int cpuCores;
    private int memory;
    private int storage;
    private int nodes;
    private String serviceManageUrls;
    private String serviceAPIUrls;


    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public int getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(int healthStatus) {
        this.healthStatus = healthStatus;
    }

    public String getCompute() {
        return compute;
    }

    public void setCompute(String compute) {
        this.compute = compute;
    }

    public int getCpuCores() {
        return cpuCores;
    }

    public void setCpuCores(int cpuCores) {
        this.cpuCores = cpuCores;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
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
