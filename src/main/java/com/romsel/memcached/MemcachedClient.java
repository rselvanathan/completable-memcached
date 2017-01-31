package com.romsel.memcached;

import com.romsel.memcached.defaults.MemcachedConstants;

import java.io.Serializable;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * The Memcached client which will wrap the results in a {@link CompletableFuture}. The three types of methods supported
 * in this client are :
 * - Get the object
 * - Set the object (either with default time to live which is One Week or set a time to live using the {@link MemcachedConstants}
 * - Delete an object
 *
 * Once the user has finished with using the client (e.g the user's application is shutting down) It is suggessted to call
 * the "shutdown" methods to cleanly close the resources that the Memcached client will be using.
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
     *
     * @param timeout the timout value
     * @param timeUnit the time unit {@link TimeUnit}
     */
    void shutdownTimed(long timeout, TimeUnit timeUnit);
}
