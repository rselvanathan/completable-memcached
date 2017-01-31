package com.romsel.memcached;

import com.romsel.memcached.exceptions.MemcachedClientException;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Will produce a {@link MemcachedClient} object. This should be used when trying to generate a client.
 *
 * @author Romesh Selvan
 */
public class MemcachedClientProducer {

    /**
     * Will generatea {@link MemcachedClient}
     *
     * @param endpoint - The endpoint int the format "host:port"
     * @return the client itself.
     */
    public static MemcachedClient build(String endpoint) {
        try {
            String[] endpointValues = endpoint.split(":");
            return new MemcachedClientImpl(new InetSocketAddress(endpointValues[0], Integer.parseInt(endpointValues[1])));
        } catch (IOException e) {
            throw new MemcachedClientException("Error occured when building the memcached client", e);
        }
    }
}
