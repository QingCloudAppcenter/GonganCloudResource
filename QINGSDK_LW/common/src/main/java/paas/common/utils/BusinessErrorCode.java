package paas.common.utils;

public enum BusinessErrorCode {
    /**
     *
     */
    ERROR(6000,"程序异常"),
    NULL_CLUSTER_MODEL_ERROR(6001,"返回CLusterModel为空"),
    NULL_RESOURCE_TAG_PAIR_MODEL_ERROR(6002,"返回ResourceTagPairModel为空"),
    NULL_CLUSTER_TAGS_ERROR(6003,"Cluster的Tags为空"),
    UNKNOWN_SERVICETYPE_ERROR(6004,"未知服务类型"),
    NULL_MAINROLE_ERROR(6005,"无法获取主角色"),
    NULL_CLUSTER_NODES_ERROR(6006,"Cluster的Nodes为空"),
    NOTEXIST_CLUSTER_MAINROLE_ERROR(6007,"Cluster的各节点中没有主角色"),
    NULL_CLUSTER_SIZE(6008,"无法获取集群规模"),
    MEMORY_SIZE(6010,"内存范围在[2，4，6，8]GB中 ");


    private Integer value;
    private String desc;

    private BusinessErrorCode(Integer value, String desc) {
        this.setValue(value);
        this.setDesc(desc);
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "[" + this.value + "]" + this.desc;
    }
}