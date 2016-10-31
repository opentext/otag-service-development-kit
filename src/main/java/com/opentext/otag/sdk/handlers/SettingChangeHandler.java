/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.handlers;

import com.opentext.otag.sdk.types.v3.OtagServiceEvent;
import com.opentext.otag.sdk.types.v3.message.SettingsChangeMessage;

/**
 * Handles changes to AppWorks configuration settings. When an AppWorks service is started
 * any implementations of this handler will be automatically registered with the AppWorks
 * Gateway for notifications about the configuration setting they are interested in. As
 * defined by the handlers implementation of {@link SettingChangeHandler#getSettingKey}.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.1
 */
public interface SettingChangeHandler extends OtagMessageHandler<SettingsChangeMessage> {

    default boolean handles(OtagServiceEvent event) {
        return event != null && SettingsChangeMessage.SETTINGS_EVENTS.contains(event);
    }

    default Class<SettingsChangeMessage> getHandledType() {
        return SettingsChangeMessage.class;
    }

    /**
     * Return the unique Setting key values that this handler is interested in.
     *
     * @return Setting key
     */
    String getSettingKey();

    /**
     * Fired when the service is informed that the value of the config setting we are interested
     * in has been altered.
     *
     * @param message settings update
     */
    void onSettingChanged(SettingsChangeMessage message);

}
