package paas.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wzl
 */
public class CreateConfParam {

    /**
     * 返回 分布式文件存储(01001) ,离线计算(02001) 内存计算(02003) 的env信息
     *
     * @return map
     */
    public static  Map getEnv001() {
        Map map = new HashMap<>();
        map.put("enable_hive", false);
        map.put("hive_execution_engine", "spark");
        map.put("PYSPARK_PYTHON", "python2");
        map.put("use_remote_mysql", false);
        map.put("remote_mysql_ip", "");
        map.put("hive_metastore_username", "hive");
        map.put("hive_metastore_password", "hive");
        map.put("hive_exec_parallel", "true");
        map.put("hive_exec_parallel_thread_number", 8);
        map.put("hive_merge_mapredfiles", "true");
        map.put("hive_merge_size_per_task", 128000000);
        map.put("hive_merge_smallfiles_avgsize", 5000000);
        map.put("hive_merge_sparkfiles", "true");
        map.put("hive_spark_driver_memory", "512m");
        map.put("hive_spark_executor_memory", "512m");
        map.put("hive_spark_executor_cores", 1);
        map.put("hive_spark_executor_instances", 3);
        map.put("hive_server2_idle_session_timeout", "7d");
        map.put("hive_server2_idle_operation_timeout", "5d");
        map.put("hive_server2_session_check_interval", "6h");
        map.put("QingStor", false);
        map.put("QingStor_zone", "pek3a");
        map.put("s3_endpoint", "");
        map.put("enable_spark_standalone", false);
        map.put("spark_master_daemon_memory", 1024);
        map.put("spark_worker_daemon_memory", 1024);
        map.put("spark_worker_cleanup_enabled", "true");
        map.put("spark_worker_cleanup_interval", 28800);
        map.put("spark_worker_cleanup_appDataTtl", 86400);
        map.put("spark.scheduler.mode", "FIFO");
        map.put("flink.jobmanager.heap.size", "1024m");
        map.put("flink.taskmanager.heap.size", "1024m");
        map.put("flink.parallelism.default", 1);
        map.put("flink.jobmanager.archive.expiration-time", 30);
        map.put("flink.slot.idle.timeout", 50000);
        map.put("flink.slot.request.timeout", 300000);
        map.put("flink.env.log.max", 30);
        map.put("hadoop_proxyuser", "");
        map.put("hadoop_proxyuser_hosts", "*");
        map.put("hadoop_proxyuser_groups", "*");
        map.put("node_manager_yarn_heapsize", 1000);
        map.put("resource_manager_yarn_heapsize", 1000);
        map.put("datanode_hadoop_heapsize", 1000);
        map.put("dfs_namenode_handler_count", 10);
        map.put("dfs_datanode_handler_count", 10);
        map.put("dfs_replication", 2);
        map.put("fs_trash_interval", 1440);
        map.put("yarn.resourcemanager.scheduler.class", "org.apache.hadoop.yarn.server.resourcemanager.scheduler.capacity.CapacityScheduler");
        map.put("yarn.resourcemanager.client.thread-count", 50);
        map.put("yarn.resourcemanager.amlauncher.thread-count", 50);
        map.put("yarn.resourcemanager.scheduler.client.thread-count", 50);
        map.put("yarn.resourcemanager.resource-tracker.client.thread-count", 50);
        map.put("yarn.resourcemanager.admin.client.thread-count", 1);
        map.put("yarn.nodemanager.container-manager.thread-count", 20);
        map.put("yarn.nodemanager.delete.thread-count", 4);
        map.put("yarn.nodemanager.localizer.client.thread-count", 5);
        map.put("yarn.nodemanager.localizer.fetch.thread-count", 4);
        map.put("yarn.nodemanager.pmem-check-enabled", true);
        map.put("yarn.nodemanager.vmem-check-enabled", true);
        map.put("yarn.nodemanager.vmem-pmem-ratio", 5);
        map.put("yarn.scheduler.minimum-allocation-mb", 1024);
        map.put("yarn.scheduler.maximum-allocation-mb", 8192);
        map.put("yarn.scheduler.minimum-allocation-vcores", 1);
        map.put("yarn.scheduler.maximum-allocation-vcores", 32);
        map.put("yarn.scheduler.fair.user-as-default-queue", true);
        map.put("yarn.scheduler.fair.preemption", false);
        map.put("yarn.scheduler.fair.preemption.cluster-utilization-threshold", 0.8);
        map.put("yarn.scheduler.fair.sizebasedweight", false);
        map.put("yarn.scheduler.fair.assignmultiple", false);
        map.put("yarn.scheduler.fair.max.assign", -1);
        map.put("yarn.scheduler.fair.locality.threshold.node", -1);
        map.put("yarn.scheduler.fair.locality.threshold.rack", -1);
        map.put("yarn.scheduler.fair.allow-undeclared-pools", true);
        map.put("yarn.scheduler.fair.update-interval-ms", 500);
        map.put("yarn_log-aggregation-enable", false);
        map.put("yarn_log-aggregation_retain-seconds", 86400);
        map.put("yarn.log-aggregation.retain-check-interval-seconds", 7200);
        map.put("yarn.nodemanager.log.retain-seconds", 86400);
        map.put("yarn.nodemanager.delete.debug-delay-sec", 0);
        map.put("yarn.nodemanager.remote-app-log-dir", "/tmp/hadoop-yarn");
        map.put("yarn.nodemanager.remote-app-log-dir-suffix", "logs");
        map.put("yarn.webapp.ui2.enable", true);
        return map;
    }
    /**
     * 返回 分布式文件存储(01001) ,离线计算(02001) 内存计算(02003) 的conf信息
     *
     * @return CreateConf
     */
    public static CreateConf getConf001(){
        CreateConf createConf = new CreateConf();
        createConf.setVersion("appv-v71be1fi");
        createConf.setResource_group("custom");
        createConf.setEnv(getEnv001());
        return createConf;
    }

