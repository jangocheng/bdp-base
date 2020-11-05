package com.platform.hbase.api;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wlhbdp
 * @ClassName: AbstractHbaseOperations
 * @Description: hbase操作抽象类
 *
 */
public abstract class AbstractHbaseOperations implements HbaseOperations{


    @Override
    public <T> T find(String tableName, String family, final ResultsExtractor<T> action) {
        Scan scan = new Scan();
        scan.addFamily(family.getBytes(Charset.forName("UTF-8")));
        return find(tableName, scan, action);
    }


    @Override
    public <T> T find(String tableName, String family, String qualifier, final ResultsExtractor<T> action) {
        Scan scan = new Scan();
        scan.addColumn(family.getBytes(Charset.forName("UTF-8")), qualifier.getBytes(Charset.forName("UTF-8")));
        return find(tableName, scan, action);
    }

    @Override
    public <T> T get(String tableName, String rowName, final RowMapper<T> mapper) {
        return this.get(tableName, rowName, null, null, mapper);
    }

    @Override
    public <T> T get(String tableName, String rowName, String familyName, final RowMapper<T> mapper) {
        return this.get(tableName, rowName, familyName, null, mapper);
    }


    /**
     * 通过表名和key获取一行数据
     * @param tableName
     * @param rowName
     * @return
     */
    public Map<String, Object> get(String tableName, String rowName) {
        return this.get(tableName, rowName, (result, rowNum) -> {
            List<Cell> ceList =   result.listCells();
            Map<String,Object> map = new HashMap<>();
            if(ceList!=null&&ceList.size()>0){
                for(Cell cell:ceList){
                    map.put(Bytes.toString(cell.getFamilyArray(),cell.getFamilyOffset(),cell.getFamilyLength())+
                                    "_"+Bytes.toString( cell.getQualifierArray(),cell.getQualifierOffset(),cell.getQualifierLength()),
                            Bytes.toString( cell.getValueArray(), cell.getValueOffset(), cell.getValueLength()));
                }
            }
            return  map;
        });
    }


    /**
     * 返回HbaseGet 对象
      * @param familyName
     * @param qualifier
     * @param rowName
     * @return
     */
    public Get getHbaseGet(String familyName, String qualifier, String rowName) {
        Get get = new Get(Bytes.toBytes(rowName));
        if (StringUtils.isNotBlank(familyName)) {
            byte[] family = Bytes.toBytes(familyName);
            if (StringUtils.isNotBlank(qualifier)) {
                get.addColumn(family, Bytes.toBytes(qualifier));
            }
            else {
                get.addFamily(family);
            }
        }
        return get;
    }


    /**
     * 通过表名和key和rowName获取一行数据
     * @param tableName
     * @param rowName
     * @return
     */
    public Map<String, Object> get(String tableName, String rowName, String familyName) {
        return this.get(tableName, rowName,familyName, (result, rowNum) -> {
            List<Cell> ceList =   result.listCells();
            Map<String,Object> map = new HashMap<>();
            if(ceList!=null&&ceList.size()>0){
                for(Cell cell:ceList){
                    map.put(Bytes.toString( cell.getQualifierArray(),cell.getQualifierOffset(),cell.getQualifierLength()),
                            Bytes.toString( cell.getValueArray(), cell.getValueOffset(), cell.getValueLength()));
                }
            }
            return  map;
        });
    }


    /**
     * 通过表名  key 和 列族 和列 获取一个数据
     * @param tableName
     * @param rowName
     * @param familyName
     * @param qualifier
     * @return
     */
    public String get(String tableName ,String rowName, String familyName, String qualifier) {
        return this.get(tableName, rowName,familyName,qualifier , (result, rowNum) -> {
            List<Cell> ceList =   result.listCells();
            String res = "";
            if(ceList!=null&&ceList.size()>0){
                for(Cell cell:ceList){
                    res = Bytes.toString( cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                }
            }
            return res;
        });
    }


    public <T> List<T> find(String tableName, String family, final RowMapper<T> action) {
        Scan scan = new Scan();
        scan.addFamily(family.getBytes(Charset.forName("UTF-8")));
        return find(tableName, scan, action);
    }


    public <T> List<T> find(String tableName, String family, String qualifier, final RowMapper<T> action) {
        Scan scan = new Scan();
        scan.addColumn(family.getBytes(Charset.forName("UTF-8")), qualifier.getBytes(Charset.forName("UTF-8")));
        return find(tableName, scan, action);
    }


    public <T> List<T> find(String tableName, final Scan scan, final RowMapper<T> action) {
        return find(tableName, scan, new RowMapperResultsExtractor<T>(action));
    }


}
