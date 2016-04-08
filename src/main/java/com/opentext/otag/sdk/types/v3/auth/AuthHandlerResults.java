/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.types.v3.auth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AuthHandlerResults implements Serializable {

    private List<AuthHandlerResult> results = new ArrayList<>();

    public AuthHandlerResults() {
    }

    public void addResult(AuthHandlerResult result) {
        results.add(result);
    }

    public List<AuthHandlerResult> getResults() {
        return results;
    }

}
