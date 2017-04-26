/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.types.v3.auth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Used by our service management agent to register auth handlers
 * it finds in the deployed service it is managing.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.2
 */
public class RegisterAuthHandlersRequest implements Serializable {

    private List<AuthHandler> handlers = new ArrayList<>();

    public RegisterAuthHandlersRequest() {
    }

    public List<AuthHandler> getHandlers() {
        return handlers;
    }

    public void addHandler(AuthHandler handler) {
        handlers.add(handler);
    }

}