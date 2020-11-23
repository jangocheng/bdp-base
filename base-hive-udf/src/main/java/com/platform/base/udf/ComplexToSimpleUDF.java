package com.platform.base.udf;

import com.github.houbb.opencc4j.util.ZhConverterUtil;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.StringObjectInspector;


public class ComplexToSimpleUDF extends GenericUDF {


    StringObjectInspector elementOI;

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
        if (arguments.length != 1) {
            throw new UDFArgumentLengthException("complexToSimple only takes 1 arguments");
        }

        ObjectInspector param = arguments[0];
        if (!(param instanceof StringObjectInspector)) {
            throw new UDFArgumentException("argument must be a string");
        }

        this.elementOI = (StringObjectInspector)param;
        //返回类型String
        return  PrimitiveObjectInspectorFactory.javaStringObjectInspector;
    }


    /**
     * 这个方法类似evaluate方法，处理真实的参数，返回最终结果.
     * @param arguments
     * @return
     * @throws HiveException
     */
    @Override
    public Object evaluate(DeferredObject[] arguments) throws HiveException {
        String arg = elementOI.getPrimitiveJavaObject(arguments[0].get());
        if (arg==null){
            return null;
        }
        String r = ZhConverterUtil.convertToSimple(arg);
        return r;
    }

    /**
     * 此方法用于当实现的GenericUDF出错的时候，
     * 打印提示信息，提示信息就是该方法的返回的字符串
     * @param children
     * @return
     */
    @Override
    public String getDisplayString(String[] children) {
        return "Usage: complexToSimple(String str)";
    }
}
