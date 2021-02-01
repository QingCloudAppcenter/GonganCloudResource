package paas.service.resource;

import com.qingcloud.sdk.service.Types;
import paas.common.response.Response;

import java.util.List;

public class ResourceListResponse extends Response {

    private String serviceList;

    public String getServiceList() {
        return serviceList;
    }

    public void setServiceList(String serviceList) {
        this.serviceList = serviceList;
    }
}
