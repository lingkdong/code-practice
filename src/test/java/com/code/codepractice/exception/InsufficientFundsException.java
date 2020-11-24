package com.code.codepractice.exception;

/**
 *sufficient:[səˈfiSHənt] 足够
 * insufficient 不足
 * funds 资金
 * InsufficientFundsException:资金不足异常
 */
public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException() {
    }

    public InsufficientFundsException(String message) {
        super(message);
    }

    public InsufficientFundsException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsufficientFundsException(Throwable cause) {
        super(cause);
    }

    public InsufficientFundsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
