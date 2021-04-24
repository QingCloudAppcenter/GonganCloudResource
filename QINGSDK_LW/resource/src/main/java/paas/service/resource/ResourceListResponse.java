package paas.service.resource;

public class ResourceListResponse extends Response {

    private String serviceList = "";

    public String getServiceList() {
        return serviceList;
    }

    public void setServiceList(String serviceList) {
        this.serviceList = serviceList;
    }
}
