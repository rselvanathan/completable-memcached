package com.romsel.memcached.operations;

import net.spy.memcached.ops.OperationStatus;
import net.spy.memcached.ops.StoreOperation;

import java.util.concurrent.CompletableFuture;

/**
 * @author Romesh Selvan
 */
public class SetOperation implements StoreOperation.Callback {

    private final CompletableFuture<Boolean> future;

    public SetOperation(CompletableFuture<Boolean> future) {
        this.future = future;
    }

    @Override
    public void gotData(String s, long l) {
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
