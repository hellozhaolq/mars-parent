package com.zhaolq.mars.common.core.util;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.lang3.StringUtils;

import com.zhaolq.mars.common.core.console.ConsoleTable;
import com.zhaolq.mars.common.core.constant.CharPool;
import com.zhaolq.mars.common.core.constant.StringPool;
import com.zhaolq.mars.common.core.function.Filter;

/**
 * 类扫描器
 *
 * @author zhaolq
 * @date 2023/6/9 20:41
 */
public class ClassScannerUtil {
    private static final long serialVersionUID = 1L;

    /**
     * 包名
     */
    private final String scanPackageName;
    /**
     * 包名，最后跟一个点，表示包名，避免在检查前缀时的歧义<br>
     * 如果包名指定为空，不跟点
     */
    private final String packageNameWithDot;
    /**
     * 包路径，用于文件中对路径操作
     */
    private final String scanPackageDirName;
    /**
     * 包路径，用于jar中对路径操作，在Linux下与packageDirName一致
     */
    private final String scanPackagePath;
    /**
     * 过滤器
     */
    private final Filter<Class<?>> classFilter;
    /**
     * 编码
     */
    private final Charset charset;
    /**
     * 类加载器
     */
    private ClassLoader classLoader;
    /**
     * 是否初始化类
     */
    private boolean initialize;
    /**
     * 扫描结果集
     */
    private final Set<Class<?>> classes = new HashSet<>();

    /**
     * 构造，默认UTF-8编码
     */
    public ClassScannerUtil() {
        this(null);
    }

    /**
     * 构造，默认UTF-8编码
     *
     * @param packageName 包名，所有包传入""或者null
     */
    public ClassScannerUtil(String packageName) {
        this(packageName, null);
    }

    /**
     * 构造，默认UTF-8编码
     *
     * @param scanPackageName 包名，所有包传入""或者null
     * @param classFilter 过滤器，无需传入null
     */
    public ClassScannerUtil(String scanPackageName, Filter<Class<?>> classFilter) {
        this(scanPackageName, classFilter, CharsetUtil.CHARSET_UTF_8);
    }

    /**
     * 构造
     *
     * @param scanPackageName 包名，所有包传入""或者null
     * @param classFilter 过滤器，无需传入null
     * @param charset 编码
     */
    public ClassScannerUtil(String scanPackageName, Filter<Class<?>> classFilter, Charset charset) {
        scanPackageName = StrUtil.nullToEmpty(scanPackageName);
        this.scanPackageName = scanPackageName;
        this.packageNameWithDot = scanPackageName.endsWith(StringPool.DOT) ? scanPackageName : scanPackageName.concat(StringPool.DOT);
        this.scanPackageDirName = scanPackageName.replace(CharPool.DOT, File.separatorChar);
        this.scanPackagePath = scanPackageName.replace(CharPool.DOT, CharPool.SLASH);
        this.classFilter = classFilter;
        this.charset = charset;
    }

    /**
     * 扫描指定包路径下所有包含指定注解的类<br>
     * 如果classpath下已经有类，不再扫描其他加载的jar或者类
     *
     * @param scanPackageName 包路径
     * @param annotationClass 注解类
     * @return 类集合
     */
    public static Set<Class<?>> scanPackageByAnnotation(
            String scanPackageName, Class<? extends Annotation> annotationClass, boolean forceScanJavaClassPaths)
            throws IOException {
        return scanPackage(scanPackageName, clazz -> clazz.isAnnotationPresent(annotationClass), forceScanJavaClassPaths);
    }

    /**
     * 扫描指定包路径下所有指定类或接口的子类或实现类，不包括指定父类本身<br>
     * 如果classpath下已经有类，不再扫描其他加载的jar或者类
     *
     * @param packageName 包路径
     * @param superClass 父类或接口（不包括）
     * @return 类集合
     */
    public static Set<Class<?>> scanPackageBySuper(
            String packageName, Class<?> superClass, boolean forceScanJavaClassPaths)
            throws IOException {
        return scanPackage(packageName, clazz -> superClass.isAssignableFrom(clazz) && !superClass.equals(clazz), forceScanJavaClassPaths);
    }

