package com.zhaolq.mars.common.core.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PushbackInputStream;
import java.io.PushbackReader;
import java.io.Reader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import org.apache.commons.lang3.Validate;

import com.zhaolq.mars.common.core.exception.IORuntimeException;
import com.zhaolq.mars.common.core.util.CharsetUtil;


/**
 * IO工具类<br>
 * IO工具类只是辅助流的读写，并不负责关闭流。原因是流可能被多次读写，读写关闭后容易造成问题。
 *
 * @Author zhaolq
 * @Date 2023/6/8 22:42
 */
public class IoUtil extends NioUtil {

    // -------------------------------------------------------------------------------------- getReader and getWriter start

    /**
     * 获得一个文件读取器，默认使用UTF-8编码
     *
     * @param in 输入流
     * @return BufferedReader对象
     */
    public static BufferedReader getUtf8Reader(InputStream in) {
        return getReader(in, StandardCharsets.UTF_8);
    }

    /**
     * 获得一个文件读取器
     *
     * @param in 输入流
     * @param charsetName 字符集名称
     * @return BufferedReader对象
     * @deprecated 请使用 {@link #getReader(InputStream, Charset)}
     */
    @Deprecated
    public static BufferedReader getReader(InputStream in, String charsetName) {
        return getReader(in, Charset.forName(charsetName));
    }

    /**
     * 获得一个Reader
     *
     * @param in 输入流
     * @param charset 字符集
     * @return BufferedReader对象
     */
    public static BufferedReader getReader(InputStream in, Charset charset) {
        if (null == in) {
            return null;
        }

        InputStreamReader reader;
        if (null == charset) {
            reader = new InputStreamReader(in);
        } else {
            reader = new InputStreamReader(in, charset);
        }

        return new BufferedReader(reader);
    }

    /**
     * 获得{@link BufferedReader}<br>
     * 如果是{@link BufferedReader}强转返回，否则新建。如果提供的Reader为null返回null
     *
     * @param reader 普通Reader，如果为null返回null
     * @return {@link BufferedReader} or null
     */
    public static BufferedReader getReader(Reader reader) {
        if (null == reader) {
            return null;
        }

        return (reader instanceof BufferedReader) ? (BufferedReader) reader : new BufferedReader(reader);
    }

    /**
     * 获得{@link PushbackReader}<br>
     * 如果是{@link PushbackReader}强转返回，否则新建
     *
     * @param reader 普通Reader
     * @param pushBackSize 推后的byte数
     * @return {@link PushbackReader}
     */
    public static PushbackReader getPushBackReader(Reader reader, int pushBackSize) {
        return (reader instanceof PushbackReader) ? (PushbackReader) reader : new PushbackReader(reader, pushBackSize);
    }

    /**
     * 获得一个Writer，默认编码UTF-8
     *
     * @param out 输入流
     * @return OutputStreamWriter对象
     */
    public static OutputStreamWriter getUtf8Writer(OutputStream out) {
        return getWriter(out, StandardCharsets.UTF_8);
    }

    /**
     * 获得一个Writer
     *
     * @param out 输入流
     * @param charsetName 字符集
     * @return OutputStreamWriter对象
     * @deprecated 请使用 {@link #getWriter(OutputStream, Charset)}
     */
    @Deprecated
    public static OutputStreamWriter getWriter(OutputStream out, String charsetName) {
        return getWriter(out, Charset.forName(charsetName));
    }

    /**
     * 获得一个Writer
     *
     * @param out 输入流
     * @param charset 字符集
     * @return OutputStreamWriter对象
     */
    public static OutputStreamWriter getWriter(OutputStream out, Charset charset) {
        if (null == out) {
            return null;
        }

        if (null == charset) {
            return new OutputStreamWriter(out);
        } else {
            return new OutputStreamWriter(out, charset);
        }
    }
    // -------------------------------------------------------------------------------------- getReader and getWriter end

    // -------------------------------------------------------------------------------------- read start

    /**
     * 从流中读取bytes
     *
     * @param in {@link InputStream}
     * @param isClose 是否关闭输入流
     * @return bytes
     * @throws IORuntimeException IO异常
     */
    public static byte[] readBytes(InputStream in, boolean isClose) throws IORuntimeException {
        if (in instanceof FileInputStream) {
            // 文件流的长度是可预见的，此时直接读取效率更高
            final byte[] result;
            try {
                final int available = in.available();
                result = new byte[available];
                final int readLength = in.read(result);
                if (readLength != available) {
                    throw new IOException(String.format("File length is [%d] but read [%d]!", available, readLength));
                }
            } catch (IOException e) {
                throw new IORuntimeException(e);
            } finally {
                if (isClose) {
                    close(in);
                }
            }
            return result;
        }
        return null;
    }

