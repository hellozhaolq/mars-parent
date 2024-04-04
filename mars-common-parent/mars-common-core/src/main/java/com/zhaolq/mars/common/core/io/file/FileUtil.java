package com.zhaolq.mars.common.core.io.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.tika.Tika;

import com.zhaolq.mars.common.core.constant.CharPool;
import com.zhaolq.mars.common.core.constant.StringPool;
import com.zhaolq.mars.common.core.exception.IORuntimeException;
import com.zhaolq.mars.common.core.exception.UtilException;
import com.zhaolq.mars.common.core.io.IoUtil;
import com.zhaolq.mars.common.core.util.ArrayUtil;
import com.zhaolq.mars.common.core.util.CharsetUtil;
import com.zhaolq.mars.common.core.util.ClassLoaderUtil;

/**
 * 文件工具类
 *
 * @Author zhaolq
 * @Date 2023/6/13 17:13:16
 */
public class FileUtil extends PathUtil {

    /**
     * Class文件扩展名
     */
    public static final String CLASS_EXT = StringPool.EXT_CLASS;
    /**
     * Jar文件扩展名
     */
    public static final String JAR_FILE_EXT = StringPool.EXT_JAR;
    /**
     * 在Jar中的路径jar的扩展名形式
     */
    public static final String JAR_PATH_EXT = ".jar!";
    /**
     * 当Path为文件形式时, path会加入一个表示文件的前缀
     */
    public static final String PATH_FILE_PRE = StringPool.FILE_URL_PREFIX;
    /**
     * 文件路径分隔符<br>
     * 在Unix和Linux下 是{@code '/'}; 在Windows下是 {@code '\'}
     */
    public static final String FILE_SEPARATOR = File.separator;
    /**
     * 多个PATH之间的分隔符<br>
     * 在Unix和Linux下 是{@code ':'}; 在Windows下是 {@code ';'}
     */
    public static final String PATH_SEPARATOR = File.pathSeparator;
    /**
     * 绝对路径判断正则
     */
    private static final Pattern PATTERN_PATH_ABSOLUTE = Pattern.compile("^[a-zA-Z]:([/\\\\].*)?");


    /**
     * 是否为Windows环境
     *
     * @return 是否为Windows环境
     */
    public static boolean isWindows() {
        return CharPool.BACKSLASH == File.separatorChar;
    }

    /**
     * 列出指定路径下的目录和文件<br>
     * 给定的绝对路径不能是压缩包中的路径
     *
     * @param path 目录绝对路径或者相对路径
     * @return 文件列表（包含目录）
     */
    public static File[] ls(String path) {
        if (path == null) {
            return null;
        }

        File file = file(path);
        if (file.isDirectory()) {
            return file.listFiles();
        }
        throw new IORuntimeException(String.format("Path [%s] is not directory!", path));
    }

    /**
     * 文件是否为空<br>
     * 目录：里面没有文件时为空 文件：文件大小为0时为空
     *
     * @param file 文件
     * @return 是否为空，当提供非目录时，返回false
     */
    public static boolean isEmpty(File file) {
        if (null == file || false == file.exists()) {
            return true;
        }

        if (file.isDirectory()) {
            String[] subFiles = file.list();
            return ArrayUtil.isEmpty(subFiles);
        } else if (file.isFile()) {
            return file.length() <= 0;
        }

        return false;
    }

    /**
     * 目录是否为空
     *
     * @param file 目录
     * @return 是否为空，当提供非目录时，返回false
     */
    public static boolean isNotEmpty(File file) {
        return false == isEmpty(file);
    }

    /**
     * 目录是否为空
     *
     * @param dir 目录
     * @return 是否为空
     */
    public static boolean isDirEmpty(File dir) {
        return isDirEmpty(dir.toPath());
    }

    /**
     * 递归遍历目录以及子目录中的所有文件<br>
     * 如果提供file为文件，直接返回过滤结果
     *
     * @param path 当前遍历文件或目录的路径
     * @param fileFilter 文件过滤规则对象，选择要保留的文件，只对文件有效，不过滤目录
     * @return 文件列表
     */
    public static List<File> loopFiles(String path, FileFilter fileFilter) {
        return loopFiles(file(path), fileFilter);
    }

    /**
     * 递归遍历目录以及子目录中的所有文件<br>
     * 如果提供file为文件，直接返回过滤结果
     *
     * @param file 当前遍历文件或目录
     * @param fileFilter 文件过滤规则对象，选择要保留的文件，只对文件有效，不过滤目录
     * @return 文件列表
     */
    public static List<File> loopFiles(File file, FileFilter fileFilter) {
        return loopFiles(file, -1, fileFilter);
    }

    /**
     * 递归遍历目录并处理目录下的文件，可以处理目录或文件：
     * <ul>
     *     <li>非目录则直接调用{@link Consumer}处理</li>
     *     <li>目录则递归调用此方法处理</li>
     * </ul>
     *
     * @param file 文件或目录，文件直接处理
     * @param consumer 文件处理器，只会处理文件
     */
    public static void walkFiles(File file, Consumer<File> consumer) {
        if (file.isDirectory()) {
            final File[] subFiles = file.listFiles();
            if (ArrayUtil.isNotEmpty(subFiles)) {
                for (File tmp : subFiles) {
                    walkFiles(tmp, consumer);
                }
            }
        } else {
            consumer.accept(file);
        }
    }

    /**
     * 递归遍历目录以及子目录中的所有文件<br>
     * 如果提供file为文件，直接返回过滤结果
     *
     * @param file 当前遍历文件或目录
     * @param maxDepth 遍历最大深度，-1表示遍历到没有目录为止
     * @param fileFilter 文件过滤规则对象，选择要保留的文件，只对文件有效，不过滤目录，null表示接收全部文件
     * @return 文件列表
     */
    public static List<File> loopFiles(File file, int maxDepth, FileFilter fileFilter) {
        return loopFiles(file.toPath(), maxDepth, fileFilter);
    }

    /**
     * 递归遍历目录以及子目录中的所有文件<br>
     * 如果用户传入相对路径，则是相对classpath的路径<br>
     * 如："test/aaa"表示"${classpath}/test/aaa"
     *
     * @param path 相对ClassPath的目录或者绝对路径目录
     * @return 文件列表
     */
    public static List<File> loopFiles(String path) {
        return loopFiles(file(path));
    }

    /**
     * 递归遍历目录以及子目录中的所有文件
     *
     * @param file 当前遍历文件
     * @return 文件列表
     */
    public static List<File> loopFiles(File file) {
        return loopFiles(file, null);
    }

    /**
     * 创建File对象，相当于调用new File()，不做任何处理
     *
     * @param path 文件路径，相对路径表示相对项目路径
     * @return File
     */
    public static File newFile(String path) {
        return new File(path);
    }

    /**
     * 创建File对象，自动识别相对或绝对路径，相对路径将自动从ClassPath下寻找
     *
     * @param path 相对ClassPath的目录或者绝对路径目录
     * @return File
     */
    public static File file(String path) {
        if (null == path) {
            return null;
        }
        return new File(getAbsolutePath(path));
    }

    /**
     * 创建File对象<br>
     * 此方法会检查slip漏洞，漏洞说明见http://blog.nsfocus.net/zip-slip-2/
     *
     * @param parent 父目录
     * @param path 文件路径
     * @return File
     */
    public static File file(String parent, String path) {
        return file(new File(parent), path);
    }

