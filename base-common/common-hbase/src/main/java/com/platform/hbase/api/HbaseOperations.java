package com.platform.hbase.api;

import org.apache.hadoop.hbase.client.Scan;


/**
 * @ClassName HbaseOperations
 * @Description Hbase的基本操作
 */
public interface HbaseOperations {


	/**
	 * Executes the given action against the specified table handling resource management.
	 * <p>
	 * Application exceptions thrown by the action object get propagated to the caller (can only be unchecked).
	 * Allows for returning a result object (typically a domain object or collection of domain objects).
	 *
	 * @param tableName the target table
	 * @param <T> action type
	 * @return the result object of the callback action, or null
	 */
	default <T> T execute(String tableName, TableCallback<T> mapper){return null;}


	default <T> T executeAsync(String tableName, AsyncTableCallback<T> mapper){return null;}

	/**
	 * Scans the target table, using the given column family.
	 * The content is processed row by row by the given action, returning a list of domain objects.
	 *
	 * @param tableName target table
	 * @param family column family
	 * @param <T> action type
	 * @return a list of objects mapping the scanned rows
	 */
	<T> T find(String tableName, String family, final ResultsExtractor<T> action);
	/**
	 * Scans the target table, using the given column family.
	 * The content is processed row by row by the given action, returning a list of domain objects.
	 *
	 * @param tableName target table
	 * @param family column family
	 * @param qualifier column qualifier
	 * @param <T> action type
	 * @return a list of objects mapping the scanned rows
	 */

	<T> T find(String tableName, String family, String qualifier, final ResultsExtractor<T> action);

	/**
	 * Scans the target table using the given {@link Scan} object. Suitable for maximum control over the scanning
	 * process.
	 * The content is processed row by row by the given action, returning a list of domain objects.
	 *
	 * @param tableName target table
	 * @param scan table scanner
	 * @param <T> action type
	 * @return a list of objects mapping the scanned rows
	 */


	<T> T find(String tableName, final Scan scan, final ResultsExtractor<T> action);





	/**
	 * Gets an individual row from the given table. The content is mapped by the given action.
	 *
	 * @param tableName target table
	 * @param rowName row name
	 * @param mapper row mapper
	 * @param <T> mapper type
	 * @return object mapping the target row
	 */
	<T> T get(String tableName, String rowName, final RowMapper<T> mapper);

	/**
	 * Gets an individual row from the given table. The content is mapped by the given action.
	 *
	 * @param tableName target table
	 * @param rowName row name
	 * @param familyName column family
	 * @param mapper row mapper
	 * @param <T> mapper type
	 * @return object mapping the target row
	 */
	<T> T get(String tableName, String rowName, String familyName, final RowMapper<T> mapper);

	/**
	 * Gets an individual row from the given table. The content is mapped by the given action.
	 *
	 * @param tableName target table
	 * @param rowName row name
	 * @param familyName family
	 * @param qualifier column qualifier
	 * @param mapper row mapper
	 * @param <T> mapper type
	 * @return object mapping the target row
	 */
	<T> T get(String tableName, final String rowName, final String familyName, final String qualifier, final RowMapper<T> mapper);

}
