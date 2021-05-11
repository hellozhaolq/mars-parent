package com.zhaolq.mars.tool.core.utils;

import cn.hutool.core.io.FileUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 文件工具类
 *
 * @author zhaolq
 * @date 2020/11/12 11:25
 */
public class FileUtils extends FileUtil {

    /**
     * 下载文件
     *
     * @param response
     * @param file
     * @param newFileName
     */
    public static void downloadFile(HttpServletResponse response, File file, String newFileName) {
        try {
            response.setHeader("Content-Disposition", "attachment; filename=" + new String(newFileName.getBytes("UTF-8"), "UTF-8"));
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
            InputStream is = new FileInputStream(file.getAbsolutePath());
            BufferedInputStream bis = new BufferedInputStream(is);
            int length = 0;
            byte[] temp = new byte[1 * 1024 * 10];
            while ((length = bis.read(temp)) != -1) {
                bos.write(temp, 0, length);
            }
            bos.flush();
            bis.close();
            bos.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
