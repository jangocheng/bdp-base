package com.platform.base.udf;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde.serdeConstants;
import org.apache.hadoop.hive.serde2.objectinspector.ListObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

import java.util.LinkedHashMap;
import java.util.List;

public class OneToOneToMap extends GenericUDF {
    private final LinkedHashMap<Object, Object> ret = new LinkedHashMap<Object, Object>();
    private ListObjectInspector keys;
    private ListObjectInspector values;

    /**
     * 这个方法只调用一次，并且在evaluate()方法之前调用，
     * 该方法接收的参数是一个ObjectInspectors数组，
     * 该方法检查接收正确的参数类型和参数个数
     * @param arguments
     * @return
     * @throws UDFArgumentException
     */
    @Override
    public ObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException {
        if (arguments.length != 2) {
            throw new UDFArgumentLengthException("oneToOneToMap only takes 2 arguments");
        }

        ObjectInspector keysData = arguments[0];
        ObjectInspector valuesData = arguments[1];

        if (keysData instanceof ListObjectInspector && valuesData instanceof ListObjectInspector) {
            keys = (ListObjectInspector) keysData;
            values = (ListObjectInspector) valuesData;
        } else {
            throw new UDFArgumentTypeException(0, "Argument must be array! "
                    + serdeConstants.LIST_TYPE_NAME + ", but "
                    + keysData.getTypeName() + " and " + valuesData.getTypeName() + " were not found.");
        }


        //返回值类型
        return ObjectInspectorFactory.getStandardMapObjectInspector(
                PrimitiveObjectInspectorFactory.javaStringObjectInspector,
                PrimitiveObjectInspectorFactory.javaStringObjectInspector);
    }


    /**
     * 这个方法类似evaluate方法，处理真实的参数，返回最终结果.
     *
     * @param arguments
     * @return
     * @throws HiveException
     */
    @Override
    public Object evaluate(DeferredObject[] arguments) throws HiveException {
        this.ret.clear();
        List<?> keysList = keys.getList(arguments[0].get());
        List<?> valuesList = values.getList(arguments[1].get());

        if (null == keysList || null == valuesList) {
            throw new IllegalArgumentException("参数不能为null");
        }

        //判断是否包含
        if (keysList.size() != valuesList.size()) {
            throw new IllegalArgumentException("keys size not equals values size,keys:" + keysList.size() + "\t values:" + valuesList.size());
        }

        for (int i = 0; i < keysList.size(); i++) {
            this.ret.put(keysList.get(i), valuesList.get(i));
        }
        return this.ret;

    }


    /**
     * 此方法用于当实现的GenericUDF出错的时候，
     * 打印提示信息，提示信息就是该方法的返回的字符串
     *
     * @param children
     * @return
     */
    @Override
    public String getDisplayString(String[] children) {
        return getStandardDisplayString("oneToOneToMap", children, ",");
    }

}