    /**
     * 从{@link Reader}中读取String
     *
     * @param reader {@link Reader}
     * @param isClose 是否关闭{@link Reader}
     * @return String
     * @throws IORuntimeException IO异常
     */
    public static String read(Reader reader, boolean isClose) throws IORuntimeException {
        final StringBuilder builder = new StringBuilder();
        final CharBuffer buffer = CharBuffer.allocate(DEFAULT_BUFFER_SIZE);
        try {
            while (-1 != reader.read(buffer)) {
                builder.append(buffer.flip());
            }
        } catch (IOException e) {
            throw new IORuntimeException(e);
        } finally {
            if (isClose) {
                IoUtil.close(reader);
            }
        }
        return builder.toString();
    }

    // -------------------------------------------------------------------------------------- read end

    /**
     * String 转为流
     *
     * @param content 内容
     * @param charsetName 编码
     * @return 字节流
     * @deprecated 请使用 {@link #toStream(String, Charset)}
     */
    @Deprecated
    public static ByteArrayInputStream toStream(String content, String charsetName) {
        return toStream(content, CharsetUtil.charset(charsetName));
    }

    /**
     * String 转为流
     *
     * @param content 内容
     * @param charset 编码
     * @return 字节流
     */
    public static ByteArrayInputStream toStream(String content, Charset charset) {
        if (content == null) {
            return null;
        }
        if (null == charset) {
            return toStream(content.toString().getBytes());
        }
        return toStream(content.toString().getBytes(charset));
    }

    /**
     * String 转为UTF-8编码的字节流流
     *
     * @param content 内容
     * @return 字节流
     */
    public static ByteArrayInputStream toUtf8Stream(String content) {
        return toStream(content, StandardCharsets.UTF_8);
    }

