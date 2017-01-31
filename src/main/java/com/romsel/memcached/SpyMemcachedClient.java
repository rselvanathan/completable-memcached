package com.romsel.memcached;

import com.romsel.memcached.defaults.MemcachedConstants;
import com.romsel.memcached.operations.GetOperation;
import com.romsel.memcached.operations.RemoveOperation;
import com.romsel.memcached.operations.SetOperation;
import net.spy.memcached.CachedData;
import net.spy.memcached.ConnectionFactory;
import net.spy.memcached.ops.DeleteOperation;
import net.spy.memcached.ops.StoreOperation;
import net.spy.memcached.ops.StoreType;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Integration client which will expose spymemached's client functionality. Will use low level callback functions
 * and wrap the results in a {@link CompletableFuture}
 *
 * @author Romesh Selvan
 */
public class SpyMemcachedClient extends net.spy.memcached.MemcachedClient {

    protected SpyMemcachedClient(InetSocketAddress... addrs) throws IOException {
        super(addrs);
    }

    protected SpyMemcachedClient(List<InetSocketAddress> addrs) throws IOException {
        super(addrs);
    }

    protected SpyMemcachedClient(ConnectionFactory cf, List<InetSocketAddress> addrs) throws IOException {
        super(cf, addrs);
    }

    /**
     * Find and retrieve the cached object. Return an {@link Optional} empty if the item is not found
     *
     * @param key The key to find
     * @param <T> The Object type
     * @return a {@link CompletableFuture} wrapping a {@link Optional} which wraps the object to be returned
     */
    public <T extends Serializable> CompletableFuture<Optional<T>> find(String key) {
        CompletableFuture<Optional<T>> future = new CompletableFuture<>();
        net.spy.memcached.ops.GetOperation getOperation =
            this.opFact.get(key, new GetOperation<T>(future, this.transcoder));
        mconn.enqueueOperation(key, getOperation);
        return future;
    }

    /**
     * Set an object into the cache with a key and a maximum time to live within the cache
     *
     * @param key the key to store the object under
     * @param object the object to be stored
     * @param timeToLiveInMs how long the object should stay stored.
     * @param <T> The type of the object being stored
     * @return A {@link CompletableFuture} whose result will let the user know whether or not the save was successful
     */
    public <T extends Serializable> CompletableFuture<Boolean> set(String key, T object, Long timeToLiveInMs) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        CachedData cachedData = this.transcoder.encode(object);
        StoreOperation storeOperation = this.opFact
            .store(StoreType.set, key, cachedData.getFlags(), timeToLiveInMs.intValue(), cachedData.getData(), new SetOperation(future));
        mconn.enqueueOperation(key, storeOperation);
        return future;
    }

    /**
     * Set an object into the cache with a key. The default time to live is set to one week.
     *
     * @param key the key to store the object under
     * @param object the object to be stored
     * @param <T> The type of the object being stored
     * @return A {@link CompletableFuture} whose result will let the user know whether or not the save was successful
     */
    public <T extends Serializable> CompletableFuture<Boolean> set(String key, T object) {
       return set(key, object, MemcachedConstants.ONE_WEEK);
    }

    /**
     * Delete the object stored under the given key
     *
     * @param key the key object to delete
     * @return A {@link CompletableFuture} whose result will let the user know whether or not the delete was successful
     */
    public CompletableFuture<Boolean> deleteKey(String key) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        DeleteOperation deleteOperation = this.opFact.delete(key, new RemoveOperation(future));
        mconn.enqueueOperation(key, deleteOperation);
        return future;
    }
}