    /**
     * 返回 分布式列式存储 的env信息
     *
     * @return map
     */
    public static  Map getEnv01002() {
        Map map = new HashMap<>();
        map.put("fs.trash.interval", 1440);
        map.put("dfs.replication", 2);
        map.put("dfs.namenode.handler.count", 10);
        map.put("dfs.datanode.handler.count", 10);
        map.put("hbase.regionserver.handler.count", 30);
        map.put("hbase.master.handler.count", 25);
        map.put("zookeeper.session.timeout", 60000);
        map.put("hbase.hregion.majorcompaction", 0);
        map.put("hbase.hstore.blockingStoreFiles", 1000000);
        map.put("hbase.regionserver.optionalcacheflushinterval", 0);
        map.put("hfile.block.cache.size", 0.4);
        map.put("hbase.regionserver.global.memstore.size", 0.4);
        map.put("hfile.index.block.max.size", 131072);
        map.put("hbase.hregion.max.filesize", 10737418240L);
        map.put("hbase.master.logcleaner.ttl", 600000);
        map.put("hbase.ipc.server.callqueue.handler.factor", 0.1);
        map.put("hbase.ipc.server.callqueue.read.ratio", 0);
        map.put("hbase.ipc.server.callqueue.scan.ratio", 0);
        map.put("hbase.regionserver.msginterval", 3000);
        map.put("hbase.regionserver.logroll.period", 3600000);
        map.put("hbase.regionserver.regionSplitLimit", 1000);
        map.put("hbase.balancer.period", 300000);
        map.put("hbase.regions.slop", 0.001);
        map.put("io.storefile.bloom.block.size", 131072);
        map.put("hbase.rpc.timeout", 60000);
        map.put("hbase.column.max.version", 1);
        map.put("hbase.security.authorization", "false");
        map.put("qingcloud.hbase.major.compact.hour", 3);
        map.put("qingcloud.phoenix.on.hbase.enable", "false");
        map.put("phoenix.functions.allowUserDefinedFunctions", "false");
        map.put("phoenix.transactions.enabled", "false");
        return map;
    }
    /**
     * 返回 分布式列式存储 的conf信息
     *
     * @return CreateConf
     */
    public static CreateConf getConf01002(){
        CreateConf createConf = new CreateConf();
        createConf.setVersion("appv-y0dunpcc");
        createConf.setResource_group("custom");
        createConf.setEnv(getEnv01002());
        return createConf;
    }

