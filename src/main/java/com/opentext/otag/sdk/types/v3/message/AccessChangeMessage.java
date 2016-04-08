/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.types.v3.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opentext.otag.sdk.types.v3.OtagServiceEvent;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AccessChangeMessage extends OtagMessageImpl {

    private static Set<OtagServiceEvent> ACCESS_EVENTS = new HashSet<>(Arrays.asList(
            OtagServiceEvent.SERVICE_ENABLED,
            OtagServiceEvent.SERVICE_DISABLED));

    public AccessChangeMessage() {
    }

    public AccessChangeMessage(OtagServiceEvent event) {
        super(event);
    }

    @JsonIgnore
    @Override
    public Set<OtagServiceEvent> getSupportedTypes() {
        return ACCESS_EVENTS;
    }

}
