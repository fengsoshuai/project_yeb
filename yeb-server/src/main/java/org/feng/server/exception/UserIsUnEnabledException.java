package org.feng.server.exception;

/**
 * 用户被禁用异常
 */
public class UserIsUnEnabledException extends RuntimeException {
    public UserIsUnEnabledException() {
    }

    public UserIsUnEnabledException(String message) {
        super(message);
    }
}
