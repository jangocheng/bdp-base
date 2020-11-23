package com.platform.finance.report.common.utils.excel;

import com.platform.finance.report.common.utils.excel.annotation.ExportBeanFieldAnnotation;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@SuppressWarnings("Duplicates")
public class ExportUtil<T> {

    public static CsvAppendData exportCsv(File targetFile) throws FileNotFoundException, UnsupportedEncodingException {
        final BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile), "UTF-8"));
        return new CsvAppendData() {
            private CSVPrinter csvPrinter = null;
            private Class tClass = null;
            private List<String> headers;
            private List<Field> orderedField = null;

            @Override
            public CsvAppendData appendData(List data) {
                if (CollectionUtils.isEmpty(headers)) {
                    tClass = data.get(0).getClass();
                    Field[] field = tClass.getDeclaredFields();
                    headers = getHeaderList(field);
                    try {
                        csvPrinter = CSVFormat.DEFAULT.withHeader(headers.toArray(new String[headers.size()])).print(bufferedWriter);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (orderedField == null && !CollectionUtils.isEmpty(data)) {
                    Object t = data.get(0);
                    Field[] fields = t.getClass().getDeclaredFields();
                    orderedField = getOrderedField(fields);
                }
                if (csvPrinter != null) {
                    try {
                        for (int i = 0; i < data.size(); i++) {
                            Object t = data.get(i);
                            List<Object> attributeValues = new ArrayList<>();
                            for (Field f : orderedField) {
                                f.setAccessible(true);
                                String attribute = f.getName();
                                String methodName = "get" + attribute.substring(0, 1).toUpperCase()
                                        + attribute.substring(1);
                                Method method = t.getClass().getMethod(methodName, new Class[]{});
                                attributeValues.add(method.invoke(t, new Object[]{}));
                            }
                            csvPrinter.printRecord(attributeValues);
                        }
                    } catch (NoSuchMethodException | IllegalAccessException | IOException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
                return this;
            }

            @Override
            public void close() {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public static <T> void exportCsv(File targetFile, List<T> data) throws IOException {
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile), "UTF-8"));
            if (!CollectionUtils.isEmpty(data)) {
                Class tClass = data.get(0).getClass();
                Field[] field = tClass.getDeclaredFields();
                List<String> headers = getHeaderList(field);
                CSVPrinter csvPrinter = CSVFormat.DEFAULT.withHeader(headers.toArray(new String[headers.size()])).print(bufferedWriter);
                for (int i = 0; i < data.size(); i++) {
                    T t = data.get(i);
                    Field[] fields = t.getClass().getDeclaredFields();
                    List<Object> attributeValues = new ArrayList<>();
                    for (Field f : getOrderedField(fields)) {
                        f.setAccessible(true);
                        String attribute = f.getName();
                        String methodName = "get" + attribute.substring(0, 1).toUpperCase()
                                + attribute.substring(1);
                        Method method = t.getClass().getMethod(methodName, new Class[]{});
                        attributeValues.add(method.invoke(t, new Object[]{}));
                    }
                    csvPrinter.printRecord(attributeValues);
                }
            }


        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        }
    }


    /**
     * @param sheetName
     * @param datas     数据行  注意：数据中的对象的属性顺序一定要和标题对应的顺序相同
     * @throws IOException
     */
    public static <T> Workbook exportXLSExcel(String sheetName, Class<T> tclass, List<T> datas) {
        SXSSFWorkbook wb = new SXSSFWorkbook(100);
        CellStyle cellStyle = null;
        List<String> headers = null;

        try {
            if (sheetName == null) {
                throw new Exception("工作区间不存在，生成EXCEL表格失败");
            }
            Sheet sheet = wb.createSheet(sheetName);
            //生成标题行
            int index = 0; //行数
            Row row = sheet.createRow(index);
            index++;

            Field[] field = tclass.getDeclaredFields();
            headers = getHeaderList(field);
            for (int i = 0; i < headers.size(); i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(headers.get(i));
            }
            //生成数据行
            for (int i = 0; i < datas.size(); i++) {
                row = sheet.createRow(index);
                T t = datas.get(i);
                Field[] fields = t.getClass().getDeclaredFields();
                List<Object> attributeValues = new ArrayList<>();
                for (Field f : getOrderedField(fields)) {
                    f.setAccessible(true);
                    String attribute = f.getName();
                    String methodName = "get" + attribute.substring(0, 1).toUpperCase()
                            + attribute.substring(1);
                    Method method = t.getClass().getMethod(methodName, new Class[]{});
                    attributeValues.add(method.invoke(t, new Object[]{}));

                }
                for (int j = 0; j < attributeValues.size(); j++) {
                    Cell cell = row.createCell(j);
                    if (attributeValues.get(j) == null) {
                        cell.setCellValue("");
                        continue;
                    }
                    cell.setCellValue(attributeValues.get(j).toString());
                }
                index++;
            }

            return wb;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wb;
    }

    public static void exportShouldRepayment(HttpServletRequest request, HttpServletResponse response, Workbook wb) {
        OutputStream os = null;
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition",
                    "attachment;fileName=report.xls");
            os = response.getOutputStream();
            wb.write(os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static List<Field> getOrderedField(Field[] fields) {
        // 用来存放所有的属性域
        List<Field> fieldList = new ArrayList<>();
        // 过滤带有注解的Field
        for (Field f : fields) {
            if (f.getAnnotation(ExportBeanFieldAnnotation.class) != null) {
                fieldList.add(f);
            }
        }
        // 这个比较排序的语法依赖于java 1.8
        fieldList.sort(Comparator.comparingInt(
                m -> m.getAnnotation(ExportBeanFieldAnnotation.class).sort()
        ));
        return fieldList;
    }

    private static List<String> getHeaderList(Field[] fields) {
        //用来存放表头的字段
        List<String> list = new ArrayList<>();
        //用来存放排序后的属性
        List<Field> fieldList = getOrderedField(fields);

        for (Field field : fieldList) {
            list.add(field.getAnnotation(ExportBeanFieldAnnotation.class).title());
        }
        return list;
    }


    public interface CsvAppendData {
        CsvAppendData appendData(List data);

        void close();
    }

}

