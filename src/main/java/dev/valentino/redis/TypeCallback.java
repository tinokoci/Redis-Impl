package dev.valentino.redis;

@FunctionalInterface
public interface TypeCallback<T> {

    void run(T type);
}
