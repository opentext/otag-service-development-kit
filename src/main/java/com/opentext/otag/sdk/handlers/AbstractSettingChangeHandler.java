/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.handlers;

import com.opentext.otag.sdk.client.v3.SettingsClient;
import com.opentext.otag.sdk.types.v3.message.SettingsChangeMessage;

/**
 * Base implementation for a handler that responds to changes to specific
 * configuration settings. These changes usually occur via the Gateway
 * administration UI, despite belonging to the AppWorks service, so this
 * is our means of responding to those changes.
 * <p>
 * The curious can always rely on the {@link SettingsClient} to retrieve
 * the value of a configuration setting directly if required.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.0.1.1
 *
 * @see SettingsChangeMessage
 * @see SettingsClient
 */
public abstract class AbstractSettingChangeHandler implements SettingChangeHandler {

    /**
     * Base handle implementation, ensures the setting key matches the setting
     * we are interested in before processing the change message.
     *
     * @param message message setting change message
     */
    @Override
    public void handle(SettingsChangeMessage message) {
        if (message == null)
            throw new IllegalArgumentException("Cannot handle a null message");

        String settingKey = getSettingKey();
        if (settingKey != null && settingKey.equals(message.getKey()))
            onSettingChanged(message);
    }

}
