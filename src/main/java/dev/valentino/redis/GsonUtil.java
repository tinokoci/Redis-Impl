package dev.valentino.redis;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.LongSerializationPolicy;

import java.lang.reflect.Type;

public class GsonUtil {

    private static final Gson GSON = new GsonBuilder()
            .serializeNulls()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES)
            .setLongSerializationPolicy(LongSerializationPolicy.STRING)
            .create();

    public static String toJson(Object src) {
        return GSON.toJson(src);
    }

    public static <T> T fromJsonObject(JsonObject object, Type typeOfT) {
        return GSON.fromJson(object, typeOfT);
    }
}