/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.types.v3.message;

import com.opentext.otag.sdk.types.v3.OtagServiceEvent;

import java.util.Set;

public interface OtagMessage {

    OtagServiceEvent getEvent();

    Set<OtagServiceEvent> getSupportedTypes();

}