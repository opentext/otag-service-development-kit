/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.handlers;

import com.opentext.otag.sdk.types.v3.OtagServiceEvent;
import com.opentext.otag.sdk.types.v3.message.SettingsChangeMessage;

import java.util.Set;

/**
 * Settings change handler type that can manage more than one setting
 * at a time.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.0.1.1
 */
public interface MultiSettingChangeHandler extends OtagMessageHandler<SettingsChangeMessage> {

    default boolean handles(OtagServiceEvent event) {
        return event != null && SettingsChangeMessage.SETTINGS_EVENTS.contains(event);
    }

    default Class<SettingsChangeMessage> getHandledType() {
        return SettingsChangeMessage.class;
    }

    /**
     * Return the set of settings keys that this handler is interested in.
     *
     * @return Setting keys
     */
    Set<String> getSettingKeys();

}
