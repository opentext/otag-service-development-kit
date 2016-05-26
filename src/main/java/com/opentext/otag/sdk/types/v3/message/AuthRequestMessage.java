/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.types.v3.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opentext.otag.sdk.types.v3.OtagServiceEvent;
import com.opentext.otag.sdk.types.v3.client.ClientRepresentation;
import com.opentext.otag.sdk.util.ForwardHeaders;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * ApWorks Gateway message used to request authentication from an AppWorks
 * service that implements an authentication request handler via
 * the AppWorks service development kit.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.0.1
 *
 * @see com.opentext.otag.sdk.handlers.AuthRequestHandler
 */
public class AuthRequestMessage extends OtagMessageImpl {

    public static final HashSet<OtagServiceEvent> AUTH_EVENTS =
            new HashSet<>(Collections.singletonList(OtagServiceEvent.AUTH_REQUEST));

    /**
     * The unique identifier of the handler we wish to fulfil the request.
     */
    private String handlerName;

    private String username;
    private String password;
    private String authToken;
    private ForwardHeaders forwardHeaders;
    private ClientRepresentation clientData;

    public AuthRequestMessage() {
    }

    public AuthRequestMessage(OtagServiceEvent event) {
        super(event);
    }

    public static AuthRequestMessage authByCredsMsg(String handlerName,
                                                    OtagServiceEvent otagServiceEvent,
                                                    String username,
                                                    String password,
                                                    ForwardHeaders forwardHeaders,
                                                    ClientRepresentation clientData) {
        AuthRequestMessage message = new AuthRequestMessage(otagServiceEvent);
        message.username = username;
        message.handlerName = handlerName;
        message.password = password;
        message.forwardHeaders = forwardHeaders;
        message.clientData = clientData;
        return message;
    }

    public static AuthRequestMessage authByTokenMsg(String handlerName,
                                                    OtagServiceEvent otagServiceEvent,
                                                    String authToken,
                                                    ForwardHeaders forwardHeaders,
                                                    ClientRepresentation clientData) {
        AuthRequestMessage message = new AuthRequestMessage(otagServiceEvent);
        message.handlerName = handlerName;
        message.authToken = authToken;
        message.forwardHeaders = forwardHeaders;
        message.clientData = clientData;
        return message;
    }

    public static AuthRequestMessage decorateByCreds(OtagServiceEvent otagServiceEvent,
                                                     String username,
                                                     String password,
                                                     ForwardHeaders forwardHeaders,
                                                     ClientRepresentation clientData) {
        AuthRequestMessage message = new AuthRequestMessage(otagServiceEvent);
        message.username = username;
        message.password = password;
        message.forwardHeaders = forwardHeaders;
        message.clientData = clientData;
        return message;
    }

    public static AuthRequestMessage decorateByAuthToken(OtagServiceEvent otagServiceEvent,
                                                    String authToken,
                                                    ForwardHeaders forwardHeaders,
                                                    ClientRepresentation clientData) {
        AuthRequestMessage message = new AuthRequestMessage(otagServiceEvent);
        message.authToken = authToken;
        message.forwardHeaders = forwardHeaders;
        message.clientData = clientData;
        return message;
    }

    @JsonIgnore
    public boolean hasAuthToken() {
        return getAuthToken() != null;
    }

    @JsonIgnore
    public boolean hasCreds() {
        return getUsername() != null &&
                getPassword() != null;
    }

    @JsonIgnore
    @Override
    public Set<OtagServiceEvent> getSupportedTypes() {
        return AUTH_EVENTS;
    }

    public String getHandlerName() {
        return handlerName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAuthToken() {
        return authToken;
    }

    public ForwardHeaders getForwardHeaders() {
        return forwardHeaders;
    }

    public ClientRepresentation getClientData() {
        return clientData;
    }

}
