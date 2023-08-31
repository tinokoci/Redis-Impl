package dev.valentino.redis;

import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RedisSubscriber<T> {

    private final Class<T> clazz;
    private final TypeCallback<T> callback;

    public void execute(JsonObject object) {
        if (callback != null) {
            callback.run(GsonUtil.fromJsonObject(object, clazz));
        }
    }
}