    /**
     * 通过多层目录参数创建文件<br>
     * 此方法会检查slip漏洞，漏洞说明见http://blog.nsfocus.net/zip-slip-2/
     *
     * @param directory 父目录
     * @param names 元素名（多层目录名），由外到内依次传入
     * @return the file 文件
     */
    public static File file(File directory, String... names) {
        Validate.notNull(directory, "directory must not be null");
        if (ArrayUtil.isEmpty(names)) {
            return directory;
        }

        File file = directory;
        for (String name : names) {
            if (null != name) {
                file = file(file, name);
            }
        }
        return file;
    }

    /**
     * 通过多层目录创建文件
     * <p>
     * 元素名（多层目录名）
     *
     * @param names 多层文件的文件名，由外到内依次传入
     * @return the file 文件
     */
    public static File file(String... names) {
        if (ArrayUtil.isEmpty(names)) {
            return null;
        }

        File file = null;
        for (String name : names) {
            if (file == null) {
                file = file(name);
            } else {
                file = file(file, name);
            }
        }
        return file;
    }

    /**
     * 创建File对象
     *
     * @param uri 文件URI
     * @return File
     */
    public static File file(URI uri) {
        if (uri == null) {
            throw new NullPointerException("File uri is null!");
        }
        return new File(uri);
    }

    /**
     * 创建File对象
     *
     * @param url 文件URL
     * @return File
     */
    public static File file(URL url) {
        File file = null;
        try {
            file = new File(new URI(StringUtils.trim(url.toString())));
        } catch (URISyntaxException e) {
            throw new UtilException(e);
        }
        return file;
    }

    /**
     * 获取临时文件路径（绝对路径）
     *
     * @return 临时文件路径
     */
    public static String getTmpDirPath() {
        return System.getProperty("java.io.tmpdir");
    }

    /**
     * 获取临时文件目录
     *
     * @return 临时文件目录
     */
    public static File getTmpDir() {
        return file(getTmpDirPath());
    }

    /**
     * 获取用户路径（绝对路径）
     *
     * @return 用户路径
     */
    public static String getUserHomePath() {
        return System.getProperty("user.home");
    }

    /**
     * 获取用户目录
     *
     * @return 用户目录
     */
    public static File getUserHomeDir() {
        return file(getUserHomePath());
    }

    /**
     * 判断文件是否存在，如果path为null，则返回false
     *
     * @param path 文件路径
     * @return 如果存在返回true
     */
    public static boolean exist(String path) {
        return (null != path) && file(path).exists();
    }

    /**
     * 判断文件是否存在，如果file为null，则返回false
     *
     * @param file 文件
     * @return 如果存在返回true
     */
    public static boolean exist(File file) {
        return (null != file) && file.exists();
    }

    /**
     * 是否存在匹配文件
     *
     * @param directory 文件夹路径
     * @param regexp 文件夹中所包含文件名的正则表达式
     * @return 如果存在匹配文件返回true
     */
    public static boolean exist(String directory, String regexp) {
        final File file = new File(directory);
        if (false == file.exists()) {
            return false;
        }

        final String[] fileList = file.list();
        if (fileList == null) {
            return false;
        }

        for (String fileName : fileList) {
            if (fileName.matches(regexp)) {
                return true;
            }

        }
        return false;
    }

    /**
     * 指定文件最后修改时间
     *
     * @param file 文件
     * @return 最后修改时间
     */
    public static Date lastModifiedTime(File file) {
        if (false == exist(file)) {
            return null;
        }

        return new Date(file.lastModified());
    }

    /**
     * 指定路径文件最后修改时间
     *
     * @param path 绝对路径
     * @return 最后修改时间
     */
    public static Date lastModifiedTime(String path) {
        return lastModifiedTime(new File(path));
    }

    /**
     * 计算目录或文件的总大小<br>
     * 当给定对象为文件时，直接调用 {@link File#length()}<br>
     * 当给定对象为目录时，遍历目录下的所有文件和目录，递归计算其大小，求和返回<br>
     * 此方法不包括目录本身的占用空间大小。
     *
     * @param file 目录或文件,null或者文件不存在返回0
     * @return 总大小，bytes长度
     */
    public static long size(File file) {
        return size(file, false);
    }

    /**
     * 计算目录或文件的总大小<br>
     * 当给定对象为文件时，直接调用 {@link File#length()}<br>
     * 当给定对象为目录时，遍历目录下的所有文件和目录，递归计算其大小，求和返回
     *
     * @param file 目录或文件,null或者文件不存在返回0
     * @param includeDirSize 是否包括每层目录本身的大小
     * @return 总大小，bytes长度
     */
    public static long size(File file, boolean includeDirSize) {
        if (null == file || false == file.exists() || isSymlink(file)) {
            return 0;
        }

        if (file.isDirectory()) {
            long size = includeDirSize ? file.length() : 0;
            File[] subFiles = file.listFiles();
            if (ArrayUtil.isEmpty(subFiles)) {
                return 0L;// empty directory
            }
            for (File subFile : subFiles) {
                size += size(subFile, includeDirSize);
            }
            return size;
        } else {
            return file.length();
        }
    }

