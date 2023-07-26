package dev.bug.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Log {

    private Log() {
    }

    public static <T> Logger log(Class<T> type) {
        return LogManager.getLogger(type);
    }
}