    /**
     * 文件转为{@link FileInputStream}
     *
     * @param file 文件
     * @return {@link FileInputStream}
     */
    public static FileInputStream toStream(File file) {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * byte[] 转为{@link ByteArrayInputStream}
     *
     * @param content 内容bytes
     * @return 字节流
     */
    public static ByteArrayInputStream toStream(byte[] content) {
        if (content == null) {
            return null;
        }
        return new ByteArrayInputStream(content);
    }

    /**
     * {@link ByteArrayOutputStream}转为{@link ByteArrayInputStream}
     *
     * @param out {@link ByteArrayOutputStream}
     * @return 字节流
     */
    public static ByteArrayInputStream toStream(ByteArrayOutputStream out) {
        if (out == null) {
            return null;
        }
        return new ByteArrayInputStream(out.toByteArray());
    }

    /**
     * 转换为{@link BufferedInputStream}
     *
     * @param in {@link InputStream}
     * @return {@link BufferedInputStream}
     */
    public static BufferedInputStream toBuffered(InputStream in) {
        Validate.notNull(in, "InputStream must be not null!");
        return (in instanceof BufferedInputStream) ? (BufferedInputStream) in : new BufferedInputStream(in);
    }

    /**
     * 转换为{@link BufferedInputStream}
     *
     * @param in {@link InputStream}
     * @param bufferSize buffer size
     * @return {@link BufferedInputStream}
     */
    public static BufferedInputStream toBuffered(InputStream in, int bufferSize) {
        Validate.notNull(in, "InputStream must be not null!");
        return (in instanceof BufferedInputStream) ? (BufferedInputStream) in : new BufferedInputStream(in, bufferSize);
    }

    /**
     * 转换为{@link BufferedOutputStream}
     *
     * @param out {@link OutputStream}
     * @return {@link BufferedOutputStream}
     */
    public static BufferedOutputStream toBuffered(OutputStream out) {
        Validate.notNull(out, "OutputStream must be not null!");
        return (out instanceof BufferedOutputStream) ? (BufferedOutputStream) out : new BufferedOutputStream(out);
    }

    /**
     * 转换为{@link BufferedOutputStream}
     *
     * @param out {@link OutputStream}
     * @param bufferSize buffer size
     * @return {@link BufferedOutputStream}
     */
    public static BufferedOutputStream toBuffered(OutputStream out, int bufferSize) {
        Validate.notNull(out, "OutputStream must be not null!");
        return (out instanceof BufferedOutputStream) ? (BufferedOutputStream) out : new BufferedOutputStream(out, bufferSize);
    }

    /**
     * 转换为{@link BufferedReader}
     *
     * @param reader {@link Reader}
     * @return {@link BufferedReader}
     */
    public static BufferedReader toBuffered(Reader reader) {
        Validate.notNull(reader, "Reader must be not null!");
        return (reader instanceof BufferedReader) ? (BufferedReader) reader : new BufferedReader(reader);
    }

    /**
     * 转换为{@link BufferedReader}
     *
     * @param reader {@link Reader}
     * @param bufferSize buffer size
     * @return {@link BufferedReader}
     */
    public static BufferedReader toBuffered(Reader reader, int bufferSize) {
        Validate.notNull(reader, "Reader must be not null!");
        return (reader instanceof BufferedReader) ? (BufferedReader) reader : new BufferedReader(reader, bufferSize);
    }

    /**
     * 转换为{@link BufferedWriter}
     *
     * @param writer {@link Writer}
     * @return {@link BufferedWriter}
     */
    public static BufferedWriter toBuffered(Writer writer) {
        Validate.notNull(writer, "Writer must be not null!");
        return (writer instanceof BufferedWriter) ? (BufferedWriter) writer : new BufferedWriter(writer);
    }

    /**
     * 转换为{@link BufferedWriter}
     *
     * @param writer {@link Writer}
     * @param bufferSize buffer size
     * @return {@link BufferedWriter}
     */
    public static BufferedWriter toBuffered(Writer writer, int bufferSize) {
        Validate.notNull(writer, "Writer must be not null!");
        return (writer instanceof BufferedWriter) ? (BufferedWriter) writer : new BufferedWriter(writer, bufferSize);
    }

    /**
     * 将{@link InputStream}转换为支持mark标记的流<br>
     * 若原流支持mark标记，则返回原流，否则使用{@link BufferedInputStream} 包装之
     *
     * @param in 流
     * @return {@link InputStream}
     */
    public static InputStream toMarkSupportStream(InputStream in) {
        if (null == in) {
            return null;
        }
        if (false == in.markSupported()) {
            return new BufferedInputStream(in);
        }
        return in;
    }

    /**
     * 转换为{@link PushbackInputStream}<br>
     * 如果传入的输入流已经是{@link PushbackInputStream}，强转返回，否则新建一个
     *
     * @param in {@link InputStream}
     * @param pushBackSize 推后的byte数
     * @return {@link PushbackInputStream}
     */
    public static PushbackInputStream toPushbackStream(InputStream in, int pushBackSize) {
        return (in instanceof PushbackInputStream) ? (PushbackInputStream) in : new PushbackInputStream(in, pushBackSize);
    }

    /**
     * 将指定{@link InputStream} 转换为{@link InputStream#available()}方法可用的流。<br>
     * 在Socket通信流中，服务端未返回数据情况下{@link InputStream#available()}方法始终为{@code 0}<br>
     * 因此，在读取前需要调用{@link InputStream#read()}读取一个字节（未返回会阻塞），一旦读取到了，{@link InputStream#available()}方法就正常了。<br>
     * 需要注意的是，在网络流中，是按照块来传输的，所以 {@link InputStream#available()} 读取到的并非最终长度，而是此次块的长度。<br>
     * 此方法返回对象的规则为：
     *
     * <ul>
     *     <li>FileInputStream 返回原对象，因为文件流的available方法本身可用</li>
     *     <li>其它InputStream 返回PushbackInputStream</li>
     * </ul>
     *
     * @param in 被转换的流
     * @return 转换后的流，可能为{@link PushbackInputStream}
     */
    public static InputStream toAvailableStream(InputStream in) {
        if (in instanceof FileInputStream) {
            // FileInputStream本身支持available方法。
            return in;
        }

        final PushbackInputStream pushbackInputStream = toPushbackStream(in, 1);
        try {
            final int available = pushbackInputStream.available();
            if (available <= 0) {
                //此操作会阻塞，直到有数据被读到
                int b = pushbackInputStream.read();
                pushbackInputStream.unread(b);
            }
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }

        return pushbackInputStream;
    }

    /**
     * 将byte[]写到流中
     *
     * @param out 输出流
     * @param isCloseOut 写入完毕是否关闭输出流
     * @param content 写入的内容
     * @throws IORuntimeException IO异常
     */
    public static void write(OutputStream out, boolean isCloseOut, byte[] content) throws IORuntimeException {
        try {
            out.write(content);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        } finally {
            if (isCloseOut) {
                close(out);
            }
        }
    }

    /**
     * 将多部分内容写到流中
     *
     * @param out 输出流
     * @param isCloseOut 写入完毕是否关闭输出流
     * @param obj 写入的对象内容
     * @throws IORuntimeException IO异常
     */
    public static void writeObj(OutputStream out, boolean isCloseOut, Serializable obj) throws IORuntimeException {
        writeObjects(out, isCloseOut, obj);
    }

    /**
     * 将多部分内容写到流中
     *
     * @param out 输出流
     * @param isCloseOut 写入完毕是否关闭输出流
     * @param contents 写入的内容
     * @throws IORuntimeException IO异常
     */
    public static void writeObjects(OutputStream out, boolean isCloseOut, Serializable... contents) throws IORuntimeException {
        ObjectOutputStream osw = null;
        try {
            osw = out instanceof ObjectOutputStream ? (ObjectOutputStream) out : new ObjectOutputStream(out);
            for (Object content : contents) {
                if (content != null) {
                    osw.writeObject(content);
                }
            }
            osw.flush();
        } catch (IOException e) {
            throw new IORuntimeException(e);
        } finally {
            if (isCloseOut) {
                close(osw);
            }
        }
    }

    /**
     * 从缓存中刷出数据
     *
     * @param flushable {@link Flushable}
     */
    public static void flush(Flushable flushable) {
        if (null != flushable) {
            try {
                flushable.flush();
            } catch (Exception e) {
                // 静默刷出
            }
        }
    }

    /**
     * 关闭<br>
     * 关闭失败不会抛出异常
     *
     * @param closeable 被关闭的对象
     */
    public static void close(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (Exception e) {
                // 静默关闭
            }
        }
    }

