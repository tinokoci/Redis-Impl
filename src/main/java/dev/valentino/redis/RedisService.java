package dev.valentino.redis;

import lombok.AccessLevel;
import lombok.Getter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

@Getter(AccessLevel.PACKAGE)
public class RedisService {

    @Getter(AccessLevel.NONE)
    private final JedisPool subscriberPool, publisherPool;
    private final Map<String, RedisSubscriber<?>> subscribers = new HashMap<>();

    private final String channel = "channel";
    private final String splitChar = ";";

    public RedisService(String address, int port) {
        subscriberPool = new JedisPool(address, port);
        publisherPool = new JedisPool(address, port);

        Executors.newSingleThreadExecutor().execute(() -> {
            try (Jedis jedis = subscriberPool.getResource()) {
                jedis.subscribe(new RedisPubSub(this), channel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public <T> void subscribe(RedisMessage message, Class<T> clazz, TypeCallback<T> callback) {
        subscribers.put(message.name(), new RedisSubscriber<>(clazz, callback));
    }

    public void publish(RedisMessage type, Object object) {
        try (Jedis jedis = publisherPool.getResource()) {
            try {
                jedis.publish(channel, type.name() + splitChar + GsonUtil.toJson(object));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
