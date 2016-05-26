/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.handlers;

import com.opentext.otag.sdk.types.v3.message.SettingsChangeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Handler that listens for changes to multiple Gateway settings, assigning handlers to the
 * keys they are interested in. Add a function ({@link Consumer}) to handle a
 * specific setting key, the function should expect to receive a single argument,
 * a {@link SettingsChangeMessage}.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.0.1
 *
 * @see SettingsChangeMessage
 */
public abstract class AbstractMultiSettingChangeHandler implements MultiSettingChangeHandler {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractMultiSettingChangeHandler.class);

    /**
     * Map of setting keys to a handler functions.
     */
    protected Map<String, Consumer<SettingsChangeMessage>> handlers = new HashMap<>();

    /**
     * Add a handler for a specific key. Note this method overwrites the existing
     * handler for the provided key.
     *
     * @param key     setting key
     * @param handler setting change handler func
     */
    public void addHandler(String key, Consumer<SettingsChangeMessage> handler) {
        handlers.put(key, handler);
    }

    /**
     * Handle the message, passing it to the handler registered for the messages
     * setting key if we have one registered. If no handler if found for the
     * received key then the message is ignored.
     *
     * @param message setting change message
     */
    @Override
    public void handle(SettingsChangeMessage message) {
        Objects.requireNonNull(message);
        Consumer<SettingsChangeMessage> messageConsumer = handlers.get(message.getKey());
        if (messageConsumer != null) {
            messageConsumer.accept(message);
        } else {
            if (LOG.isDebugEnabled())
                LOG.debug("No handler found for setting " + message.getKey());
        }
    }

}
