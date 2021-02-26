package paas.service.instance;

import paas.common.response.Response;

public class InstanceCreateResponse extends Response {
    private int receiveStatus;
    private int paramsCheckResult;
    private String instanceId;
    private String serviceManageUrls;
    private String serviceAPIUrls;

    public int getReceiveStatus() {
        return receiveStatus;
    }

    public void setReceiveStatus(int receiveStatus) {
        this.receiveStatus = receiveStatus;
    }

    public int getParamsCheckResult() {
        return paramsCheckResult;
    }

    public void setParamsCheckResult(int paramsCheckResult) {
        this.paramsCheckResult = paramsCheckResult;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
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
