package paas.service.resource;

public class ResourceQueryResponse extends Response {
    /**
     *  返回值
     * 	任务执行状态 taskStatus        是   1 表示成功，0 表示失败
     * 	服务资源唯一标识 servieId      是
     * 	服务资源名称 serviceName      是
     * 	服务提供商 serviceProvider,   否
     * 	服务注册位置 location         否	    编码规则遵循 GBT2260-2007《中华人民共和国行政区划代码》。
     * 	服务请求类型 requsetType      是      1 表示 rest 接口；2 表示 api 接口；3 表示 webservice；4 表示其
     * 	服务版本 version              是     通常在接口不兼容时版本号才需要升级。
     * 	服务分组 group                是
     * 	服务标签 label                否
     * 	服务访问地址 url              是
     * 	服务帮助文档路径 docDir       否     提供一个 URL 地址，该地址所在页面文档包含各类型服务的具体帮助信息，包括接口参数说明，调用方式
     * 	服务发布时间 releaseTime      否     以时间戳形式返回
     * 	可用状态 serviceStatus       否      0 表示不可用；1 表示可用。
     * 	错误码 errorCode             否
     *  失败原因 errorMsg            否	    失败时填写。
     */
    private String serviceId;
    private String serviceName;
    private String serviceProvider;
    private String location;
    private Integer requsetType;
    private String version;
    private String group;
    private String label;
    private String url;
    private String docDir;
    private Long releaseTime;
    private String serviceStatus;

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

    public Integer getRequsetType() {
        return requsetType;
    }

    public void setRequsetType(Integer requsetType) {
        this.requsetType = requsetType;
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

    public Long getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Long releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }
}
