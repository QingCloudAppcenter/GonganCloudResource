package com.qingcloud.sdk.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.qingcloud.sdk.config.EnvContext;
import com.qingcloud.sdk.constants.QCConstant;
import com.qingcloud.sdk.exception.QCException;
import com.qingcloud.sdk.model.IaasParamBody;
import com.qingcloud.sdk.model.OutputModel;
import com.qingcloud.sdk.request.ResourceRequestFactory;
import com.qingcloud.sdk.request.ResponseCallBack;
import com.qingcloud.sdk.utils.QCStringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleClusterService {

    private EnvContext envContext;

    private String zone;

    public SimpleClusterService(EnvContext envContext) {
        this.envContext = envContext;
    }

    public SimpleClusterService(EnvContext envContext, String zone) {
        this.envContext = envContext;
        this.zone = zone;
    }

    /**
      * @param input Request parameters and headers in the class
      * @return DescribeClustersOutput Response body and headers in the class
      * @throws QCException IOException or network error
      *
      * <a href=https://docs.qingcloud.com/api/cluster/describe_clusters.html>Documentation URL</a>
      */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public DescribeClustersOutput describeClusters(DescribeClustersInput input) throws QCException {
        if (input == null) {
            input = new DescribeClustersInput();
        }
        Map context = new HashMap();
        context.put(QCConstant.ENV_CONTEXT_KEY, this.envContext);
        context.put("OperationName", "DescribeClusters");
        context.put("APIName", "DescribeClusters");
        context.put("ServiceName", "DescribeClusters");
        context.put("RequestMethod", "GET");
        context.put("RequestURI", "/iaas/DescribeClusters");

        input.setAction("DescribeClusters");
        if (QCStringUtil.isEmpty(this.zone)) {
            input.setZone(this.envContext.getZone());
        } else {
            input.setZone(this.zone);
        }

        Object backModel = ResourceRequestFactory.getResourceRequest().sendApiRequest(context, input, DescribeClustersOutput.class);
        if(backModel != null){
            return (DescribeClustersOutput)backModel;
        }
        return null;
    }

     /**
       * @param input Request parameters and headers in the class
       * @param callback Response calls back when success
       * @throws QCException IOException or network error
       *
       * <a href=https://docs.qingcloud.com/api/cluster/describe_clusters.html>Documentation URL</a>
       */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void describeClusters(DescribeClustersInput input, ResponseCallBack<DescribeClustersOutput> callback) throws QCException {
        if (input == null) {
            input = new DescribeClustersInput();
        }
        Map context = new HashMap();
        context.put(QCConstant.ENV_CONTEXT_KEY, this.envContext);
        context.put("OperationName", "DescribeClusters");
        context.put("APIName", "DescribeClusters");
        context.put("ServiceName", "DescribeClusters");
        context.put("RequestMethod", "GET");
        context.put("RequestURI", "/iaas/DescribeClusters");

        input.setAction("DescribeClusters");
        if (QCStringUtil.isEmpty(this.zone)) {
            input.setZone(this.envContext.getZone());
        } else {
            input.setZone(this.zone);
        }

        if(callback == null) {
            throw new QCException("callback can't be null");
        }

        ResourceRequestFactory.getResourceRequest().sendApiRequestAsync(context,input,callback);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DescribeClustersOutput extends OutputModel {
        private String action;

        @JsonProperty(value = "action")
        public void setAction(String action) {
            this.action = action;
        }

        @JsonProperty(value = "action")
        public String getAction() {
            return this.action;
        }

        private List<Types.SimpleClusterModel> clusterSet;

        @JsonProperty(value = "cluster_set")
        public void setClusterSet(List<Types.SimpleClusterModel> clusterSet) {
            this.clusterSet = clusterSet;
        }

        @JsonProperty(value = "cluster_set")
        public List<Types.SimpleClusterModel> getClusterSet() {
            return this.clusterSet;
        }

        private Integer retCode;

        @JsonProperty(value = "ret_code")
        public void setRetCode(Integer retCode) {
            this.retCode = retCode;
        }

        @JsonProperty(value = "ret_code")
        public Integer getRetCode() {
            return this.retCode;
        }

        private Integer totalCount;

        @JsonProperty(value = "total_count")
        public void setTotalCount(Integer totalCount) {
            this.totalCount = totalCount;
        }

        @JsonProperty(value = "total_count")
        public Integer getTotalCount() {
            return this.totalCount;
        }

    }

    public static class DescribeClustersInput extends IaasParamBody {
        private List<String> appVersions;

        @JsonProperty(value = "app_versions")
        public void setAppVersions(List<String> appVersions) {
            this.appVersions = appVersions;
        }

        @JsonProperty(value = "app_versions")
        public List<String> getAppVersions() {
            return this.appVersions;
        }

        private List<String> clusters;

        @JsonProperty(value = "clusters")
        public void setClusters(List<String> clusters) {
            this.clusters = clusters;
        }

        @JsonProperty(value = "clusters")
        public List<String> getClusters() {
            return this.clusters;
        }

        private Integer verbose;

        @JsonProperty(value = "verbose")
        public void setVerbose(Integer verbose) {
            this.verbose = verbose;
        }

        @JsonProperty(value = "verbose")
        public Integer getVerbose() {
            return this.verbose;
        }


        private String status;
        @JsonProperty(value = "status")
        public void setStatus(String status) {
            this.status = status;
        }
        @JsonProperty(value = "status")
        public String getStatus() {
            return this.status;
        }
    }
}