    /**
     * 扫描满足class过滤器条件的所有class文件，<br>
     * 如果包路径为 com.abs + A.class 但是输入 abs会产生classNotFoundException<br>
     * 因为className 应该为 com.abs.A 现在却成为abs.A,此工具类对该异常进行忽略处理<br>
     * <p>
     * packageName为空时，扫描该包路径下所有class文件，包括其他加载的jar或者类
     *
     * @param scanPackageName 包路径 com | com. | com.abs | com.abs.
     * @param classFilter class过滤器，过滤掉不需要的class
     * @param forceScanJavaClassPaths 是否强制扫描其他位于classpath关联jar中的类
     * @return 类集合
     */
    public static Set<Class<?>> scanPackage(String scanPackageName, Filter<Class<?>> classFilter, boolean forceScanJavaClassPaths) throws IOException {
        return new ClassScannerUtil(scanPackageName, classFilter).scan(forceScanJavaClassPaths);
    }

    /**
     * 扫描包路径下满足class过滤器条件的所有class文件。
     * forceScanJavaClassPaths为false时，首先扫描指定包名下的资源目录，如果未扫描到，则扫描整个classpath中所有加载的类
     *
     * @param forceScanJavaClassPaths 是否强制扫描其他位于classpath关联jar中的类
     * @return 类集合
     */
    public Set<Class<?>> scan(boolean forceScanJavaClassPaths) throws IOException {
        final Enumeration<URL> resources = ClassLoaderUtil.getClassLoader().getResources(this.scanPackagePath);
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            switch (url.getProtocol()) {
                case "file":
                    scanFile(new File(URLDecoder.decode(url.getFile(), this.charset.name())), null);
                    break;
                case "jar":
                    JarURLConnection urlConnection = (JarURLConnection) url.openConnection();
                    scanJar(urlConnection.getJarFile());
                    break;
            }
        }
        // classpath下未找到，则扫描其他jar包下的类
        scanJavaClassPaths();
        // 扫描JDK
        scanJavaHomePaths();

