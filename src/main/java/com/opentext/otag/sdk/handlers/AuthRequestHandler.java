/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.handlers;

import com.opentext.otag.sdk.types.v3.OtagServiceEvent;
import com.opentext.otag.sdk.types.v3.auth.AuthHandlerResult;
import com.opentext.otag.sdk.types.v3.client.ClientRepresentation;
import com.opentext.otag.sdk.types.v3.message.AuthRequestMessage;
import com.opentext.otag.sdk.util.Cookie;
import com.opentext.otag.sdk.util.ForwardHeaders;

import java.util.Set;

/**
 * Used to delegate auth to a back-end server other than OTDS. A service should only
 * provide one auth handler.
 * <p>
 * The back-end server must implement a scheme that guarantees at least the same level
 * of protection that OTDS offers as it can be promoted to the primary authentication mechanism
 * within the Gateway.
 * <p>
 * Implementations must provide a default (no-argument) constructor, and can be optionally
 * annotated with {@link AuthResponseDecorator} to indicate that they should be used to
 * decorate auth responses when they are not being used as the Gateway's primary auth handler.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.0.1
 */
public interface AuthRequestHandler extends OtagMessageHandler<AuthRequestMessage> {

    default boolean handles(OtagServiceEvent event) {
        return event != null && AuthRequestMessage.AUTH_EVENTS.contains(event);
    }

    default Class<AuthRequestMessage> getHandledType() {
        return AuthRequestMessage.class;
    }

    /**
     * Authorize the user on the back-end server. The server must accept the
     * same credentials that OTAG does.
     *
     * @param username   username as provided to the auth service
     * @param password   password
     * @param headers    implementations should use this to properly set X-Forwarded-For headers
     *                   that the back-end server may require
     * @param clientData client details
     * @return Set of cookies to return to the client, or null to skip auth for this back-end
     */
    AuthHandlerResult auth(String username, String password, ForwardHeaders headers, ClientRepresentation clientData);

    /**
     * Authorize the user on the back-end server using an session token (OTDS potentially).
     *
     * @param authToken the auth token for the user.
     * @param headers    Implementations should use this to properly set X-Forwarded-For headers
     * @param clientData client details
     * @return Set of cookies to return to the client, or null to skip auth for this back-end
     */
    AuthHandlerResult auth(String authToken, ForwardHeaders headers, ClientRepresentation clientData);

    /**
     * Many of our back-end servers integrate with the OTDS (OpenText Directory Services)
     * product. This can be used to map usernames from back-end servers to OTDS representations
     * within specific OTDS "resources". If this auth handler does relate to a back-end server
     * that does make use of this OTDS feature it can indicate that this is the case here.
     * <p>
     * If the handler implementation returns true then we expect it to implement
     * {@link #getOtdsResourceId}, supplying the OTDS "resource" id.
     *
     * @return true if this handler accepts usernames resolved from a related OTDS
     * resource partition, false if the username received in the original
     * Gateway auth request should be passed to the handler as is
     */
    boolean resolveUsernamesViaOtdsResource();

    /**
     * Retrieve the OTDS resource id for the back-end server performing the auth on
     * our behalf (if OTDS is being used). The Gateway will use this to form the requests
     * the handler should expect to receive.
     *
     * @return resource id or null
     */
    String getOtdsResourceId();

    /**
     * The set of cookies this handler uses.
     *
     * @return auth cookies
     */
    Set<Cookie> getKnownCookies();

}