    /**
     * 返回 关系型数据库 的env信息
     *
     * @return Map
     */
    public static  Map getEnv01003() {
        Map map = new HashMap<>();
        map.put("ftp_user", "ftpuser");
        map.put("ftp_passwd", "ftppassword");
        map.put("load_read_requests_to_master", "NO");
        map.put("election-timeout", 20000);
        map.put("admit-defeat-hearbeat-count", 5);
        map.put("port", 3306);
        map.put("audit_log_policy", "NONE");
        map.put("audit_log_format", "OLD");
        map.put("audit_log_rotate_on_size", 104857600);
        map.put("audit_log_rotations", 6);
        map.put("default-time-zone", "+08:00");
        map.put("default-storage-engine", "InnoDB");
        map.put("loose_tokudb_cache_size", -1);
        map.put("innodb_buffer_pool_size", -1);
        map.put("character_set_server", "utf8mb4");
        map.put("interactive_timeout", 3600);
        map.put("back_log", 2048);
        map.put("expire_logs_days", 7);
        map.put("ft_min_word_len", 4);
        map.put("group_concat_max_len", 1024);
        map.put("key_buffer_size", 33554432);
        map.put("log_bin_trust_function_creators", 1);
        map.put("long_query_time", 3);
        map.put("lower_case_table_names", 0);
        map.put("max_connections", 1024);
        map.put("max_connect_errors", 655360);
        map.put("binlog_cache_size", 32768);
        map.put("binlog_stmt_cache_size", 32768);
        map.put("max_execution_time", 0);
        map.put("query_cache_size", 0);
        map.put("query_cache_type", "OFF");
        map.put("sync_master_info", 1000);
        map.put("sync_relay_log", 1000);
        map.put("sync_relay_log_info", 1000);
        map.put("table_open_cache", 2000);
        map.put("thread_cache_size", 128);
        map.put("wait_timeout", 3600);
        map.put("innodb_ft_max_token_size", 84);
        map.put("innodb_ft_min_token_size", 3);
        map.put("sql_mode", "STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION");
        map.put("max_allowed_packet", 134217728);
        map.put("tokudb_fsync_log_period", 0);
        map.put("slave_parallel_workers", 8);
        map.put("slave_pending_jobs_size_max", 134217728);
        map.put("event_scheduler", "OFF");
        map.put("innodb_print_all_deadlocks", 0);
        map.put("skip-name-resolve", 0);
        map.put("autocommit", 1);
        map.put("transaction_isolation", "REPEATABLE-READ");
        map.put("innodb_log_buffer_size", 16777216);
        map.put("innodb_log_file_size", 268435456);
        map.put("innodb_log_files_in_group", 2);
        map.put("validate_password_policy", "MEDIUM");
        map.put("validate_password_length", 8);
        map.put("innodb_flush_method", "fsync");
        map.put("innodb_use_native_aio", 1);
        map.put("connection_control_failed_connections_threshold", 3);
        map.put("connection_control_min_connection_delay", 1000);
        map.put("connection_control_max_connection_delay", 2147483647);
        map.put("not_load_read_to_much_delay_slave", 0);
        return map;
    }
    /**
     * 返回 关系型数据库 的conf信息
     *
     * @return CreateConf
     */
    public static CreateConf getConf01003(){
        CreateConf createConf = new CreateConf();
        createConf.setVersion("appv-fjjyg8ae");
        createConf.setEnv(getEnv01003());
        return createConf;
    }

