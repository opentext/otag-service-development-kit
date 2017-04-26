/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.handlers;

import com.opentext.otag.sdk.types.v3.OtagServiceEvent;
import com.opentext.otag.sdk.types.v3.message.OtagMessage;
import com.opentext.otag.service.context.components.AWComponent;
import com.opentext.otag.service.context.components.AWComponentContext;

/**
 * Marker type for AppWorks handler instances. These classes respond to messages
 * issued from the Gateway, these can be control messages or general updates that
 * an AppWorks service may be interested in.
 * <p>
 * In general, a single instance of each handler found in the service will be
 * created  (registered) by the AppWorks service platform using the single <strong>required</strong>
 * default no-arg constructor. The instance will then be placed, on your behalf, into
 * the {@link AWComponentContext} for later reference. The platform will then,
 * based on the type of message handled, pass the message to the handler implementation.
 * <p>
 * Note, only non-abstract implementations of this interface that you include in your
 * AppWorks service will be registered on your behalf when the service starts.
 *
 * @param <T> OTAG message type handled by this handler
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.2
 */
public interface OtagMessageHandler<T extends OtagMessage> extends AWComponent {

    /**
     * Handle an AppWorks service message.
     *
     * @param message message
     */
    void handle(T message);

    /**
     * Does the handler handle the provided event type?
     *
     * @param event event type
     * @return true if this handler can handle this type of event
     */
    boolean handles(OtagServiceEvent event);

}