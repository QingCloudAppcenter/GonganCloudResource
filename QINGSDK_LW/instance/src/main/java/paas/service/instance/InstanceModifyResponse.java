package paas.service.instance;

import paas.common.response.Response;

public class InstanceModifyResponse extends Response {
    private String instanceId;

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }
}
