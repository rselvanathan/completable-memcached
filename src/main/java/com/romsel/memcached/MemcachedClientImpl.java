package com.romsel.memcached;

import net.spy.memcached.ConnectionFactory;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Implementation for {@link MemcachedClient}
 *
 * @author Romesh Selvan
 */
public class MemcachedClientImpl implements MemcachedClient {

    private final SpyMemcachedClient spyMemcachedClient;

    protected MemcachedClientImpl(InetSocketAddress... addrs) throws IOException {
        spyMemcachedClient = new SpyMemcachedClient(addrs);
    }

    protected MemcachedClientImpl(List<InetSocketAddress> addrs) throws IOException {
        spyMemcachedClient = new SpyMemcachedClient(addrs);
    }

    protected MemcachedClientImpl(ConnectionFactory cf, List<InetSocketAddress> addrs) throws IOException {
        spyMemcachedClient = new SpyMemcachedClient(cf, addrs);
    }

    @Override
    public <T extends Serializable> CompletableFuture<Optional<T>> get(String key) {
        return spyMemcachedClient.find(key);
    }

    @Override
    public <T extends Serializable> CompletableFuture<Boolean> set(String key, T object) {
        return spyMemcachedClient.set(key, object);
    }

    @Override
    public <T extends Serializable> CompletableFuture<Boolean> set(String key, T object, Long timeToLiveInMs) {
        return spyMemcachedClient.set(key, object, timeToLiveInMs);
    }

    @Override
    public CompletableFuture<Boolean> delete(String key) {
        return spyMemcachedClient.deleteKey(key);
    }

    @Override
    public void shutdown() {
        spyMemcachedClient.shutdown();
    }

    @Override
    public void shutdownTimed(long timeout, TimeUnit timeUnit) {
        spyMemcachedClient.shutdown(timeout, timeUnit);
    }
}
