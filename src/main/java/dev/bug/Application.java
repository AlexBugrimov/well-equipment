package dev.bug;

import org.apache.logging.log4j.Logger;

import static dev.bug.common.Log.log;

/**
 * Some class
 */
public class Application {

    private static final Logger LOG = log(Application.class);

    public static void main(final String[] args) {
        LOG.info("Start application...");
    }
}
