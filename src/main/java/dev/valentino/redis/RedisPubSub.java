package dev.valentino.redis;

import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import redis.clients.jedis.JedisPubSub;

import java.util.Arrays;

@RequiredArgsConstructor
public class RedisPubSub extends JedisPubSub {

    private final RedisService redisService;

    @Override
    public void onMessage(String channel, String channelMessage) {
        if (!channel.equals(redisService.getChannel())) return;

        String[] args = channelMessage.split(redisService.getSplitChar());
        if (args.length < 2) return;

        Arrays.stream(RedisMessage.values())
                .filter(redisMessage -> redisMessage.name().equals(args[0]))
                .findFirst()
                .ifPresent(message -> {
                    RedisSubscriber<?> subscriber = redisService.getSubscribers().get(message.name());

                    if (subscriber != null) {
                        subscriber.execute(JsonParser.parseString(args[1]).getAsJsonObject());
                    }
                });
    }
}
