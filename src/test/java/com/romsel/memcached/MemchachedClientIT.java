package com.romsel.memcached;

import com.romsel.memcached.defaults.MemcachedConstants;
import de.flapdoodle.embed.memcached.Command;
import de.flapdoodle.embed.memcached.MemcachedExecutable;
import de.flapdoodle.embed.memcached.MemcachedStarter;
import de.flapdoodle.embed.memcached.config.MemcachedConfig;
import de.flapdoodle.embed.memcached.config.RuntimeConfigBuilder;
import de.flapdoodle.embed.memcached.distribution.Version;
import de.flapdoodle.embed.process.config.IRuntimeConfig;
import org.hamcrest.CoreMatchers;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Romesh Selvan
 */
@RunWith(JUnit4.class)
public class MemchachedClientIT {

    private final int port = 11211;
    private static MemcachedExecutable memcachedExecutable;
    private static MemcachedClient client;

    public MemchachedClientIT() throws IOException {
        IRuntimeConfig iRuntimeConfig = new RuntimeConfigBuilder().defaults(Command.MemcacheD).build();
        MemcachedStarter runtime = MemcachedStarter.getInstance(iRuntimeConfig);
        memcachedExecutable = runtime.prepare(new MemcachedConfig(Version.Main.PRODUCTION, port));
        memcachedExecutable.start();

        String endpoint = "localhost:"+port;
        client = MemcachedClientProducer.build(endpoint);
    }

    @AfterClass
    public static void after() {
        client.shutdown();
        memcachedExecutable.stop();
    }

    @Test
    public void testClient() {
        String value = "testingValue";
        String objectId = "objectId";

        TestObject object = new TestObject(value);

        // Get
        CompletableFuture<Boolean> setFuture = client.set(objectId, object, MemcachedConstants.ONE_MINUTE);
        assertThat("Item was not added successfully", setFuture.join(), is(true));

        // Set
        CompletableFuture<Optional<TestObject>> futureGet = client.get(objectId);
        Optional<TestObject> testObject = futureGet.join();
        assertThat("Expected item to be in the memcache", testObject.isPresent(), CoreMatchers.is(true));
        assertThat(testObject.get(), is(object));

        // Delete
        CompletableFuture<Boolean> deleteFuture = client.delete(objectId);
        Assert.assertThat("The delete operation did not work", deleteFuture.join(), CoreMatchers.is(true));

        // Check Delete
        CompletableFuture<Optional<Serializable>> deleteCheckFuture = client.get(objectId);
        Optional<Serializable> deleteCheck = deleteCheckFuture.join();
        Assert.assertThat("Expected the item not to be there", deleteCheck.isPresent(), CoreMatchers.is(false));
    }
}
