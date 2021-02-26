package paas.service.resource;

import paas.common.response.Response;

public class ResourceRevokeResponse extends Response {
    private String serviceId;
    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
