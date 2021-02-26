package paas.service.instance;

import paas.common.response.Response;

public class InstanceListResponse extends Response {
    private String serviceList;
    public void setServiceList(String serviceList){
        this.serviceList = serviceList;
    }
    public String getServiceList(){
        return this.serviceList;
    }

}
