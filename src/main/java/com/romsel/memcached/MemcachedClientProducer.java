package com.romsel.memcached;

import com.romsel.memcached.exceptions.MemcachedClientException;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Will procude a {@link MemcachedClient} object
 *
 * @author Romesh Selvan
 */
public class MemcachedClientProducer {

    public static MemcachedClient build(String endpoint) {
        try {
            String[] endpointValues = endpoint.split(":");
            return new MemcachedClientImpl(new InetSocketAddress(endpointValues[0], Integer.parseInt(endpointValues[1])));
        } catch (IOException e) {
            throw new MemcachedClientException("Error occured when building the memcached client", e);
        }
    }
}
