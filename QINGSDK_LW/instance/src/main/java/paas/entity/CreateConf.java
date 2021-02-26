package paas.entity;

import java.util.Map;

/**
 * @author wzl
 */
public class CreateConf {

    private Object cluster;
    private String version;
    private String resource_group;
    private String zone;
    private Map env;

    public Object getCluster() {
        return cluster;
    }

    public void setCluster(Object cluster) {
        this.cluster = cluster;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getResource_group() {
        return resource_group;
    }

    public void setResource_group(String resource_group) {
        this.resource_group = resource_group;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public Map getEnv() {
        return env;
    }

    public void setEnv(Map env) {
        this.env = env;
    }
}
