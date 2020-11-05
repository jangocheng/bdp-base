package com.platform.hbase.api;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Threads;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.Assert;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.apache.hadoop.hbase.util.FutureUtils.addListener;


/**
 * @author wlhbdp
 * @ClassName: HbaseAsyncTemplate
 * @Description: HbaseAsyncTemplate
 *
 */
public class HbaseAsyncTemplate extends AbstractHbaseOperations{

    private static final Logger LOGGER = LogManager.getLogger(HbaseAsyncTemplate.class);

    private Configuration configuration;

    private volatile CompletableFuture<AsyncConnection> connection;

    private static final int THREAD_POOL_SIZE = 16;

    public HbaseAsyncTemplate(Configuration configuration) {
        this.setConfiguration(configuration);
        this.connection = getConnection();
        Assert.notNull(configuration, " a valid configuration is required");
    }


    ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE,
            Threads.newDaemonThreadFactory("hbase-pool-async"));


    public CompletableFuture<AsyncConnection> getConnection() {
        return new HbaseConectionManager().getConn(this.configuration);
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }


    @Override
    public <T> T find(String tableName, Scan scan, ResultsExtractor<T> action) {
        return this.executeAsync(tableName,table->{
            int caching = scan.getCaching();
            // 如果caching未设置(默认是1)，将默认配置成5000
            if (caching == 1) {
                scan.setCaching(5000);
            }
            ResultScanner scanner = table.getScanner(scan);
            try {
                return action.extractData(scanner);
            } finally {
                scanner.close();
            }

        });
    }

    @Override
    public <T> T get(String tableName, String rowName, String familyName, String qualifier, RowMapper<T> mapper) {
        return this.executeAsync(tableName, table -> {
            Get get = getHbaseGet(familyName,qualifier,rowName);
            CompletableFuture<Result> result = table.get(get);
            return mapper.mapRow(result.get(), 0);
        });
    }


    @Override
    public <T> T executeAsync(String tableName, AsyncTableCallback<T> action){
        Assert.notNull(action, "Callback object must not be null");
        Assert.notNull(tableName, "No table specified");
        CompletableFuture<T> completableFuture = new CompletableFuture<>();
        addListener(connection,(conn,error) ->{
            if (error != null) {
                LOGGER.warn("failed to get async connection for executeAsync", error);
                completableFuture.completeExceptionally(error);
            }else{
                try{
                    AsyncTable<?> table = conn.getTable(TableName.valueOf(tableName), threadPool);
                    T result = action.doInTable(table);
                    completableFuture.complete(result);
                }catch (Throwable throwable){
                    LOGGER.error("failed to execute");
                    completableFuture.completeExceptionally(throwable);
                }
            }
        });

        T result;
        try {
            result = completableFuture.get();
        }catch (Exception e){
            throw new HbaseSystemException(e);
        }
        return result;
    }


    /**
     * 异步插入集合数据到Hbase
     * @param tableName
     * @param puts
     * @return
     */
    public CompletableFuture<Boolean> addPuts(String tableName,final List<Put> puts) throws Exception{
        CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();
        addListener(connection,(conn,error)->{
            boolean flag = false;
            if (error != null) {
                LOGGER.error("failed to get async connection for addPuts", error);
                completableFuture.completeExceptionally(error);
            }else {
                try{
                    AsyncTable<?> table = conn.getTable(TableName.valueOf(tableName), threadPool);
                    table.put(puts);
                    flag = true;
                }
                catch (Exception e){
                    LOGGER.error("failed to add puts");
                    completableFuture.completeExceptionally(e);
                }
                completableFuture.complete(flag);
            }
        });
        return completableFuture;
    }
}
