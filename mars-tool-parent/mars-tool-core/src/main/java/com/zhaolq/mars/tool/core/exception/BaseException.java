/**
 * 抽象异常基类
 *
 * @author zwx1085453
 * @since 2022/1/30 8:31
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
