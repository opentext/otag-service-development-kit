/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.handlers;

import com.opentext.otag.sdk.types.v3.OtagServiceEvent;
import com.opentext.otag.sdk.types.v3.message.LifecycleChangeMessage;

/**
 * Base implementation of the {@link LifecycleCallbackHandler} that provides a
 * default implementation of the {@link OtagMessageHandler#handle} method.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.0.0
 */
public abstract class AbstractLifecycleChangeHandler implements LifecycleCallbackHandler {

    /**
     * Route the message to the appropriate lifecycle handler method.
     *
     * @param message message service lifecycle message
     */
    @Override
    public void handle(LifecycleChangeMessage message) {
        if (message == null)
            throw new IllegalArgumentException("Cannot handle a null message");

        OtagServiceEvent event = message.getEvent();

        // call the implementation based on the event type of the message
        switch (event) {
            case SERVICE_INSTALLED:
                onInstall(message);
                return;
            case SERVICE_UPGRADED:
            case SERVICE_DOWNGRADED:
                onChangeVersion(message);
                return;
            case SERVICE_UNINSTALLED:
                onUninstall(message);
                return;
        }

        throw new IllegalArgumentException("LifecycleCallbackHandler " +
                "cannot handle event " + event);
    }

}