    /**
     * 计算文件的总行数<br>
     * 读取文件采用系统默认编码，一般乱码不会造成行数错误。
     *
     * @param file 文件
     * @return 该文件总行数
     */
    public static int getTotalLines(File file) {
        if (false == isFile(file)) {
            throw new IORuntimeException("Input must be a File");
        }
        try (final LineNumberReader lineNumberReader = new LineNumberReader(new java.io.FileReader(file))) {
            // 设置起始为1
            lineNumberReader.setLineNumber(1);
            // 跳过文件中内容
            //noinspection ResultOfMethodCallIgnored
            lineNumberReader.skip(Long.MAX_VALUE);
            // 获取当前行号
            return lineNumberReader.getLineNumber();
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * 给定文件或目录的最后修改时间是否晚于给定时间
     *
     * @param file 文件或目录
     * @param reference 参照文件
     * @return 是否晚于给定时间
     */
    public static boolean newerThan(File file, File reference) {
        if (null == reference || false == reference.exists()) {
            return true;// 文件一定比一个不存在的文件新
        }
        return newerThan(file, reference.lastModified());
    }

    /**
     * 给定文件或目录的最后修改时间是否晚于给定时间
     *
     * @param file 文件或目录
     * @param timeMillis 做为对比的时间
     * @return 是否晚于给定时间
     */
    public static boolean newerThan(File file, long timeMillis) {
        if (null == file || false == file.exists()) {
            return false;// 不存在的文件一定比任何时间旧
        }
        return file.lastModified() > timeMillis;
    }

    /**
     * 创建文件及其父目录，如果这个文件存在，直接返回这个文件<br>
     * 此方法不对File对象类型做判断，如果File不存在，无法判断其类型
     *
     * @param path 相对ClassPath的目录或者绝对路径目录，使用POSIX风格
     * @return 文件，若路径为null，返回null
     * @throws IORuntimeException IO异常
     */
    public static File touch(String path) throws IORuntimeException {
        if (path == null) {
            return null;
        }
        return touch(file(path));
    }

    /**
     * 创建文件及其父目录，如果这个文件存在，直接返回这个文件<br>
     * 此方法不对File对象类型做判断，如果File不存在，无法判断其类型
     *
     * @param file 文件对象
     * @return 文件，若路径为null，返回null
     * @throws IORuntimeException IO异常
     */
    public static File touch(File file) throws IORuntimeException {
        if (null == file) {
            return null;
        }
        if (false == file.exists()) {
            mkParentDirs(file);
            try {
                //noinspection ResultOfMethodCallIgnored
                file.createNewFile();
            } catch (Exception e) {
                throw new IORuntimeException(e);
            }
        }
        return file;
    }

    /**
     * 创建文件及其父目录，如果这个文件存在，直接返回这个文件<br>
     * 此方法不对File对象类型做判断，如果File不存在，无法判断其类型
     *
     * @param parent 父文件对象
     * @param path 文件路径
     * @return File
     * @throws IORuntimeException IO异常
     */
    public static File touch(File parent, String path) throws IORuntimeException {
        return touch(file(parent, path));
    }

    /**
     * 创建文件及其父目录，如果这个文件存在，直接返回这个文件<br>
     * 此方法不对File对象类型做判断，如果File不存在，无法判断其类型
     *
     * @param parent 父文件对象
     * @param path 文件路径
     * @return File
     * @throws IORuntimeException IO异常
     */
    public static File touch(String parent, String path) throws IORuntimeException {
        return touch(file(parent, path));
    }

    /**
     * 创建所给文件或目录的父目录
     *
     * @param file 文件或目录
     * @return 父目录
     */
    public static File mkParentDirs(File file) {
        if (null == file) {
            return null;
        }
        return mkdir(getParent(file, 1));
    }

    /**
     * 创建父文件夹，如果存在直接返回此文件夹
     *
     * @param path 文件夹路径，使用POSIX格式，无论哪个平台
     * @return 创建的目录
     */
    public static File mkParentDirs(String path) {
        if (path == null) {
            return null;
        }
        return mkParentDirs(file(path));
    }

    /**
     * 删除文件或者文件夹<br>
     * 路径如果为相对路径，会转换为ClassPath路径！ 注意：删除文件夹时不会判断文件夹是否为空，如果不空则递归删除子文件或文件夹<br>
     * 某个文件删除失败会终止删除操作
     *
     * @param fullFileOrDirPath 文件或者目录的路径
     * @return 成功与否
     * @throws IORuntimeException IO异常
     */
    public static boolean del(String fullFileOrDirPath) throws IORuntimeException {
        return del(file(fullFileOrDirPath));
    }

    /**
     * 删除文件或者文件夹<br>
     * 注意：删除文件夹时不会判断文件夹是否为空，如果不空则递归删除子文件或文件夹<br>
     * 某个文件删除失败会终止删除操作
     *
     * <p>
     * 从5.7.6开始，删除文件使用{@link Files#delete(Path)}代替 {@link File#delete()}<br>
     * 因为前者遇到文件被占用等原因时，抛出异常，而非返回false，异常会指明具体的失败原因。
     * </p>
     *
     * @param file 文件对象
     * @return 成功与否
     * @throws IORuntimeException IO异常
     * @see Files#delete(Path)
     */
    public static boolean del(File file) throws IORuntimeException {
        if (file == null || false == file.exists()) {
            // 如果文件不存在或已被删除，此处返回true表示删除成功
            return true;
        }

        if (file.isDirectory()) {
            // 清空目录下所有文件和目录
            boolean isOk = clean(file);
            if (false == isOk) {
                return false;
            }
        }

        // 删除文件或清空后的目录
        final Path path = file.toPath();
        try {
            delFile(path);
        } catch (DirectoryNotEmptyException e) {
            // 遍历清空目录没有成功，此时补充删除一次（可能存在部分软链）
            del(path);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }

        return true;
    }

    /**
     * 清空文件夹<br>
     * 注意：清空文件夹时不会判断文件夹是否为空，如果不空则递归删除子文件或文件夹<br>
     * 某个文件删除失败会终止删除操作
     *
     * @param dirPath 文件夹路径
     * @return 成功与否
     * @throws IORuntimeException IO异常
     */
    public static boolean clean(String dirPath) throws IORuntimeException {
        return clean(file(dirPath));
    }

    /**
     * 清空文件夹<br>
     * 注意：清空文件夹时不会判断文件夹是否为空，如果不空则递归删除子文件或文件夹<br>
     * 某个文件删除失败会终止删除操作
     *
     * @param directory 文件夹
     * @return 成功与否
     * @throws IORuntimeException IO异常
     */
    public static boolean clean(File directory) throws IORuntimeException {
        if (directory == null || directory.exists() == false || false == directory.isDirectory()) {
            return true;
        }

        final File[] files = directory.listFiles();
        if (null != files) {
            for (File childFile : files) {
                if (false == del(childFile)) {
                    // 删除一个出错则本次删除任务失败
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 清理空文件夹<br>
     * 此方法用于递归删除空的文件夹，不删除文件<br>
     * 如果传入的文件夹本身就是空的，删除这个文件夹
     *
     * @param directory 文件夹
     * @return 成功与否
     * @throws IORuntimeException IO异常
     */
    public static boolean cleanEmpty(File directory) throws IORuntimeException {
        if (directory == null || false == directory.exists() || false == directory.isDirectory()) {
            return true;
        }

        final File[] files = directory.listFiles();
        if (ArrayUtil.isEmpty(files)) {
            // 空文件夹则删除之
            return directory.delete();
        }

        for (File childFile : files) {
            cleanEmpty(childFile);
        }
        return true;
    }

    /**
     * 创建文件夹，如果存在直接返回此文件夹<br>
     * 此方法不对File对象类型做判断，如果File不存在，无法判断其类型
     *
     * @param dirPath 文件夹路径，使用POSIX格式，无论哪个平台
     * @return 创建的目录
     */
    public static File mkdir(String dirPath) {
        if (dirPath == null) {
            return null;
        }
        final File dir = file(dirPath);
        return mkdir(dir);
    }

    /**
     * 创建文件夹，会递归自动创建其不存在的父文件夹，如果存在直接返回此文件夹<br>
     * 此方法不对File对象类型做判断，如果File不存在，无法判断其类型<br>
     *
     * @param dir 目录
     * @return 创建的目录
     */
    public static File mkdir(File dir) {
        if (dir == null) {
            return null;
        }
        if (false == dir.exists()) {
            mkdirsSafely(dir, 5, 1);
        }
        return dir;
    }

    /**
     * 安全地级联创建目录 (确保并发环境下能创建成功)
     *
     * <pre>
     *     并发环境下，假设 test 目录不存在，如果线程A mkdirs "test/A" 目录，线程B mkdirs "test/B"目录，
     *     其中一个线程可能会失败，进而导致以下代码抛出 FileNotFoundException 异常
     *
     *     file.getParentFile().mkdirs(); // 父目录正在被另一个线程创建中，返回 false
     *     file.createNewFile(); // 抛出 IO 异常，因为该线程无法感知到父目录已被创建
     * </pre>
     *
     * @param dir 待创建的目录
     * @param tryCount 最大尝试次数
     * @param sleepMillis 线程等待的毫秒数
     * @return true表示创建成功，false表示创建失败
     * @Author z8g
     */
    public static boolean mkdirsSafely(File dir, int tryCount, long sleepMillis) {
        if (dir == null) {
            return false;
        }
        if (dir.isDirectory()) {
            return true;
        }
        for (int i = 1; i <= tryCount; i++) { // 高并发场景下，可以看到 i 处于 1 ~ 3 之间
            // 如果文件已存在，也会返回 false，所以该值不能作为是否能创建的依据，因此不对其进行处理
            //noinspection ResultOfMethodCallIgnored
            dir.mkdirs();
            if (dir.exists()) {
                return true;
            }
            try {
                Thread.sleep(sleepMillis);
            } catch (InterruptedException e) {
                return false;
            }
        }
        return dir.exists();
    }

    /**
     * 创建临时文件<br>
     * 创建后的文件名为 prefix[Randon].tmp
     *
     * @param dir 临时文件创建的所在目录
     * @return 临时文件
     * @throws IORuntimeException IO异常
     */
    public static File createTempFile(File dir) throws IORuntimeException {
        return createTempFile("hutool", null, dir, true);
    }

    /**
     * 在默认临时文件目录下创建临时文件，创建后的文件名为 prefix[Randon].tmp。
     * 默认临时文件目录由系统属性 {@code java.io.tmpdir} 指定。
     * 在 UNIX 系统上，此属性的默认值通常是 {@code "tmp"} 或 {@code "vartmp"}；
     * 在 Microsoft Windows 系统上，它通常是 {@code "C:\\WINNT\\TEMP"}。
     * 调用 Java 虚拟机时，可以为该系统属性赋予不同的值，但不保证对该属性的编程更改对该方法使用的临时目录有任何影响。
     *
     * @return 临时文件
     * @throws IORuntimeException IO异常
     */
    public static File createTempFile() throws IORuntimeException {
        return createTempFile("hutool", null, null, true);
    }

    /**
     * 在默认临时文件目录下创建临时文件，创建后的文件名为 prefix[Randon].suffix。
     * 默认临时文件目录由系统属性 {@code java.io.tmpdir} 指定。
     * 在 UNIX 系统上，此属性的默认值通常是 {@code "tmp"} 或 {@code "vartmp"}；
     * 在 Microsoft Windows 系统上，它通常是 {@code "C:\\WINNT\\TEMP"}。
     * 调用 Java 虚拟机时，可以为该系统属性赋予不同的值，但不保证对该属性的编程更改对该方法使用的临时目录有任何影响。
     *
     * @param suffix 后缀，如果null则使用默认.tmp
     * @param isReCreat 是否重新创建文件（删掉原来的，创建新的）
     * @return 临时文件
     * @throws IORuntimeException IO异常
     */
    public static File createTempFile(String suffix, boolean isReCreat) throws IORuntimeException {
        return createTempFile("hutool", suffix, null, isReCreat);
    }

    /**
     * 在默认临时文件目录下创建临时文件，创建后的文件名为 prefix[Randon].suffix。
     * 默认临时文件目录由系统属性 {@code java.io.tmpdir} 指定。
     * 在 UNIX 系统上，此属性的默认值通常是 {@code "tmp"} 或 {@code "vartmp"}；
     * 在 Microsoft Windows 系统上，它通常是 {@code "C:\\WINNT\\TEMP"}。
     * 调用 Java 虚拟机时，可以为该系统属性赋予不同的值，但不保证对该属性的编程更改对该方法使用的临时目录有任何影响。
     *
     * @param prefix 前缀，至少3个字符
     * @param suffix 后缀，如果null则使用默认.tmp
     * @param isReCreat 是否重新创建文件（删掉原来的，创建新的）
     * @return 临时文件
     * @throws IORuntimeException IO异常
     */
    public static File createTempFile(String prefix, String suffix, boolean isReCreat) throws IORuntimeException {
        return createTempFile(prefix, suffix, null, isReCreat);
    }

    /**
     * 创建临时文件<br>
     * 创建后的文件名为 prefix[Randon].tmp
     *
     * @param dir 临时文件创建的所在目录
     * @param isReCreat 是否重新创建文件（删掉原来的，创建新的）
     * @return 临时文件
     * @throws IORuntimeException IO异常
     */
    public static File createTempFile(File dir, boolean isReCreat) throws IORuntimeException {
        return createTempFile("hutool", null, dir, isReCreat);
    }

    /**
     * 创建临时文件<br>
     * 创建后的文件名为 prefix[Randon].suffix From com.jodd.io.FileUtil
     *
     * @param prefix 前缀，至少3个字符
     * @param suffix 后缀，如果null则使用默认.tmp
     * @param dir 临时文件创建的所在目录
     * @param isReCreat 是否重新创建文件（删掉原来的，创建新的）
     * @return 临时文件
     * @throws IORuntimeException IO异常
     */
    public static File createTempFile(String prefix, String suffix, File dir, boolean isReCreat) throws IORuntimeException {
        int exceptionsCount = 0;
        while (true) {
            try {
                File file = File.createTempFile(prefix, suffix, mkdir(dir)).getCanonicalFile();
                if (isReCreat) {
                    //noinspection ResultOfMethodCallIgnored
                    file.delete();
                    //noinspection ResultOfMethodCallIgnored
                    file.createNewFile();
                }
                return file;
            } catch (IOException ioex) { // fixes java.io.WinNTFileSystem.createFileExclusively access denied
                if (++exceptionsCount >= 50) {
                    throw new IORuntimeException(ioex);
                }
            }
        }
    }

    /**
     * 通过JDK7+的 Files#copy(Path, Path, CopyOption...) 方法拷贝文件
     *
     * @param src 源文件路径
     * @param dest 目标文件或目录路径，如果为目录使用与源文件相同的文件名
     * @param options {@link StandardCopyOption}
     * @return File
     * @throws IORuntimeException IO异常
     */
    public static File copyFile(String src, String dest, StandardCopyOption... options) throws IORuntimeException {
        Validate.notBlank(src, "Source File path is blank !");
        Validate.notBlank(dest, "Destination File path is blank !");
        return copyFile(Paths.get(src), Paths.get(dest), options).toFile();
    }

    /**
     * 通过JDK7+的 Files#copy(Path, Path, CopyOption...) 方法拷贝文件
     *
     * @param src 源文件
     * @param dest 目标文件或目录，如果为目录使用与源文件相同的文件名
     * @param options {@link StandardCopyOption}
     * @return 目标文件
     * @throws IORuntimeException IO异常
     */
    public static File copyFile(File src, File dest, StandardCopyOption... options) throws IORuntimeException {
        // check
        Validate.notNull(src, "Source File is null !");
        if (false == src.exists()) {
            throw new IORuntimeException("File not exist: " + src);
        }
        Validate.notNull(dest, "Destination File or directiory is null !");
        if (equals(src, dest)) {
            throw new IORuntimeException(String.format("Files '%s' and '%s' are equal", src, dest));
        }
        return copyFile(src.toPath(), dest.toPath(), options).toFile();
    }

    /**
     * 移动文件或者目录
     *
     * @param src 源文件或者目录
     * @param target 目标文件或者目录
     * @param isOverride 是否覆盖目标，只有目标为文件才覆盖
     * @throws IORuntimeException IO异常
     * @see PathUtil#move(Path, Path, boolean)
     */
    public static void move(File src, File target, boolean isOverride) throws IORuntimeException {
        Validate.notNull(src, "Src file must be not null!");
        Validate.notNull(target, "target file must be not null!");
        move(src.toPath(), target.toPath(), isOverride);
    }

    /**
     * 移动文件或者目录
     *
     * @param src 源文件或者目录
     * @param target 目标文件或者目录
     * @param isOverride 是否覆盖目标，只有目标为文件才覆盖
     * @throws IORuntimeException IO异常
     * @see PathUtil#moveContent(Path, Path, boolean)
     */
    public static void moveContent(File src, File target, boolean isOverride) throws IORuntimeException {
        Validate.notNull(src, "Src file must be not null!");
        Validate.notNull(target, "target file must be not null!");
        moveContent(src.toPath(), target.toPath(), isOverride);
    }

    /**
     * 修改文件或目录的文件名，不变更路径，只是简单修改文件名，不保留扩展名。<br>
     *
     * <pre>
     * FileUtil.rename(file, "aaa.png", true) xx/xx.png =》xx/aaa.png
     * </pre>
     *
     * @param file 被修改的文件
     * @param newName 新的文件名，如需扩展名，需自行在此参数加上，原文件名的扩展名不会被保留
     * @param isOverride 是否覆盖目标文件
     * @return 目标文件
     */
    public static File rename(File file, String newName, boolean isOverride) {
        return rename(file, newName, false, isOverride);
    }

    /**
     * 修改文件或目录的文件名，不变更路径，只是简单修改文件名<br>
     * 重命名有两种模式：<br>
     * 1、isRetainExt为true时，保留原扩展名：
     *
     * <pre>
     * FileUtil.rename(file, "aaa", true) xx/xx.png =》xx/aaa.png
     * </pre>
     *
     * <p>
     * 2、isRetainExt为false时，不保留原扩展名，需要在newName中
     *
     * <pre>
     * FileUtil.rename(file, "aaa.jpg", false) xx/xx.png =》xx/aaa.jpg
     * </pre>
     *
     * @param file 被修改的文件
     * @param newName 新的文件名，可选是否包括扩展名
     * @param isRetainExt 是否保留原文件的扩展名，如果保留，则newName不需要加扩展名
     * @param isOverride 是否覆盖目标文件
     * @return 目标文件
     * @see PathUtil#rename(Path, String, boolean)
     */
    public static File rename(File file, String newName, boolean isRetainExt, boolean isOverride) {
        if (isRetainExt) {
            final String extName = FilenameUtils.getExtension(file.getName());
            if (StringUtils.isNotBlank(extName)) {
                newName = newName.concat(".").concat(extName);
            }
        }
        return rename(file.toPath(), newName, isOverride).toFile();
    }

    /**
     * 获取规范的绝对路径
     *
     * @param file 文件
     * @return 规范绝对路径，如果传入file为null，返回null
     */
    public static String getCanonicalPath(File file) {
        if (null == file) {
            return null;
        }
        try {
            return file.getCanonicalPath();
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * 获取标准的绝对路径
     *
     * @param file 文件
     * @return 绝对路径
     */
    public static String getAbsolutePath(File file) {
        if (file == null) {
            return null;
        }

        try {
            return file.getCanonicalPath();
        } catch (IOException e) {
            return file.getAbsolutePath();
        }
    }

    /**
     * 获取绝对路径，相对于ClassPath的目录<br>
     * 如果给定就是绝对路径，则返回原路径，原路径把所有\替换为/<br>
     * 兼容Spring风格的路径表示，例如：classpath:config/example.setting也会被识别后转换
     *
     * @param path 相对路径
     * @return 绝对路径
     */
    public static String getAbsolutePath(String path) {
        return getAbsolutePath(path, null);
    }

    /**
     * 获取绝对路径<br>
     * 此方法不会判定给定路径是否有效（文件或目录存在）
     *
     * @param path 相对路径
     * @param baseClass 相对路径所相对的类
     * @return 绝对路径
     */
    public static String getAbsolutePath(String path, Class<?> baseClass) {
        try {
            String normalPath;
            if (path == null) {
                normalPath = StringUtils.EMPTY;
            } else {
                normalPath = normalize(path);
                if (isAbsolutePath(normalPath)) {
                    // 给定的路径已经是绝对路径了
                    return normalPath;
                }
            }

            // 相对于ClassPath路径
            final URL url = (null != baseClass) ? baseClass.getResource(path) : ClassLoaderUtil.getClassLoader().getResource(path);
            if (null != url) {
                URI uri = new URI(StringUtils.trim(url.toString()));
                // 对于jar中文件包含file:前缀，需要去掉此类前缀
                return FileUtil.normalize(uri.getPath());
            }

            // 如果资源不存在，则返回一个拼接的资源绝对路径
            final URL classPathURL = ClassLoaderUtil.getClassLoader().getResource("");
            final String classPath = new URI(StringUtils.trim(classPathURL.toString())).getPath();
            if (null == classPath) {
                // throw new NullPointerException("ClassPath is null !");
                // 在jar运行模式中，ClassPath有可能获取不到，此时返回原始相对路径（此时获取的文件为相对工作目录）
                return path;
            }
            // 资源不存在的情况下使用标准化路径有问题，使用原始路径拼接后标准化路径
            return normalize(classPath.concat(Objects.requireNonNull(path)));
        } catch (URISyntaxException e) {
            throw new UtilException(e);
        }
    }

    /**
     * 给定路径已经是绝对路径<br>
     * 此方法并没有针对路径做标准化，建议先执行{@link #normalize(String)}方法标准化路径后判断<br>
     * 绝对路径判断条件是：
     * <ul>
     *     <li>以/开头的路径</li>
     *     <li>满足类似于 c:/xxxxx，其中祖母随意，不区分大小写</li>
     *     <li>满足类似于 d:\xxxxx，其中祖母随意，不区分大小写</li>
     * </ul>
     *
     * @param path 需要检查的Path
     * @return 是否已经是绝对路径
     */
    public static boolean isAbsolutePath(String path) {
        if (StringUtils.isEmpty(path)) {
            return false;
        }

        // 给定的路径已经是绝对路径了
        return StringPool.C_SLASH == path.charAt(0) || PATTERN_PATH_ABSOLUTE.matcher(path).matches();
    }

    /**
     * 判断是否为目录，如果path为null，则返回false
     *
     * @param path 文件路径
     * @return 如果为目录true
     */
    public static boolean isDirectory(String path) {
        return (null != path) && file(path).isDirectory();
    }

    /**
     * 判断是否为目录，如果file为null，则返回false
     *
     * @param file 文件
     * @return 如果为目录true
     */
    public static boolean isDirectory(File file) {
        return (null != file) && file.isDirectory();
    }

    /**
     * 判断是否为文件，如果path为null，则返回false
     *
     * @param path 文件路径
     * @return 如果为文件true
     */
    public static boolean isFile(String path) {
        return (null != path) && file(path).isFile();
    }

    /**
     * 判断是否为文件，如果file为null，则返回false
     *
     * @param file 文件
     * @return 如果为文件true
     */
    public static boolean isFile(File file) {
        return (null != file) && file.isFile();
    }

    /**
     * 检查两个文件是否是同一个文件<br>
     * 所谓文件相同，是指File对象是否指向同一个文件或文件夹
     *
     * @param file1 文件1
     * @param file2 文件2
     * @return 是否相同
     * @throws IORuntimeException IO异常
     */
    public static boolean equals(File file1, File file2) throws IORuntimeException {
        Validate.notNull(file1);
        Validate.notNull(file2);
        if (false == file1.exists() || false == file2.exists()) {
            // 两个文件都不存在判断其路径是否相同， 对于一个存在一个不存在的情况，一定不相同
            return false == file1.exists()//
                   && false == file2.exists()//
                   && pathEquals(file1, file2);
        }
        return equals(file1.toPath(), file2.toPath());
    }

    /**
     * 比较两个文件内容是否相同<br>
     * 首先比较长度，长度一致再比较内容<br>
     * 此方法来自Apache Commons io
     *
     * @param file1 文件1
     * @param file2 文件2
     * @return 两个文件内容一致返回true，否则false
     * @throws IORuntimeException IO异常
     */
    public static boolean contentEquals(File file1, File file2) throws IORuntimeException {
        boolean file1Exists = file1.exists();
        if (file1Exists != file2.exists()) {
            return false;
        }

        if (false == file1Exists) {
            // 两个文件都不存在，返回true
            return true;
        }

        if (file1.isDirectory() || file2.isDirectory()) {
            // 不比较目录
            throw new IORuntimeException("Can't compare directories, only files");
        }

        if (file1.length() != file2.length()) {
            // 文件长度不同
            return false;
        }

        if (equals(file1, file2)) {
            // 同一个文件
            return true;
        }

        InputStream input1 = null;
        InputStream input2 = null;
        try {
            input1 = getInputStream(file1);
            input2 = getInputStream(file2);
            return IoUtil.contentEquals(input1, input2);

        } finally {
            IoUtil.close(input1);
            IoUtil.close(input2);
        }
    }

    // -----------------------------------------------------------------------

    /**
     * 比较两个文件内容是否相同<br>
     * 首先比较长度，长度一致再比较内容，比较内容采用按行读取，每行比较<br>
     * 此方法来自Apache Commons io
     *
     * @param file1 文件1
     * @param file2 文件2
     * @param charset 编码，null表示使用平台默认编码 两个文件内容一致返回true，否则false
     * @return 是否相同
     * @throws IORuntimeException IO异常
     */
    public static boolean contentEqualsIgnoreEOL(File file1, File file2, Charset charset) throws IORuntimeException {
        boolean file1Exists = file1.exists();
        if (file1Exists != file2.exists()) {
            return false;
        }

        if (!file1Exists) {
            // 两个文件都不存在，返回true
            return true;
        }

        if (file1.isDirectory() || file2.isDirectory()) {
            // 不比较目录
            throw new IORuntimeException("Can't compare directories, only files");
        }

        if (equals(file1, file2)) {
            // 同一个文件
            return true;
        }

        Reader input1 = null;
        Reader input2 = null;
        try {
            input1 = getReader(file1, charset);
            input2 = getReader(file2, charset);
            return IoUtil.contentEqualsIgnoreEOL(input1, input2);
        } finally {
            IoUtil.close(input1);
            IoUtil.close(input2);
        }
    }

    /**
     * 文件路径是否相同<br>
     * 取两个文件的绝对路径比较，在Windows下忽略大小写，在Linux下不忽略。
     *
     * @param file1 文件1
     * @param file2 文件2
     * @return 文件路径是否相同
     */
    public static boolean pathEquals(File file1, File file2) {
        if (isWindows()) {
            // Windows环境
            try {
                if (StringUtils.equalsIgnoreCase(file1.getCanonicalPath(), file2.getCanonicalPath())) {
                    return true;
                }
            } catch (Exception e) {
                if (StringUtils.equalsIgnoreCase(file1.getAbsolutePath(), file2.getAbsolutePath())) {
                    return true;
                }
            }
        } else {
            // 类Unix环境
            try {
                if (StringUtils.equals(file1.getCanonicalPath(), file2.getCanonicalPath())) {
                    return true;
                }
            } catch (Exception e) {
                if (StringUtils.equals(file1.getAbsolutePath(), file2.getAbsolutePath())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获得最后一个文件路径分隔符的位置
     *
     * @param filePath 文件路径
     * @return 最后一个文件路径分隔符的位置
     */
    public static int lastIndexOfSeparator(String filePath) {
        if (StringUtils.isNotEmpty(filePath)) {
            int i = filePath.length();
            char c;
            while (--i >= 0) {
                c = filePath.charAt(i);
                if (CharPool.SLASH == c || CharPool.BACKSLASH == c) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * 判断文件是否被改动<br>
     * 如果文件对象为 null 或者文件不存在，被视为改动
     *
     * @param file 文件对象
     * @param lastModifyTime 上次的改动时间
     * @return 是否被改动
     * @deprecated 拼写错误，请使用{@link #isModified(File, long)}
     */
    @Deprecated
    public static boolean isModifed(File file, long lastModifyTime) {
        return isModified(file, lastModifyTime);
    }


    /**
     * 判断文件是否被改动<br>
     * 如果文件对象为 null 或者文件不存在，被视为改动
     *
     * @param file 文件对象
     * @param lastModifyTime 上次的改动时间
     * @return 是否被改动
     */
    public static boolean isModified(File file, long lastModifyTime) {
        if (null == file || false == file.exists()) {
            return true;
        }
        return file.lastModified() != lastModifyTime;
    }

    /**
     * 修复路径<br>
     * 如果原路径尾部有分隔符，则保留为标准分隔符（/），否则不保留
     * <ol>
     * <li>1. 统一用 /</li>
     * <li>2. 多个 / 转换为一个 /</li>
     * <li>3. 去除左边空格</li>
     * <li>4. .. 和 . 转换为绝对路径，当..多于已有路径时，直接返回根路径</li>
     * </ol>
     * <p>
     * 栗子：
     *
     * <pre>
     * "/foo//" =》 "/foo/"
     * "/foo/./" =》 "/foo/"
     * "/foo/../bar" =》 "/bar"
     * "/foo/../bar/" =》 "/bar/"
     * "/foo/../bar/../baz" =》 "/baz"
     * "/../" =》 "/"
     * "foo/bar/.." =》 "foo"
     * "foo/../bar" =》 "bar"
     * "foo/../../bar" =》 "bar"
     * "//server/foo/../bar" =》 "/server/bar"
     * "//server/../bar" =》 "/bar"
     * "C:\\foo\\..\\bar" =》 "C:/bar"
     * "C:\\..\\bar" =》 "C:/bar"
     * "~/foo/../bar/" =》 "~/bar/"
     * "~/../bar" =》 普通用户运行是'bar的home目录'，ROOT用户运行是'/bar'
     * </pre>
     *
     * @param path 原路径
     * @return 修复后的路径
     */
    public static String normalize(String path) {
        if (path == null) {
            return null;
        }

        // 兼容Spring风格的ClassPath路径，去除前缀，不区分大小写
        String pathToUse = StringUtils.removeStartIgnoreCase(path, StringPool.CLASSPATH_URL_PREFIX);
        // 去除file:前缀
        pathToUse = StringUtils.removeStartIgnoreCase(pathToUse, StringPool.FILE_URL_PREFIX);

        // 识别home目录形式，并转换为绝对路径

        if (pathToUse.charAt(0) == '~') {
            pathToUse = getUserHomePath() + pathToUse.substring(1);
        }

        // 统一使用斜杠
        pathToUse = pathToUse.replaceAll("[/\\\\]+", StringPool.SLASH);
        // 去除开头空白符，末尾空白符合法，不去除
        pathToUse = org.springframework.util.StringUtils.trimLeadingCharacter(pathToUse, ' ');
        //兼容Windows下的共享目录路径（原始路径如果以\\开头，则保留这种路径）
        if (path.startsWith("\\\\")) {
            pathToUse = "\\" + pathToUse;
        }

        String prefix = StringUtils.EMPTY;
        int prefixIndex = pathToUse.indexOf(StringPool.COLON);
        if (prefixIndex > -1) {
            // 可能Windows风格路径
            prefix = pathToUse.substring(0, prefixIndex + 1);
            if (prefix.charAt(0) == StringPool.C_SLASH) {
                // 去除类似于/C:这类路径开头的斜杠
                prefix = prefix.substring(1);
            }
            if (false == prefix.contains(StringPool.SLASH)) {
                pathToUse = pathToUse.substring(prefixIndex + 1);
            } else {
                // 如果前缀中包含/,说明非Windows风格path
                prefix = StringUtils.EMPTY;
            }
        }
        if (pathToUse.startsWith(StringPool.SLASH)) {
            prefix += StringPool.SLASH;
            pathToUse = pathToUse.substring(1);
        }

        List<String> pathList = Arrays.asList(StringUtils.split(pathToUse, StringPool.C_SLASH));

        List<String> pathElements = new LinkedList<>();
        int tops = 0;
        String element;
        for (int i = pathList.size() - 1; i >= 0; i--) {
            element = pathList.get(i);
            // 只处理非.的目录，即只处理非当前目录
            if (false == StringPool.DOT.equals(element)) {
                if (StringPool.DOUBLE_DOT.equals(element)) {
                    tops++;
                } else {
                    if (tops > 0) {
                        // 有上级目录标记时按照个数依次跳过
                        tops--;
                    } else {
                        // Normal path element found.
                        pathElements.add(0, element);
                    }
                }
            }
        }

        // issue#1703@Github
        if (tops > 0 && StringUtils.isEmpty(prefix)) {
            // 只有相对路径补充开头的..，绝对路径直接忽略之
            while (tops-- > 0) {
                //遍历完节点发现还有上级标注（即开头有一个或多个..），补充之
                // Normal path element found.
                pathElements.add(0, StringPool.DOUBLE_DOT);
            }
        }

        return prefix + StringUtils.join(pathElements, StringPool.SLASH);
    }

    /**
     * 获得相对子路径
     * <p>
     * 栗子：
     *
     * <pre>
     * dirPath: d:/aaa/bbb    filePath: d:/aaa/bbb/ccc     =》    ccc
     * dirPath: d:/Aaa/bbb    filePath: d:/aaa/bbb/ccc.txt     =》    ccc.txt
     * </pre>
     *
     * @param rootDir 绝对父路径
     * @param file 文件
     * @return 相对子路径
     */
    public static String subPath(String rootDir, File file) {
        try {
            return subPath(rootDir, file.getCanonicalPath());
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * 获得相对子路径，忽略大小写
     * <p>
     * 栗子：
     *
     * <pre>
     * dirPath: d:/aaa/bbb    filePath: d:/aaa/bbb/ccc     =》    ccc
     * dirPath: d:/Aaa/bbb    filePath: d:/aaa/bbb/ccc.txt     =》    ccc.txt
     * dirPath: d:/Aaa/bbb    filePath: d:/aaa/bbb/     =》    ""
     * </pre>
     *
     * @param dirPath 父路径
     * @param filePath 文件路径
     * @return 相对子路径
     */
    public static String subPath(String dirPath, String filePath) {
        if (StringUtils.isNotEmpty(dirPath) && StringUtils.isNotEmpty(filePath)) {
            dirPath = StringUtils.removeEnd(normalize(dirPath), "/");
            filePath = normalize(filePath);

            final String result = StringUtils.removeStartIgnoreCase(filePath, dirPath);
            return StringUtils.removeStart(result, "/");
        }
        return filePath;
    }

    // -------------------------------------------------------------------------------------------- name start

    /**
     * 返回文件名
     *
     * @param file 文件
     * @return 文件名
     * @see FilenameUtils#getName(String)
     */
    public static String getName(File file) {
        return FilenameUtils.getName(file.getName());
    }

    /**
     * 返回文件名<br>
     * <pre>
     * "d:/test/aaa" 返回 "aaa"
     * "/test/aaa.jpg" 返回 "aaa.jpg"
     * </pre>
     *
     * @param filePath 文件
     * @return 文件名
     * @see FilenameUtils#getName(String)
     */
    public static String getName(String filePath) {
        return FilenameUtils.getName(filePath);
    }

    /**
     * 获取文件后缀名，扩展名不带“.”
     *
     * @param file 文件
     * @return 扩展名
     * @see FilenameUtils#getExtension(String)
     */
    public static String getSuffix(File file) {
        return FilenameUtils.getExtension(file.getName());
    }

    /**
     * 获得文件后缀名，扩展名不带“.”
     *
     * @param fileName 文件名
     * @return 扩展名
     * @see FilenameUtils#getExtension(String)
     */
    public static String getSuffix(String fileName) {
        return FilenameUtils.getExtension(fileName);
    }

    /**
     * 返回主文件名
     *
     * @param file 文件
     * @return 主文件名
     * @see FilenameUtils#getPrefix(String)
     */
    public static String getPrefix(File file) {
        return FilenameUtils.getPrefix(file.getName());
    }

    /**
     * 返回主文件名
     *
     * @param fileName 完整文件名
     * @return 主文件名
     * @see FilenameUtils#getPrefix(String)
     */
    public static String getPrefix(String fileName) {
        return FilenameUtils.getPrefix(fileName);
    }

    // -------------------------------------------------------------------------------------------- name end

    /**
     * 判断文件路径是否有指定后缀，忽略大小写<br>
     * 常用语判断扩展名
     *
     * @param file 文件或目录
     * @param suffix 后缀
     * @return 是否有指定后缀
     */
    public static boolean pathEndsWith(File file, String suffix) {
        return file.getPath().toLowerCase().endsWith(suffix);
    }

    /**
     * 检测给定文件的媒体类型
     *
     * @param file file
     * @return java.lang.String
     */
    public static String getMimeType(File file) {
        // 互联网媒体类型（Internet media type，也称为MIME类型（MIME type）或内容类型（content type））
        String MIMEType = null;
        Tika tika = new Tika();
        try {
            MIMEType = tika.detect(file);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
        return MIMEType;
    }

    // -------------------------------------------------------------------------------------------- in start

    /**
     * 获得输入流
     *
     * @param file 文件
     * @return 输入流
     * @throws IORuntimeException 文件未找到
     */
    public static BufferedInputStream getInputStream(File file) throws IORuntimeException {
        return IoUtil.toBuffered(IoUtil.toStream(file));
    }

    /**
     * 获得输入流
     *
     * @param path 文件路径
     * @return 输入流
     * @throws IORuntimeException 文件未找到
     */
    public static BufferedInputStream getInputStream(String path) throws IORuntimeException {
        return getInputStream(file(path));
    }

    /**
     * 获得一个文件读取器
     *
     * @param file 文件
     * @return BufferedReader对象
     * @throws IORuntimeException IO异常
     */
    public static BufferedReader getUtf8Reader(File file) throws IORuntimeException {
        return getReader(file, CharsetUtil.CHARSET_UTF_8);
    }

    /**
     * 获得一个文件读取器
     *
     * @param path 文件路径
     * @return BufferedReader对象
     * @throws IORuntimeException IO异常
     */
    public static BufferedReader getUtf8Reader(String path) throws IORuntimeException {
        return getReader(path, CharsetUtil.CHARSET_UTF_8);
    }

    /**
     * 获得一个文件读取器
     *
     * @param file 文件
     * @param charsetName 字符集
     * @return BufferedReader对象
     * @throws IORuntimeException IO异常
     * @deprecated 请使用 {@link #getReader(File, Charset)}
     */
    @Deprecated
    public static BufferedReader getReader(File file, String charsetName) throws IORuntimeException {
        return IoUtil.getReader(getInputStream(file), CharsetUtil.charset(charsetName));
    }

    /**
     * 获得一个文件读取器
     *
     * @param file 文件
     * @param charset 字符集
     * @return BufferedReader对象
     * @throws IORuntimeException IO异常
     */
    public static BufferedReader getReader(File file, Charset charset) throws IORuntimeException {
        return IoUtil.getReader(getInputStream(file), charset);
    }

    /**
     * 获得一个文件读取器
     *
     * @param path 绝对路径
     * @param charsetName 字符集
     * @return BufferedReader对象
     * @throws IORuntimeException IO异常
     * @deprecated 请使用 {@link #getReader(String, Charset)}
     */
    @Deprecated
    public static BufferedReader getReader(String path, String charsetName) throws IORuntimeException {
        return getReader(path, CharsetUtil.charset(charsetName));
    }

    /**
     * 获得一个文件读取器
     *
     * @param path 绝对路径
     * @param charset 字符集
     * @return BufferedReader对象
     * @throws IORuntimeException IO异常
     */
    public static BufferedReader getReader(String path, Charset charset) throws IORuntimeException {
        return getReader(file(path), charset);
    }

    // -------------------------------------------------------------------------------------------- in end

    /**
     * 单行处理文件内容
     *
     * @param file {@link RandomAccessFile}文件
     * @param charset 编码
     * @return 行内容
     * @throws IORuntimeException IO异常
     */
    public static String readLine(RandomAccessFile file, Charset charset) {
        String line;
        try {
            line = file.readLine();
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
        if (null != line) {
            return CharsetUtil.convert(line, CharsetUtil.CHARSET_ISO_8859_1, charset);
        }

        return null;
    }

    // -------------------------------------------------------------------------------------------- out start

    /**
     * 获得一个输出流对象
     *
     * @param file 文件
     * @return 输出流对象
     * @throws IORuntimeException IO异常
     */
    public static BufferedOutputStream getOutputStream(File file) throws IORuntimeException {
        final OutputStream out;
        try {
            out = new FileOutputStream(touch(file));
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
        return IoUtil.toBuffered(out);
    }

    /**
     * 获得一个输出流对象
     *
     * @param path 输出到的文件路径，绝对路径
     * @return 输出流对象
     * @throws IORuntimeException IO异常
     */
    public static BufferedOutputStream getOutputStream(String path) throws IORuntimeException {
        return getOutputStream(touch(path));
    }

    /**
     * 获取当前系统的换行分隔符
     *
     * <pre>
     * Windows: \r\n
     * Mac: \r
     * Linux: \n
     * </pre>
     *
     * @return 换行符
     */
    public static String getLineSeparator() {
        return System.lineSeparator();
        // return System.getProperty("line.separator");
    }

    // -------------------------------------------------------------------------------------------- out end

    /**
     * 获取指定层级的父路径
     *
     * <pre>
     * getParent("d:/aaa/bbb/cc/ddd", 0) -》 "d:/aaa/bbb/cc/ddd"
     * getParent("d:/aaa/bbb/cc/ddd", 2) -》 "d:/aaa/bbb"
     * getParent("d:/aaa/bbb/cc/ddd", 4) -》 "d:/"
     * getParent("d:/aaa/bbb/cc/ddd", 5) -》 null
     * </pre>
     *
     * @param filePath 目录或文件路径
     * @param level 层级
     * @return 路径File，如果不存在返回null
     */
    public static String getParent(String filePath, int level) {
        final File parent = getParent(file(filePath), level);
        try {
            return null == parent ? null : parent.getCanonicalPath();
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * 获取指定层级的父路径
     *
     * <pre>
     * getParent(file("d:/aaa/bbb/cc/ddd", 0)) -》 "d:/aaa/bbb/cc/ddd"
     * getParent(file("d:/aaa/bbb/cc/ddd", 2)) -》 "d:/aaa/bbb"
     * getParent(file("d:/aaa/bbb/cc/ddd", 4)) -》 "d:/"
     * getParent(file("d:/aaa/bbb/cc/ddd", 5)) -》 null
     * </pre>
     *
     * @param file 目录或文件
     * @param level 层级
     * @return 路径File，如果不存在返回null
     */
    public static File getParent(File file, int level) {
        if (level < 1 || null == file) {
            return file;
        }

        File parentFile;
        try {
            parentFile = file.getCanonicalFile().getParentFile();
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
        if (1 == level) {
            return parentFile;
        }
        return getParent(parentFile, level - 1);
    }

    /**
     * 检查父完整路径是否为自路径的前半部分，如果不是说明不是子路径，可能存在slip注入。
     * <p>
     * 见http://blog.nsfocus.net/zip-slip-2/
     *
     * @param parentFile 父文件或目录
     * @param file 子文件或目录
     * @return 子文件或目录
     * @throws IllegalArgumentException 检查创建的子文件不在父目录中抛出此异常
     */
    public static File checkSlip(File parentFile, File file) throws IllegalArgumentException {
        if (null != parentFile && null != file) {
            String parentCanonicalPath;
            String canonicalPath;
            try {
                parentCanonicalPath = parentFile.getCanonicalPath();
                canonicalPath = file.getCanonicalPath();
            } catch (IOException e) {
                // issue#I4CWMO@Gitee
                // getCanonicalPath有时会抛出奇怪的IO异常，此时忽略异常，使用AbsolutePath判断。
                parentCanonicalPath = parentFile.getAbsolutePath();
                canonicalPath = file.getAbsolutePath();
            }
            if (false == canonicalPath.startsWith(parentCanonicalPath)) {
                throw new IllegalArgumentException("New file is outside of the parent dir: " + file.getName());
            }
        }
        return file;
    }

    /**
     * 判断是否为符号链接文件
     *
     * @param file 被检查的文件
     * @return 是否为符号链接文件
     */
    public static boolean isSymlink(File file) {
        return isSymlink(file.toPath());
    }

    /**
     * 判断给定的目录是否为给定文件或文件夹的子目录
     *
     * @param parent 父目录
     * @param sub 子目录
     * @return 子目录是否为父目录的子目录
     */
    public static boolean isSub(File parent, File sub) {
        Validate.notNull(parent);
        Validate.notNull(sub);
        return isSub(parent.toPath(), sub.toPath());
    }
}
