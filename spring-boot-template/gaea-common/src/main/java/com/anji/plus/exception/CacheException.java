package com.anji.plus.exception;

/**
 * <pre>
 * CacheException
 * </pre>
 * @author peiyanni
 * @version CacheException.java,
 */
public class CacheException extends RuntimeException {
    private static final long serialVersionUID = 4376793843747815643L;
    private String            msg;

    public CacheException(Exception ex, String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
