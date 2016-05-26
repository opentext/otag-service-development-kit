/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.types.v3.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opentext.otag.sdk.types.v3.OtagServiceEvent;
import com.opentext.otag.sdk.types.v3.sdk.EIMConnector;
import jersey.repackaged.com.google.common.collect.Sets;

import java.util.Set;

/**
 * Message used to convey any updates to an EIM connector made via the
 * Gateway's administration console(provider key updates, etc...).
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.0.1
 */
public class EimConnectorUpdateMessage extends OtagMessageImpl {

    private EIMConnector updatedConnector;

    public EimConnectorUpdateMessage() {
    }

    public EimConnectorUpdateMessage(EIMConnector updatedConnector) {
        super(OtagServiceEvent.CONNECTOR_UPDATE);
        this.updatedConnector = updatedConnector;
    }

    @Override
    @JsonIgnore
    public Set<OtagServiceEvent> getSupportedTypes() {
        return Sets.newHashSet(OtagServiceEvent.CONNECTOR_UPDATE);
    }

    public EIMConnector getUpdatedConnector() {
        return updatedConnector;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        EimConnectorUpdateMessage that = (EimConnectorUpdateMessage) o;

        return updatedConnector != null ? updatedConnector.equals(that.updatedConnector) : that.updatedConnector == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (updatedConnector != null ? updatedConnector.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EimConnectorUpdateMessage{" +
                "updatedConnector=" + updatedConnector +
                '}';
    }

}
