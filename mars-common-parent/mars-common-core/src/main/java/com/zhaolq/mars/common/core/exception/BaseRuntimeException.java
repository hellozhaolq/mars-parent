package com.zhaolq.mars.common.core.exception;

import com.zhaolq.mars.common.core.result.ErrorCode;
import com.zhaolq.mars.common.core.result.ICode;

/**
 * 运行时异常，常用于对RuntimeException的包装。此类为JDK已定义异常的补集。
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
 *      throw上抛，但尽量是BaseRuntimeException的子类。
 * 3、捕获多种具体异常时，如果处理逻辑相同，必须用并语法(ExceptionType| ...| ExceptionType 变量）来减少重复代码。
 * 4、工具方法中可以捕获异常，也可以上抛给业务层处理。
 * 5、业务代码可通过抛出异常的方式响应API请求，web框架拦截处理 基类Exception、BaseRuntimeException
 * </pre>
 *
 * @Author zhaolq
 * @Date 2024/6/14 14:57:34
 */
public class BaseRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private int code;

    public BaseRuntimeException() {
        super();
        this.code = ErrorCode.SYSTEM_ERROR.getCode();
    }

    public BaseRuntimeException(String message) {
        super(message);
        this.code = ErrorCode.SYSTEM_ERROR.getCode();
    }

    public BaseRuntimeException(String message, Throwable cause) {
        super(message, cause);
        this.code = ErrorCode.SYSTEM_ERROR.getCode();
    }

    public BaseRuntimeException(Throwable cause) {
        super();
        this.code = ErrorCode.SYSTEM_ERROR.getCode();
    }

    public BaseRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = ErrorCode.SYSTEM_ERROR.getCode();
    }


    public BaseRuntimeException(ICode code) {
        super(code.getMsg());
        this.code = code.getCode();
    }

    public BaseRuntimeException(String message, ICode code) {
        super(message);
        this.code = code.getCode();
    }

    public BaseRuntimeException(String message, Throwable cause, ICode code) {
        super(message, cause);
        this.code = code.getCode();
    }

    public BaseRuntimeException(Throwable cause, ICode code) {
        super(cause);
        this.code = code.getCode();
    }

    public BaseRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ICode code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code.getCode();
    }
}
