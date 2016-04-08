/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.types.v3.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opentext.otag.sdk.types.v3.OtagServiceEvent;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class LifecycleChangeMessage extends OtagMessageImpl {

    public static Set<OtagServiceEvent> LIFE_CYCLE_EVENTS = new HashSet<>(Arrays.asList(
            OtagServiceEvent.SERVICE_INSTALLED,
            OtagServiceEvent.SERVICE_UPGRADED,
            OtagServiceEvent.SERVICE_DOWNGRADED,
            OtagServiceEvent.SERVICE_UNINSTALLED
    ));

    public LifecycleChangeMessage() {
    }

    public LifecycleChangeMessage(OtagServiceEvent event) {
        super(event);
    }

    @JsonIgnore
    @Override
    public Set<OtagServiceEvent> getSupportedTypes() {
        return LIFE_CYCLE_EVENTS;
    }

}
