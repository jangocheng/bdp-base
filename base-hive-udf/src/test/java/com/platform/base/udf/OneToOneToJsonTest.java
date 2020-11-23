package com.platform.base.udf;

import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.StandardMapObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OneToOneToJsonTest {
    public static void main(String[] args) {
        try {
            testOneToOneToMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testOneToOneToMap() throws HiveException {

        // 建立需要的模型
        OneToOneToMap example = new OneToOneToMap();
        //输入参数类型
        ObjectInspector stringOI = PrimitiveObjectInspectorFactory.javaStringObjectInspector;
        ObjectInspector listOI = ObjectInspectorFactory.getStandardListObjectInspector(stringOI);
        //输出参数类型
        //输出参数类型
        StandardMapObjectInspector resultInspector = (StandardMapObjectInspector) example.initialize(new ObjectInspector[]{listOI, listOI});


        // 测试结果
        List<String> keys = new ArrayList<String>();
        keys.add("");
        keys.add("");
        keys.add("");

        List<String> values = new ArrayList<String>();
        values.add("a");
        values.add("b");
        values.add("c");


        // 存在的值
        Object result = example.evaluate(new GenericUDF.DeferredObject[]{new GenericUDF.DeferredJavaObject(keys),
                new GenericUDF.DeferredJavaObject(null)});

        System.out.println(result);

    }
}
