package com.platform.finance.report.common.utils.excel;


import com.platform.finance.report.common.utils.excel.annotation.ImportBeanFieldAnnotation;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.assertj.core.util.Lists;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

/**
 * 导入Excel文件（支持“XLS”和“XLSX”格式）
 * @author wlhbdp
 * @create 2020-01-05 下午1:23
 **/
@SuppressWarnings("all")
@Log4j2
public class ImportExcel {

    /**
     * 工作薄对象
     */
    private Workbook wb;

    /**
     * 工作表对象
     */
    private Sheet sheet;

    /**
     * 标题行号
     */
    private int headerNum;

    /**
     * 构造函数
     * @param fileName 导入文件，读取第一个工作表
     * @param headerNum 标题行号，数据行号=标题行号+1
     * @throws InvalidFormatException
     * @throws IOException
     */
    public ImportExcel(String fileName, int headerNum)
            throws InvalidFormatException, IOException {
        this(new File(fileName), headerNum);
    }

    /**
     * 构造函数
     * @param file 导入文件对象，读取第一个工作表
     * @param headerNum 标题行号，数据行号=标题行号+1
     * @throws InvalidFormatException
     * @throws IOException
     */
    public ImportExcel(File file, int headerNum)
            throws InvalidFormatException, IOException {
        this(file, headerNum, 0);
    }

    /**
     * 构造函数
     * @param fileName 导入文件
     * @param headerNum 标题行号，数据行号=标题行号+1
     * @param sheetIndex 工作表编号
     * @throws InvalidFormatException
     * @throws IOException
     */
    public ImportExcel(String fileName, int headerNum, int sheetIndex)
            throws InvalidFormatException, IOException {
        this(new File(fileName), headerNum, sheetIndex);
    }

    /**
     * 构造函数
     * @param file 导入文件对象
     * @param headerNum 标题行号，数据行号=标题行号+1
     * @param sheetIndex 工作表编号
     * @throws InvalidFormatException
     * @throws IOException
     */
    public ImportExcel(File file, int headerNum, int sheetIndex)
            throws InvalidFormatException, IOException {
        this(file.getName(), new FileInputStream(file), headerNum, sheetIndex);
    }

    /**
     * 构造函数
     * @param multipartFile 导入文件对象
     * @param headerNum 标题行号，数据行号=标题行号+1
     * @param sheetIndex 工作表编号
     * @throws InvalidFormatException
     * @throws IOException
     */
    public ImportExcel(MultipartFile multipartFile, int headerNum, int sheetIndex)
            throws InvalidFormatException, IOException {
        this(multipartFile.getOriginalFilename(), multipartFile.getInputStream(), headerNum, sheetIndex);
    }

    /**
     * 构造函数
     * @param fileName 导入文件对象
     * @param headerNum 标题行号，数据行号=标题行号+1
     * @param sheetIndex 工作表编号
     * @throws InvalidFormatException
     * @throws IOException
     */
    public ImportExcel(String fileName, InputStream is, int headerNum, int sheetIndex)
            throws InvalidFormatException, IOException {
        if (StringUtils.isBlank(fileName)){
            throw new RuntimeException("导入文档为空!");
        }else if(fileName.toLowerCase().endsWith("xls")){
            this.wb = new HSSFWorkbook(is);
        }else if(fileName.toLowerCase().endsWith("xlsx")){
            this.wb = new XSSFWorkbook(is);
        }else{
            throw new RuntimeException("文档格式不正确!");
        }
        if (this.wb.getNumberOfSheets()<sheetIndex){
            throw new RuntimeException("文档中没有工作表!");
        }
        this.sheet = this.wb.getSheetAt(sheetIndex);
        this.headerNum = headerNum;
        log.debug("Initialize success.");
    }

    /**
     * 获取行对象
     * @param rownum
     * @return
     */
    public Row getRow(int rownum){
        return this.sheet.getRow(rownum);
    }

    /**
     * 获取数据行号
     * @return
     */
    public int getDataRowNum(){
        return headerNum+1;
    }

    /**
     * 获取最后一个数据行号
     * @return
     */
    public int getLastDataRowNum(){
        return this.sheet.getLastRowNum();
    }

