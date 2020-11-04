package com.platform.hbase.api;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.AsyncConnection;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.hadoop.hbase.util.FutureUtils.addListener;

/**
 * @ClassName HbaseConectionManager
 * @Description
 */
public class HbaseConectionManager {

    private static final Logger LOGGER = LogManager.getLogger(HbaseConectionManager.class);

    private volatile Connection connection = null;

	private final AtomicReference<CompletableFuture<AsyncConnection>> future =
			new AtomicReference<>();

	public  Connection getConnection(Configuration configuration) {
		checkNotNull(configuration);
        if (null == this.connection) {
            synchronized (this) {
            	if (this.connection!=null){
					if (!this.connection.isClosed() && !this.connection.isAborted()) {
						return connection;
					}
				}

				try{
					IOUtils.closeQuietly(connection);
				}catch (Exception e){
					LOGGER.error("hbase connection close error！",e);
				}

				try {
					this.connection = ConnectionFactory.createConnection(configuration);
				} catch (IOException e) {
					LOGGER.error("hbase connection创建失败",e);
				}

            }
        }
        return this.connection;
	}


	public CompletableFuture<AsyncConnection> getConn(Configuration configuration) {
		CompletableFuture<AsyncConnection> f = future.get();
		if (f != null) {
			return f;
		}
		for (;;) {
			if (future.compareAndSet(null, new CompletableFuture<>())) {
				CompletableFuture<AsyncConnection> toComplete = future.get();
				addListener(ConnectionFactory.createAsyncConnection(configuration),(conn,error) -> {
					if (error != null) {
						toComplete.completeExceptionally(error);
						future.set(null);
						return;
					}
					toComplete.complete(conn);
				});
				return toComplete;
			} else {
				f = future.get();
				if (f != null) {
					return f;
				}
			}
		}
	}


	@PreDestroy
	public void destroy() {
		try {
			IOUtils.closeQuietly(connection);
			closeConn().get();
		} catch (Exception e) {
			LOGGER.error("destroy method hbase connection close error！",e);
		}
	}

	private CompletableFuture<Void> closeConn() {
		CompletableFuture<AsyncConnection> f = future.get();
		if (f == null) {
			return CompletableFuture.completedFuture(null);
		}
		CompletableFuture<Void> closeFuture = new CompletableFuture<>();
		addListener(f, (conn, error) -> {
			if (error == null) {
				IOUtils.closeQuietly(conn);
			}
			closeFuture.complete(null);
		});
		return closeFuture;
	}


}