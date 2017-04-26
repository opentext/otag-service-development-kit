/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.types.v3;

/**
 * An enum to represent the events an AppWorks Gateway can issue to the
 * AppWorks Services it manages.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.2
 */
public enum OtagServiceEvent {

    AUTH_REQUEST("authRequired"),
    CONNECTOR_UPDATE("connectorUpdate"),
    DECORATE_AUTH_RESPONSE("decorateAuthResponse"),
    SERVICE_ENABLED("serviceEnabled"),
    SERVICE_DISABLED("serviceDisabled"),
    SERVICE_INSTALLED("serviceInstalled"),
    SERVICE_UPGRADED("serviceUpgraded"),
    SERVICE_DOWNGRADED("serviceDowngraded"),
    SERVICE_UNINSTALLED("serviceUninstalled"),
    SETTING_UPDATED("settingUpdated");

    /**
     * The specific life-cycle event String.
     */
    private String event;

    OtagServiceEvent(String event) {
        this.event = event;
    }

    public static OtagServiceEvent getByEvent(String action) {
        for (OtagServiceEvent event : values()) {
            if (action.equals(event.getEvent())) {
                return event;
            }
        }

        return null;
    }

    public String getEvent() {
        return event;
    }

    public boolean isLifecycleEvent() {
        return this.equals(SERVICE_INSTALLED) ||
                this.equals(SERVICE_UPGRADED) ||
                this.equals(SERVICE_DOWNGRADED) ||
                this.equals(SERVICE_UNINSTALLED);
    }

    public boolean isSettingsEvent() {
        return this.equals(SETTING_UPDATED);
    }

    public boolean isAuthEvent() {
        return this.equals(AUTH_REQUEST) ||
                this.equals(DECORATE_AUTH_RESPONSE);
    }

}