    /**
     * 获取最后一个列号
     * @return
     */
    public int getLastCellNum(){
        return this.getRow(headerNum).getLastCellNum();
    }

    /**
     * 获取单元格值
     * @param row 获取的行
     * @param column 获取单元格列号
     * @return 单元格值
     */
    public Object getCellValue(Row row, int column){
        Object val = "";
        try{
            Cell cell = row.getCell(column);
            if (cell != null){
                if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
                    val = cell.getNumericCellValue();
                }else if (cell.getCellType() == Cell.CELL_TYPE_STRING){
                    val = cell.getStringCellValue();
                }else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA){
                    val = cell.getCellFormula();
                }else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN){
                    val = cell.getBooleanCellValue();
                }else if (cell.getCellType() == Cell.CELL_TYPE_ERROR){
                    val = cell.getErrorCellValue();
                }
            }
        }catch (Exception e) {
            return val;
        }
        return val;
    }

    /**
     * 获取导入数据列表
     * @param cls 导入对象类型
     * @param groups 导入分组
     */
    public <E> List<E> getDataList(Class<E> cls) throws Exception {
        List<Object[]> annotationList = Lists.newArrayList();
        // Get annotation field
        Field[] fs = cls.getDeclaredFields();
        // 用来存放所有带注解的属性域
        List<Field> fieldList = new ArrayList<>();
        for(Field f:fs){
            if(f.getAnnotation(ImportBeanFieldAnnotation.class)!=null){
                fieldList.add(f);
            }
        }

        for (Field f : fieldList){
            ImportBeanFieldAnnotation ef = f.getAnnotation(ImportBeanFieldAnnotation.class);
            annotationList.add(new Object[]{ef, f});
        }

        // Field sorting
        Collections.sort(annotationList, new Comparator<Object[]>() {
            @Override
            public int compare(Object[] o1, Object[] o2) {
                return new Integer(((ImportBeanFieldAnnotation)o1[0]).sort()).compareTo(
                        new Integer(((ImportBeanFieldAnnotation)o2[0]).sort()));
            };
        });

        //log.debug("Import column count:"+annotationList.size());
        // Get excel data
        List<E> dataList = Lists.newArrayList();
        for (int i = this.getDataRowNum(); i <= this.getLastDataRowNum(); i++) {
            E e = (E)cls.newInstance();
            int column = 0;
            Row row = this.getRow(i);
            StringBuilder sb = new StringBuilder();
            for (Object[] os : annotationList){
                Object val = this.getCellValue(row, column++);
                if (val != null){
                    ImportBeanFieldAnnotation ef = (ImportBeanFieldAnnotation)os[0];
                    // Get param type and type cast
                    Class<?> valType = Class.class;
                    if (os[1] instanceof Field){
                        valType = ((Field)os[1]).getType();
                    }
                    try {
                        if (valType == String.class){
                            String s = String.valueOf(val.toString());
                            if(StringUtils.endsWith(s, ".0")){
                                val = StringUtils.substringBefore(s, ".0");
                            }else{
                                val = String.valueOf(val.toString());
                            }
                        }else if (valType == Integer.class){
                            val = Double.valueOf(val.toString()).intValue();
                        }else if (valType == Long.class){
                            val = Double.valueOf(val.toString()).longValue();
                        }else if (valType == Double.class){
                            val = Double.valueOf(val.toString());
                        }else if (valType == Float.class){
                            val = Float.valueOf(val.toString());
                        }else if (valType == Date.class){
                            val = DateUtil.getJavaDate((Double)val);
                        }else if(valType == BigDecimal.class){
                            val = new BigDecimal(val.toString());
                        }
                    } catch (Exception ex) {
                        log.info("Get cell value ["+i+","+column+"] error: " + ex.toString());
                        throw new Exception("第"+i+1+"行，第"+column+"列，存在错误格式数据，请核对后再次尝试导入");
                    }
                    // set entity value
                    if (os[1] instanceof Field){
                        Reflections.invokeSetter(e, ((Field)os[1]).getName(), val);
                    }
                }
                sb.append(val+", ");
            }
            dataList.add(e);
            log.debug("Read success: ["+i+"] "+sb.toString());
        }
        return dataList;
    }

}
