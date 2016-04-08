/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.handlers;

import com.opentext.otag.sdk.types.v3.auth.AuthHandler;
import com.opentext.otag.sdk.types.v3.auth.RegisterAuthHandlersRequest;
import com.opentext.otag.sdk.types.v3.message.AuthRequestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base implementation of the {@link AuthRequestHandler} type, we provide a
 * method to produce a representation of this handler implementation that can be
 * passed to the Gateway in a auth handler registration request.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.0.0
 *
 * @see RegisterAuthHandlersRequest
 */
public abstract class AbstractAuthRequestHandler implements AuthRequestHandler {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractAuthRequestHandler.class);

    @Override
    public void handle(AuthRequestMessage message) {
        throw new UnsupportedOperationException("Use the auth method of this handler directly");
    }

    /**
     * Build a representation of this handler that can be passed in a auth handler
     * registration request. This info allows administrators to select this handler
     * as the primary auth handler for the Gateway itself.
     *
     * @return auth handler representation
     */
    public AuthHandler buildHandler() {
        String handlerName = this.getClass().getName();
        boolean isDecorator = isDecorator();

        String otdsResId = null;

        // should the Gateway attempt to resolve the usernames it receives
        // for the current primary mechanism into what the back-end server
        // understands in its OTDS resource partition?
        if (resolveUsernamesViaOtdsResource()) {
            try {
                otdsResId = getOtdsResourceId();
            } catch (Exception e) {
                LOG.warn("Failed to resolve OTDS resource id for " + handlerName +
                        " - " + e.getMessage(), e);
            }
        }

        AuthHandler authHandler;
        if (otdsResId != null) {
            authHandler = new AuthHandler(handlerName, isDecorator, otdsResId, getKnownCookies());
        } else {
            authHandler = new AuthHandler(handlerName, isDecorator, getKnownCookies());
        }

        return authHandler;
    }

    /**
     * Does this auth handler participate in auth response decoration, adding
     * cookies to responses even when this handler is not being used as the
     * primary auth mechanism.
     *
     * @return true if the Gateway should use this handler in auth response decoration
     */
    private boolean isDecorator() {
        return this.getClass().getAnnotation(AuthResponseDecorator.class) != null;
    }

}
