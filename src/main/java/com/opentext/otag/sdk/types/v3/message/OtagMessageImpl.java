/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.types.v3.message;

import com.opentext.otag.sdk.types.v3.OtagServiceEvent;

import java.io.Serializable;

/**
 * Message from AppWorks Gateway to a deployed managed AppWorks service.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.2
 */
public abstract class OtagMessageImpl implements OtagMessage, Serializable {

    /**
     * A String representing something the Gateway wants the service to
     * know has happened.
     */
    private OtagServiceEvent event;

    public OtagMessageImpl() {
    }

    public OtagMessageImpl(OtagServiceEvent event) {
        if (!getSupportedTypes().contains(event))
            throw new IllegalArgumentException("Unsupported event type " + event.name());

        this.event = event;
    }

    @Override
    public OtagServiceEvent getEvent() {
        return event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OtagMessageImpl that = (OtagMessageImpl) o;

        return event == that.event;

    }

    @Override
    public int hashCode() {
        return event != null ? event.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "OtagMessageImpl{" +
                "event=" + event +
                '}';
    }

}
