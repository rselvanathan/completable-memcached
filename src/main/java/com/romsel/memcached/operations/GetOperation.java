package com.romsel.memcached.operations;


import net.spy.memcached.CachedData;
import net.spy.memcached.ops.OperationStatus;
import net.spy.memcached.transcoders.Transcoder;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Will map the Data from the client to expected object if the object has been found.
 *
 * @author Romesh Selvan
 */
public class GetOperation<T> implements net.spy.memcached.ops.GetOperation.Callback {

    private final CompletableFuture<Optional<T>> future;
    private final Transcoder<Object> transcoder;

    public GetOperation(CompletableFuture<Optional<T>> future, Transcoder<Object> transcoder) {
        this.future = future;
        this.transcoder = transcoder;
    }


    @Override
    public void gotData(String s, int flags, byte[] bytes) {
        Object decodedObject = transcoder.decode(new CachedData(flags, bytes, transcoder.getMaxSize()));
        //noinspection unchecked
        future.complete(Optional.of((T)decodedObject));
    }

    @Override
    public void receivedStatus(OperationStatus status) {
        if(!status.isSuccess()) {
            future.complete(Optional.empty());
        }
    }

    @Override
    public void complete() {
        // Do nothing
    }
}
