package com.romsel.memcached;

import java.io.Serializable;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Memcached client
 *
 * @author Romesh Selvan
 */
public interface MemcachedClient {
    /**
     * Get a mapped object from memcached
     *
     * @param key - the Key to look up
     * @param <T> - The object type to return
     * @return Optional object wrapped in a CompletableFuture
     */
    <T extends Serializable> CompletableFuture<Optional<T>> get(String key);

    /**
     * Serialize an object and store it using the configured default time to live value
     *
     * @param key - the key to add
     * @param object - the object value to add
     * @param <T> The object type to add
     * @return a future with a boolean that will be false when object has not been saved
     */
    <T extends Serializable> CompletableFuture<Boolean> set(String key, T object);

    /**
     * Serialize an object and send it to memcached
     *
     * @param key - the key to add
     * @param object - the object value to add
     * @param timeToLiveInMs - Time To live in Milliseconds
     * @param <T> The object type to add
     * @return a future with a boolean that will be false when object has not been saved
     */
    <T extends Serializable> CompletableFuture<Boolean> set(String key, T object, Long timeToLiveInMs);


    /**
     * Delete entity with key from cache
     * @param key - the key the deleteKey
     * @return a future with a boolean that will be false when object has not been deleted
     */
    CompletableFuture<Boolean> delete(String key);

    /**
     * Shutdown the Memcached client Daemon and clean up resources
     */
    void shutdown();

    /**
     * Shutdown the Memcached client Daemon and clean up resources after a specified amount of time
     */
    void shutdownTimed(long timeout, TimeUnit timeUnit);
}
