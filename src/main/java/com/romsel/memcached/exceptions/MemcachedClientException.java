package com.romsel.memcached.exceptions;

/**
 * A exception thrown when an error occurs with the Memcached client
 *
 * @author Romesh Selvan
 */
public class MemcachedClientException extends RuntimeException {
    public MemcachedClientException(String message, Exception e) {
        super(message, e);
    }
}