    /**
     * 返回 分布式内存数据库 的env信息
     *
     * @return Map
     */
    public static  Map getEnv01004() {
        Map map = new HashMap<>();
        map.put("disabled-commands", "no");
        map.put("maxmemory_portion", 80);
        map.put("databases", 16);
        map.put("sentinel.down-after-milliseconds", 4000);
        map.put("sentinel.failover-timeout", 60000);
        map.put("enable-commands", "DISABLE_ALL");
        map.put("activerehashing", "yes");
        map.put("appendonly", "yes");
        map.put("appendfsync", "everysec");
        map.put("hash-max-ziplist-entries", 512);
        map.put("hash-max-ziplist-value", 64);
        map.put("latency-monitor-threshold", 0);
        map.put("list-max-ziplist-entries", 512);
        map.put("list-max-ziplist-value", 64);
        map.put("maxclients", 65000);
        map.put("maxmemory-policy", "volatile-lru");
        map.put("maxmemory-samples", 3);
        map.put("min-slaves-max-lag", 10);
        map.put("min-slaves-to-write", 0);
        map.put("no-appendfsync-on-rewrite", "yes");
        map.put("notify-keyspace-events", "");
        map.put("repl-backlog-size", 1048576);
        map.put("repl-backlog-ttl", 3600);
        map.put("repl-timeout", 60);
        map.put("set-max-intset-entries", 512);
        map.put("slowlog-log-slower-than", -1);
        map.put("slowlog-max-len", 128);
        map.put("tcp-keepalive", 300);
        map.put("timeout", 0);
        map.put("port", 6379);
        map.put("zset-max-ziplist-entries", 128);
        map.put("zset-max-ziplist-value", 64);
        map.put("lua-time-limit", 5000);
        map.put("web.console.enabled", false);
        map.put("web.console.username", "admin");
        return map;
    }
    /**
     * 返回 分布式内存数据库 的conf信息
     *
     * @return CreateConf
     */
    public static CreateConf getConf01004(){
        CreateConf createConf = new CreateConf();
        createConf.setVersion("appv-e97gz6wz");
        createConf.setEnv(getEnv01004());
        return createConf;
    }

    /**
     * 返回 全文数据库 的env信息
     *
     * @return Map
     */
    public static  Map getEnv01006() {
        Map map = new HashMap<>();
        map.put("es_node.action.destructive_requires_name", true);
        map.put("es_node.discovery.zen.no_master_block", "write");
        map.put("es_node.gateway.recover_after_time", "5m");
        map.put("es_node.http.cors.enabled", false);
        map.put("es_node.http.cors.allow-origin", "");
        map.put("es_node.indices.fielddata.cache.size", "90%");
        map.put("es_node.indices.memory.index_buffer_size", "10%");
        map.put("es_node.indices.queries.cache.size", "10%");
        map.put("es_node.indices.requests.cache.size", "1%");
        map.put("es_node.node.attr.data", "hot");
        map.put("es_node.script.allowed_types", "");
        map.put("es_node.script.allowed_contexts", "");
        map.put("es_node.reindex.remote.whitelist", "");
        map.put("es_node.remote_ext_dict", "");
        map.put("es_node.remote_ext_stopwords", "");
        map.put("es_node.path.repo", "[]");
        map.put("es_node.repositories.url.allowed_urls", "[]");
        map.put("es_node.es_additional_line1", "");
        map.put("es_node.es_additional_line2", "");
        map.put("es_node.es_additional_line3", "");
        map.put("es_node.logger.action.level", "info");
        map.put("es_node.rootLogger.level", "info");
        map.put("es_node.logger.deprecation.level", "warn");
        map.put("es_node.logger.index_search_slowlog_rolling.level", "trace");
        map.put("es_node.logger.index_indexing_slowlog.level", "trace");
        map.put("es_node.enable_heap_dump", false);
        map.put("es_node.heap_dump_path", "/data/elasticsearch/dump");
        map.put("es_node.clean_logs_older_than_n_days", 7);
        map.put("es_node.appctl.tcp_keepalive_intvl", 75);
        map.put("es_node.appctl.tcp_keepalive_probes", 9);
        map.put("es_node.appctl.tcp_keepalive_time", 7200);
        map.put("es_node_2.node.attr.data", "warm");
        map.put("es_node_3.node.attr.data", "cold");
        map.put("kbn_node.enable_elasticsearch_head", false);
        map.put("kbn_node.enable_elastichd", true);
        map.put("kbn_node.enable_cerebro", true);
        map.put("kbn_node.enable_elasticsearch_hq", false);
        map.put("kbn_node.enable_elasticsearch_sql", true);
        map.put("kbn_node.proxy.balance.policy", "roundrobin");
        map.put("kbn_node.proxy.connect.timeout", "5s");
        map.put("kbn_node.proxy.timeout", "60s");
        map.put("kbn_node.proxy.max.conn", 2000);
        map.put("kbn_node.nginx_client_max_body_size", "20m");
        map.put("kbn_node.server.basePath", "");
        map.put("kbn_node.server.rewriteBasePath", true);
        map.put("lst_node.config.reload.automatic", false);
        map.put("lst_node.config.reload.interval", "3s");
        map.put("lst_node.input_conf_content", "http { port => 9700 }");
        map.put("lst_node.filter_conf_content", "");
        map.put("lst_node.output_conf_content", "");
        map.put("lst_node.output_es_content", "");
        map.put("lst_node.gemfile_append_content", "");
        return map;
    }
    /**
     * 返回 全文数据库 的conf信息
     *
     * @return CreateConf
     */
    public static CreateConf getConf01006(){
        CreateConf createConf = new CreateConf();
        createConf.setVersion("appv-dy6k7pd0");
        createConf.setResource_group("custom");
        createConf.setEnv(getEnv01006());
        return createConf;
    }

