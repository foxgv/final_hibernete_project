package com.javarush.utilRedis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;

import static java.util.Objects.nonNull;

public class PrepareRedisClient {

    static RedisClient redisClient;
    public RedisClient prepareRedisClient() {
        redisClient = RedisClient.create(RedisURI.create("localhost", 6379));
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        try (connection) {
            System.out.println("\nConnected to Redis\n");
        }
        return redisClient;
    }

    public static void shutdownRedisClient() {
        if (nonNull(redisClient)) {
            redisClient.shutdown();
        }
    }
}
