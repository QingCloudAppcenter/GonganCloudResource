package paas.service.resource;

/**
 * 注册
 */
public class ResourceRegisterResponse extends Response {
    private String serviceId = "";


    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
