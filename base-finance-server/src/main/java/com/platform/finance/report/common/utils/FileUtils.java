package com.platform.finance.report.common.utils;

import com.platform.finance.report.common.annotation.FileNameAnnotation;
import com.platform.finance.report.common.constant.FileConstants;
import com.platform.finance.report.common.utils.excel.ExportUtil;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @author wlhbdp
 * @date 2020/9/4 23:48
 * @description 文件处理方法
 */
public class FileUtils {

    /**
     * 获取文件名
     *
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> String getFileName(Class<T> cls) {
        return cls.getAnnotation(FileNameAnnotation.class).value();
    }

    /**
     * 文件下载
     *
     * @param cls
     * @param list
     * @param response
     * @param <T>
     */
    public static <T> void downFile(Class<T> cls, List<T> list, HttpServletResponse response) {
        String fileName = getFileName(cls);
        Workbook wb = ExportUtil.exportXLSExcel(fileName, cls, list);
        response.setCharacterEncoding(FileConstants.CHARACTER_ENCODING_UTF);
        response.setContentType(FileConstants.CONTENT_TYPE);
        response.addHeader(FileConstants.CONTENT_DISPOSITION, FileConstants.ATTACHMENT + fileName + FileConstants.SEPARATOR + FileConstants.FILE_SUFFIX_XLS);
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            wb.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
