package paas.service.instance;

public interface IInstance {

    /**
     * 创建服务实例(create)
     * @param serviceType
     * @param serviceName
     * @param compute
     * @param cpuCores
     * @param memory
     * @param storage
     * @param nodes
     * @param accessToken
     * @return
     */
    public InstanceCreateResponse create(String serviceType, String serviceName, String compute, int
            cpuCores, int memory, int storage, int nodes, String accessToken);

    /**
     * 删除服务实例(delete)
     * @param instanceId
     * @param accessToken
     * @return
     */
    public InstanceDeleteResponse delete(String instanceId, String accessToken);

    /**
     * 修改服务实例(modify)
     * @param instanceId
     * @param serviceName
     * @param serviceManagerURLs
     * @param serviceAPIUrls
     * @param nodes
     * @param accessToken
     * @return
     */
    public InstanceModifyResponse modify(String instanceId, String serviceName, String
            serviceManagerURLs, String serviceAPIUrls, int nodes, String accessToken);

    /**
     * 获取服务实例列表(list)
     * @param serviceType
     * @param accessToken
     * @return
     */
    public InstanceListResponse list(String serviceType, String accessToken);

    /**
     * 查询服务实例详情(query)
     * @param instanceId
     * @param accessToken
     * @return
     */
    public InstanceQueryResponse query(String instanceId, String accessToken);

}
