package top.monkeyfans.vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtil {

    private LoggerUtil() throws IllegalAccessException {
        throw new IllegalAccessException("forbid to construct");
    }

    public Logger getLogger(String name) {
        return LoggerFactory.getLogger(name);
    }

    public <T> Logger getLogger(Class<T> tClass) {
        return LoggerFactory.getLogger(tClass);
    }

    public <T> void serviceLogger(Class<T> tClass) {
        LoggerFactory.getLogger(tClass)
                .atInfo()
                .addKeyValue("service_version", "1.0.0")
                .addKeyValue("event_type", "1.0.0")
                .log("Service Event Occurred");
    }
}
