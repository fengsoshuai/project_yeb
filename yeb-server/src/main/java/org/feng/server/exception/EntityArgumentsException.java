package org.feng.server.exception;

/**
 * 实体参数异常
 * @author FengJinSong
 */
public class EntityArgumentsException extends IllegalArgumentException {
    public EntityArgumentsException() {
    }

    public EntityArgumentsException(String s) {
        super(s);
    }

    public EntityArgumentsException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityArgumentsException(Throwable cause) {
        super(cause);
    }
}
