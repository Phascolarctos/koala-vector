package top.monkeyfans.vector;

import io.undertow.Undertow;
import io.undertow.util.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KoalaVector {
    private static final Logger LOGGER = LoggerFactory.getLogger(KoalaVector.class);

    public static void main(String[] args) {
        LOGGER.info("Starting KoalaVector");
        Undertow undertow = Undertow.builder()
                .addHttpListener(8080, "localhost")
                .setHandler(exchange -> {
                    exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                    exchange.getResponseSender().send("Hello World");
                }).build();
        undertow.start();
    }
}
