package paas.service.resource;

public class ResourceModifyResponse extends Response {
    private String serviceId;
    private String serviceName;
    private String serviceProvider;
    private String location;
    private String version;
    private String group;
    private String label;
    private String url;
    private String docDir;
    private Integer serviceStatus;
    private String accessToken;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(String serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDocDir() {
        return docDir;
    }

    public void setDocDir(String docDir) {
        this.docDir = docDir;
    }

    public Integer getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(Integer serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public ResourceModifyResponse(String serviceId, String serviceName, String serviceProvider, String location, String version, String group, String label, String url, String docDir, Integer serviceStatus, String accessToken) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.serviceProvider = serviceProvider;
        this.location = location;
        this.version = version;
        this.group = group;
        this.label = label;
        this.url = url;
        this.docDir = docDir;
        this.serviceStatus = serviceStatus;
        this.accessToken = accessToken;
    }

    public ResourceModifyResponse() {
    }
}
