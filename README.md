## Completable Memcached

An asynchronous Java 8 Memcached client, using Java 8 Completable Futures throughout the API. It is built on top of spymemchached, which is
an excellent and fast memchached client implementation : https://github.com/couchbase/spymemcached

#### Build Status
(Soon to come)

#### Maven central
(Soon to come)

##### Build Dependencies

- Java 8 or higher
- Maven 3.3.3

##### Project Usage

This project is meant to be used a library embedded in other software.

To import it with maven use this :

(To come soon)

##### Code Usage

Creating the client :

```java
MemcachedClient client = MemcachedClientProducer.build("HOST:PORT");
```

Closing the client :

```java
client.shutdown();
// Or timed shutdown
client shutdown(60L, TimeUnit.SECONDS);
```

Using the client :

```java
// Get an object of object type "String" 
String cacheKey = "key";
CompletableFuture<Optional<String>> cachedObjectResponse = client.get(cacheKey);
```
```java
// Set an object
String data = "data"
String cacheKeyToAdd = "key"

// If the "time to live value" is not specified, the default of ONE_WEEK will be used
CompletableFuture<Boolean> objectAddedResponse = client.set(cacheKeyToAdd, data, MemcachedConstants.ONE_DAY);
// Or with default time to live
CompletableFuture<Boolean> objectAddedResponse = client.set(cacheKeyToAdd, data);
```
```java
// Delete and object in the cache
String cacheKeyToDelete = "key";
CompletableFuture<Boolean> objectAddedResponse = client.delete(cacheKeyToAdd, data);
```

An example of using CompletableFutures to chain together memcached and other external requests :

```java
 public Item getItem(String itemId) {
        CompletableFuture<Optional<Item>> cachedObjectResponse = memcachedClient.get(itemId);
        CompletableFuture<Item> resultFuture = cachedObjectResponse.thenApply(response -> {
           if(response.isPresent()) {
               return CompletableFuture.completedFuture(response.get());
           }

           // Perform an REST API request - just an example usage if item is not found in the cache
           CompletableFuture<Item> apiResultFuture = someRestEndpointClient.get(itemId);

            apiResultFuture.whenComplete((apiResult, ex) -> memcachedClient.set(itemId, apiResult, MemcachedConstants.ONE_WEEK));

           return apiResultFuture;
        });
        return resultFuture.join():
    }
```

##### Author 
Romesh Selvanathan
