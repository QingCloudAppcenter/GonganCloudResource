package paas.service.resource;


public class ResourcePublishResponse extends Response {
    private String serviceId;
    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
