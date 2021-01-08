package com.zhaolq.common.export;


import org.apache.poi.ss.usermodel.Workbook;
import java.io.*;

/**
 * Apache POI相关操作
 *      https://zh.wikipedia.org/wiki/Apache_POI
 * @author zhaolq
 * @date 2020/11/12 11:32
 */
public class PoiUtils {

    /**
     * 生成Excel文件
     *
     * @param workbook
     * @param fileName
     * @return java.io.File
     */
    public static File createExcelFile(Workbook workbook, String fileName) {
        OutputStream stream = null;
        File file = null;
        try {
            file = File.createTempFile(fileName, ".xlsx");
            stream = new FileOutputStream(file.getAbsoluteFile());
            workbook.write(stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(workbook);
            IOUtils.closeQuietly(stream);
        }
        return file;
    }

}
