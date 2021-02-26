package paas.service.resource;

public interface IResource {
    /**
     *  服务资源注册(register)
     * @param serviceName
     * @param serviceProvider
     * @param location
     * @param requsetType
     * @param version
     * @param group
     * @param label
     * @param url
     * @param docDir
     * @param accessToken
     * @return
     */
    public  ResourceRegisterResponse  register(String serviceName, String serviceProvider, String
            location, Integer requsetType, String version, String group, String label, String url, String docDir,
                                               String accessToken);

    /**
     * 服务资源发布(publish)
     * @param serviceId
     * @param accessToken
     * @return
     */
    public ResourcePublishResponse publish(String serviceId, String accessToken);

    /**
     * 服务资源撤销(revoke)
     * @param serviceId
     * @param accessToken
     * @return
     */
    public ResourceRevokeResponse revoke(String serviceId, String accessToken);

    /**
     *  服务资源查询 (query)
     * @param serviceId
     * @param accessToken
     * @return
     */
    public ResourceQueryResponse query(String serviceId, String accessToken);

    /**
     *  服务资源修改(modify)
     * @param serviceId
     * @param serviceName
     * @param serviceProvider
     * @param location
     * @param version
     * @param group
     * @param label
     * @param url
     * @param docDir
     * @param serviceStatus
     * @param accessToken
     * @return
     */
    public ResourceModifyResponse modify(String serviceId, String serviceName, String serviceProvider,
                                         String location, String version, String group, String label, String url, String docDir, Integer
                                                 serviceStatus, String accessToken);

    /**
     *  服务资源注销(unregister)
     * @param serviceId
     * @param accessToken
     * @return
     */
    public ResourceUnregisterResponse unregister(String serviceId, String accessToken);

    /**
     *  获取服务资源列表(list)
     * @param serviceName
     * @param requsetType
     * @param group
     * @param label
     * @param accessToken
     * @return
     */
    public ResourceListResponse list(String serviceName, Integer requsetType, String group, String
            label, String accessToken);
}
