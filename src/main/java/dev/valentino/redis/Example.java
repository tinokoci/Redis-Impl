package dev.valentino.redis;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class Example {

    public static void main(String[] args) {
        RedisService service = new RedisService("localhost", 6379);

        // Subscribing to the ANNOUNCEMENT messages
        service.subscribe(RedisMessage.ANNOUNCEMENT, AnnouncementModel.class, announcement -> {
            System.out.println("New announcement from " + announcement.getIssuer() + '!');
            System.out.println(" - " + announcement.getMessage());
        });

        // Publishing an ANNOUNCEMENT message
        service.publish(RedisMessage.ANNOUNCEMENT, new AnnouncementModel("valentino", "Hello World"));
    }

    @RequiredArgsConstructor
    @Getter
    private static class AnnouncementModel {

        private final String issuer;
        private final String message;
    }
}
