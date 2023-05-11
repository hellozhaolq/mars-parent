package com.zhaolq.mars.tool.core.exception;

import com.zhaolq.mars.tool.core.result.IResultCode;

/**
 * 此类为JDK已定义异常的补集。
 * <p>非检查异常（unckecked exception）：Error 和 RuntimeException 以及他们的子类。
 * <p>检查异常（checked exception）：除了Error 和 RuntimeException的其它异常。
 * <p>Java异常结构及常用异常(持续补充...)：
 * <pre>
 * Throwable
 * ├─ Error
 * │  ├─ VirtualMachineError
 * │  │  ├─ StackOverFlowError
 * │  │  └─ OutOfMemoryError
 * │  └─ AWTError
 * ├─ Exception
 * │  ├─ RuntimeException
 * │  │  ├─ NullPointerException
 * │  │  ├─ IndexOutOfBoundsException
 * │  │  ├─ SecurityException
 * │  │  └─ IllegalArgumentException
 * │  │     └─ NumberFormatException
 * │  ├─ IOException
 * │  │  ├─ UnsupportedCharsetException
 * │  │  ├─ FileNotFoundException
 * │  │  └─ SocketException
 * │  ├─ ParseException
 * │  ├─ GeneralSecurityException
 * │  ├─ SQLException
 * └─ └─ TimeoutException
 * </pre>
 * <p>如果检查异常可以由方法或构造函数的执行引发并传播到方法或构造函数边界之外，则需要在方法或构造函数的throws子句中声明这些异常，也可以try..catch..捕获
 * <pre>
 *     public void exception() throws Exception {
 * 	       throw new Exception();
 *     }
 * </pre>
 * <pre>编码建议：
 * 1、不允许直接捕获受检异常的基类Exception，要在方法或构造函数的throws子句中声明抛出。
 * 2、捕获的非基类Exception处理
 *      打印异常msg。
 *      throw上抛，但尽量是BaseException的子类。
 * 3、捕获多种具体异常时，如果处理逻辑相同，必须用并语法(ExceptionType| ...| ExceptionType 变量）来减少重复代码。
 * 4、工具方法中不可捕获异常，上抛给业务层处理。
 * 5、业务代码可通过抛出异常的方式响应API请求，web框架拦截处理 基类Exception、BaseException
 * </pre>
 *
 * @author zhaolq
 * @date 2022/1/30 8:31
 */
public abstract class BaseException extends RuntimeException {
    private IResultCode resultCode = this.setExceptionResultCode();

    public BaseException() {
        super();
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Object... params) {
        super(String.format(message, params));
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public IResultCode getResultCode() {
        return this.resultCode;
    }

    protected abstract IResultCode setExceptionResultCode();
}
