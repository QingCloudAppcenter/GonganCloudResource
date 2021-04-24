package paas.service.resource;

public class ResourceUnregisterResponse extends Response {
    private String serviceId;
    private String accessToken;

    public ResourceUnregisterResponse() {
    }

    public ResourceUnregisterResponse(String serviceId, String accessToken) {
        this.serviceId = serviceId;
        this.accessToken = accessToken;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
