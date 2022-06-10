package org.argonath.rd.entry.app;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.jboss.logging.Logger;

import javax.enterprise.event.Observes;

public class EventListener {
    private static final Logger LOGGER = Logger.getLogger(EventListener.class.getName());

    public void startup(@Observes StartupEvent startupEvent) {
        LOGGER.info("Starting Down Entry Service");
    }

    public void shutdown(@Observes ShutdownEvent shutdownEvent) {
        LOGGER.info("Shutting Down Entry Service");
    }
}