    /**
     * 返回 图数据库 的conf信息
     *
     * @return CreateConf
     */
    public static CreateConf getConf01005(){
        CreateConf createConf = new CreateConf();
        createConf.setVersion("appv-ul7s3cvn");
//        createConf.setVersion("appv-ksw5ctiz");
        return createConf;
    }

    /**
     * 返回 流式计算 的env信息
     *
     * @return Map
     */
    public static  Map getEnv02002() {
        Map map = new HashMap<>();
        map.put("nimbus_childopts","-Xmx1024m");
        map.put("ui_childopts","-Xmx768m");
        map.put("logviewer_childopts","-Xmx128m");
        map.put("drpc_childopts","-Xmx768m");
        map.put("pacemaker_childopts","-Xmx1024m");
        map.put("supervisor_slots_ports","[6700,6701,6702,6703]");
        map.put("supervisor_childopts","-Xmx256m");
        map.put("worker_childopts","-Xmx%HEAP-MEM%m -Dsun.jnu.encoding=UTF-8 -Dfile.encoding=UTF-8 -XX:+PrintGCDetails -Xloggc:artifacts/gc.log -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=10 -XX:GCLogFileSize=1M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=artifacts/heapdump");
        map.put("zookeeper_session_timeout",20000);
        map.put("zookeeper_connection_timeout",15000);
        map.put("zookeeper_retry_times",5);
        map.put("zookeeper_retry_interval",1000);
        map.put("zookeeper_retry_intervalceiling_millis",30000);
        map.put("nimbus_task_timeout_secs",30);
        map.put("nimbus_supervisor_timeout_secs",60);
        map.put("logviewer_max_sum_worker_logs_size_mb",4096);
        map.put("logviewer_max_per_worker_logs_size_mb",2048);
        map.put("topology_workers",1);
        map.put("topology_eventlogger_executors",0);
        map.put("supervisor_worker_start_timeout_secs",120);
        map.put("supervisor_worker_timeout_secs",30);
        map.put("supervisor_shutdown_sleep_secs",3);
        map.put("supervisor_monitor_frequency_secs",3);
        map.put("supervisor_heartbeat_frequency_secs",5);
        map.put("worker_heap_memory_mb",768);
        return map;
    }
    /**
     * 返回 流式计算 的conf信息
     *
     * @return CreateConf
     */
    public static CreateConf getConf02002(){
        CreateConf createConf = new CreateConf();
        createConf.setVersion("appv-wvbdsw6a");
        createConf.setEnv(getEnv02002());
        return createConf;
    }


}
