package com.platform.base.udf;

import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.JavaStringObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;


public class IdentityToAgeTest {
    public static void main(String [] args){
        try {
            testComplexUDFReturnsCorrectValues();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void testComplexUDFReturnsCorrectValues() throws HiveException {

        // 建立需要的模型
        IdentityToAge example = new IdentityToAge();
        //输入参数类型
        ObjectInspector stringOI = PrimitiveObjectInspectorFactory.javaStringObjectInspector;
        //输出参数类型
        JavaStringObjectInspector resultInspector = (JavaStringObjectInspector) example.initialize(new ObjectInspector[]{stringOI});


        // 测试结果
        String value = "433127199301204532";

        // 存在的值
        Object result = example.evaluate(new GenericUDF.DeferredObject[]{new GenericUDF.DeferredJavaObject(value)});
        System.out.println(resultInspector.getPrimitiveJavaObject(result));

    }
}