        return Collections.unmodifiableSet(this.classes);
    }

    /**
     * 设置是否在扫描到类时初始化类
     *
     * @param initialize 是否初始化类
     */
    public void setInitialize(boolean initialize) {
        this.initialize = initialize;
    }

    /**
     * 设置自定义的类加载器
     *
     * @param classLoader 类加载器
     */
    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    // --------------------------------------------------------------------------------------------------- Private method start

    /**
     * 扫描JavaClassPath路径
     */
    private void scanJavaClassPaths() throws IOException {
        final String[] javaClassPaths = System.getProperty("java.class.path").split(System.getProperty("path.separator"));
        for (String classPath : javaClassPaths) {
            classPath = URLDecoder.decode(classPath, CharsetUtil.systemCharsetName());
            scanFile(new File(classPath), null);
        }
    }

    /**
     * 扫描JavaHome路径
     */
    private void scanJavaHomePaths() throws IOException {
        final String[] javaHomePaths = System.getProperty("java.home").split(System.getProperty("path.separator"));
        for (String classPath : javaHomePaths) {
            classPath = URLDecoder.decode(classPath, CharsetUtil.systemCharsetName());
            scanFile(new File(classPath), null);
        }
    }

    /**
     * 扫描文件或目录中的类
     *
     * @param file 文件或目录
     * @param rootDir 包名对应classpath绝对路径
     */
    private void scanFile(File file, String rootDir) throws IOException {
        if (file.isFile()) {
            final String fileName = file.getAbsolutePath();
            if (fileName.endsWith(StringPool.EXT_CLASS)) {
                final String className = fileName//
                        // 8为classes长度，fileName.length() - 6为".class"的长度
                        .substring(rootDir.length(), fileName.length() - 6)//
                        .replace(File.separatorChar, CharPool.DOT);//
                // 加入满足条件的类
                addIfAccept(className);
            } else if (fileName.endsWith(StringPool.EXT_JAR)) {
                scanJar(new JarFile(file));
            } else if (fileName.endsWith(StringPool.EXT_ZIP)) {
                scanZip(new ZipFile(file));
            }
        } else if (file.isDirectory()) {
            final File[] files = file.listFiles();
            if (null != files) {
                for (File subFile : files) {
                    scanFile(subFile, (null == rootDir) ? subPathBeforePackage(file) : rootDir);
                }
            }
        }
    }

    /**
     * 扫描jar包
     *
     * @param jar jar包
     */
    private void scanJar(JarFile jar) {
        String name;
        Enumeration<JarEntry> resources = jar.entries();
        while (resources.hasMoreElements()) {
            JarEntry entry = resources.nextElement();
            name = StringUtils.removeStart(entry.getName(), StringPool.SLASH);
            if (StringUtils.isEmpty(scanPackagePath) || name.startsWith(this.scanPackagePath)) {
                if (name.endsWith(StringPool.EXT_CLASS) && false == entry.isDirectory()) {
                    final String className = name//
                            .substring(0, name.length() - 6)//
                            .replace(CharPool.SLASH, CharPool.DOT);//
                    addIfAccept(loadClass(className));
                }
            }
        }
    }

    /**
     * 扫描zip包
     *
     * @param zip zip包
     */
    private void scanZip(ZipFile zip) {
        String name;
        Enumeration<ZipEntry> resources = (Enumeration<ZipEntry>) zip.entries();
        while (resources.hasMoreElements()) {
            ZipEntry entry = resources.nextElement();
            name = StringUtils.substringAfter(entry.getName(), StringPool.SLASH);
            if (StringUtils.isEmpty(scanPackagePath) || name.startsWith(this.scanPackagePath)) {
                if (name.endsWith(StringPool.EXT_JAVA) && false == entry.isDirectory()) {
                    final String className = name//
                            .substring(0, name.length() - 5)//
                            .replace(CharPool.SLASH, CharPool.DOT);//
                    addIfAccept(loadClass(className));
                }
            }
        }
    }

    /**
     * 加载类
     *
     * @param className 类名
     * @return 加载的类
     */
    private Class<?> loadClass(String className) {
        ClassLoader loader = this.classLoader;
        if (null == loader) {
            loader = ClassLoaderUtil.getClassLoader();
            this.classLoader = loader;
        }

        Class<?> clazz = null;
        try {
            clazz = Class.forName(className, this.initialize, loader);
        } catch (NoClassDefFoundError | ClassNotFoundException e) {
            // 由于依赖库导致的类无法加载，直接跳过此类
        } catch (UnsupportedClassVersionError e) {
            // 版本导致的不兼容的类，跳过
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return clazz;
    }

    /**
     * 通过过滤器，是否满足接受此类的条件
     *
     * @param className 类名
     */
    private void addIfAccept(String className) {
        if (StringUtils.isBlank(className)) {
            return;
        }
        int classLen = className.length();
        int packageLen = this.scanPackageName.length();
        if (classLen == packageLen) {
            // 类名和包名长度一致，用户可能传入的包名是类名
            if (className.equals(this.scanPackageName)) {
                addIfAccept(loadClass(className));
            }
        } else if (classLen > packageLen) {
            // 检查类名是否以指定包名为前缀，包名后加.（避免类似于cn.zhaolq.A和cn.zhaolq.ATest这类类名引起的歧义）
            if (".".equals(this.packageNameWithDot) || className.startsWith(this.packageNameWithDot)) {
                addIfAccept(loadClass(className));
            }
        }
    }

    /**
     * 通过过滤器，是否满足接受此类的条件
     *
     * @param clazz 类
     */
    private void addIfAccept(Class<?> clazz) {
        if (null != clazz) {
            Filter<Class<?>> classFilter = this.classFilter;
            if (classFilter == null || classFilter.accept(clazz)) {
                this.classes.add(clazz);
            }
        }
    }

    /**
     * 截取文件绝对路径中包名之前的部分
     *
     * @param file 文件
     * @return 包名之前的部分
     */
    private String subPathBeforePackage(File file) {
        String filePath = file.getAbsolutePath();
        if (StringUtils.isNotEmpty(this.scanPackageDirName)) {
            filePath = StrUtil.subBefore(filePath, this.scanPackageDirName, true);
        }
        return scanPackageName.endsWith(File.separator) ? filePath : filePath.concat(File.separator);
    }
    // --------------------------------------------------------------------------------------------------- Private method end

    public static void main(String[] args) throws IOException {
        Set<Class<?>> allUtils = ClassScannerUtil.scanPackage("", clazz -> {
            if (clazz.getName().toLowerCase(Locale.ROOT).contains("exception")) {
                return true;
            }
            return false;
        }, false);
        ConsoleTable consoleTable = ConsoleTable.create().setDBCMode(false).addHeader("PackageName", "SimpleName", "TypeName");
        for (Class<?> clazz : allUtils) {
            try {
                // 什么都没做，为了发生异常时能继续
                clazz.getSimpleName();
            } catch (Throwable throwable) {
                continue;
            }
            consoleTable.addBody(clazz.getPackageName(), clazz.getSimpleName(), clazz.getTypeName());
        }
        consoleTable.print(true);
    }
}