    /**
     * 尝试关闭指定对象<br>
     * 判断对象如果实现了{@link AutoCloseable}，则调用之
     *
     * @param obj 可关闭对象
     */
    public static void closeIfPosible(Object obj) {
        if (obj instanceof AutoCloseable) {
            close((AutoCloseable) obj);
        }
    }

    /**
     * 对比两个流内容是否相同<br>
     * 内部会转换流为 {@link BufferedInputStream}
     *
     * @param input1 第一个流
     * @param input2 第二个流
     * @return 两个流的内容一致返回true，否则false
     * @throws IORuntimeException IO异常
     */
    public static boolean contentEquals(InputStream input1, InputStream input2) throws IORuntimeException {
        if (false == (input1 instanceof BufferedInputStream)) {
            input1 = new BufferedInputStream(input1);
        }
        if (false == (input2 instanceof BufferedInputStream)) {
            input2 = new BufferedInputStream(input2);
        }

        try {
            int ch = input1.read();
            while (EOF != ch) {
                int ch2 = input2.read();
                if (ch != ch2) {
                    return false;
                }
                ch = input1.read();
            }

            int ch2 = input2.read();
            return ch2 == EOF;
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * 对比两个Reader的内容是否一致<br>
     * 内部会转换流为 {@link BufferedInputStream}
     *
     * @param input1 第一个reader
     * @param input2 第二个reader
     * @return 两个流的内容一致返回true，否则false
     * @throws IORuntimeException IO异常
     */
    public static boolean contentEquals(Reader input1, Reader input2) throws IORuntimeException {
        input1 = getReader(input1);
        input2 = getReader(input2);

        try {
            int ch = input1.read();
            while (EOF != ch) {
                int ch2 = input2.read();
                if (ch != ch2) {
                    return false;
                }
                ch = input1.read();
            }

            int ch2 = input2.read();
            return ch2 == EOF;
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * 对比两个流内容是否相同，忽略EOL字符<br>
     * 内部会转换流为 {@link BufferedInputStream}
     *
     * @param input1 第一个流
     * @param input2 第二个流
     * @return 两个流的内容一致返回true，否则false
     * @throws IORuntimeException IO异常
     */
    public static boolean contentEqualsIgnoreEOL(Reader input1, Reader input2) throws IORuntimeException {
        final BufferedReader br1 = getReader(input1);
        final BufferedReader br2 = getReader(input2);

        try {
            String line1 = br1.readLine();
            String line2 = br2.readLine();
            while (line1 != null && line1.equals(line2)) {
                line1 = br1.readLine();
                line2 = br2.readLine();
            }
            return Objects.equals(line1, line2);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * {@link ByteArrayOutputStream} 转换为String
     *
     * @param out {@link ByteArrayOutputStream}
     * @param charset 编码
     * @return 字符串
     */
    public static String toStr(ByteArrayOutputStream out, Charset charset) {
        try {
            return out.toString(charset.name());
        } catch (UnsupportedEncodingException e) {
            throw new IORuntimeException(e);
        }
    }
}
