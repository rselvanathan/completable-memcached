package com.romsel.memcached.operations;

import net.spy.memcached.ops.DeleteOperation;
import net.spy.memcached.ops.OperationStatus;

import java.util.concurrent.CompletableFuture;

/**
 * Will return a completed future once the call has been successful
 *
 * @author Romesh Selvan
 */
public class RemoveOperation implements DeleteOperation.Callback {

    private final CompletableFuture<Boolean> future;

    public RemoveOperation(CompletableFuture<Boolean> future) {
        this.future = future;
    }

    @Override
    public void gotData(long l) {
        // Do Nothing
    }

    @Override
    public void receivedStatus(OperationStatus status) {
        future.complete(status.isSuccess());
    }

    @Override
    public void complete() {
        // Do Nothing
    }
}
