/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.client.v3;

import com.opentext.otag.sdk.bus.SdkEventKeys;
import com.opentext.otag.sdk.bus.SdkQueueEvent;
import com.opentext.otag.sdk.handlers.AbstractSettingChangeHandler;
import com.opentext.otag.sdk.handlers.SettingChangeHandler;
import com.opentext.otag.sdk.types.v3.api.SDKResponse;
import com.opentext.otag.sdk.types.v3.api.error.APIException;
import com.opentext.otag.sdk.types.v3.settings.Setting;
import com.opentext.otag.sdk.types.v3.settings.Settings;
import com.opentext.otag.sdk.types.v4.SdkRequest;
import com.opentext.otag.service.context.AWConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;

import static com.opentext.otag.sdk.bus.SdkEventKeys.SETTINGS_REGISTER_FOR_SETTING_UPDATES;
import static com.opentext.otag.sdk.util.StringUtil.isNullOrEmpty;

/**
 * A REST client that can be used to with the AppWorks Gateway Settings API. This Setting
 * API allows developers to store centralised configuration and react to updates to this
 * config made via the AppWorks administration UI.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.2
 */
public class SettingsClient extends AbstractOtagServiceClient {

    private static final Logger LOG = LoggerFactory.getLogger(SettingsClient.class);

    public static final String SETTINGS_SERVICE_PATH = OTAG_DEPLOYMENTS_SERVICE_PATH + "settings/";

    public SettingsClient() {
        super();
    }

    public SettingsClient(Client restClient, AWConfigFactory configurationLoaderFactory) {
        // testing constructor
        super(restClient, configurationLoaderFactory);
    }

    /**
     * Ask OTAG to let us know when a specific Setting is updated. A handler implementation
     * should be provided if you expect to respond to this kind of notification from OTAG
     * however.
     *
     * @param settingKey the unique configuration setting id
     * @return sdk response with a success indicator, true if we registered for updates successfully
     * @throws APIException if a non 200 response is received
     * @see SettingChangeHandler
     * @see AbstractSettingChangeHandler
     */
    public SDKResponse registerForUpdates(String settingKey) {
        checkSettingKey(settingKey);

        return sendSdkEventAndGetResponse(SdkQueueEvent.request(
                new SdkRequest<>(SETTINGS_REGISTER_FOR_SETTING_UPDATES, settingKey),
                getAppName(), getPersistenceContext()));
    }

    /**
     * Create a new Setting in the shared OTAG config store. If the Setting already
     * exists this call will fail.
     *
     * @param setting the new Setting
     * @return sdk response with a success indicator, true if the new Setting was stored, false otherwise
     * @throws APIException if a non 200 response is received
     */
    public SDKResponse createSetting(Setting setting) {
        if (setting == null || isNullOrEmpty(setting.getKey())) {
            throw new IllegalArgumentException("A valid setting is required");
        }
        return sendSdkEventAndGetResponse(SdkQueueEvent.request(
                new SdkRequest<>(SdkEventKeys.SETTINGS_ADD_SETTING, setting), getAppName(), getPersistenceContext()));
    }

    /**
     * Update an existing Setting. Supply the whole Setting state as this is a
     * PUT operation essentially, which will fail in the event that the Setting
     * does not already exist.
     *
     * @param setting Setting to update
     * @return sdk response with a success indicator, true if the update succeeds
     * @throws APIException if a non 200 response is received
     */
    public SDKResponse updateSetting(Setting setting) {
        if (setting == null || isNullOrEmpty(setting.getKey())) {
            throw new IllegalArgumentException("A valid setting is required");
        }
        return sendSdkEventAndGetResponse(SdkQueueEvent.request(
                new SdkRequest<>(SdkEventKeys.SETTINGS_UPDATE_SETTING, setting), getAppName(), getPersistenceContext()));
    }

    /**
     * Retrieve all the Settings for the app.
     *
     * @return A collection of Settings.
     * @throws APIException if a non 200 response is received
     */
    public Settings getSettings() {
        return sendSdkEventAndGetTypedResponse(SdkQueueEvent.request(
                new SdkRequest<>(SdkEventKeys.SETTINGS_GET_SETTINGS), getAppName(), getPersistenceContext()),
                Settings.class);
    }

    /**
     * Retrieve a Setting via its unique id.
     *
     * @param settingKey the unique configuration setting id
     * @return a Setting, or null if no Setting was found for the supplied identifier
     * @throws APIException if a non 200 response is received
     */
    public Setting getSetting(String settingKey) {
        checkSettingKey(settingKey);
        return sendSdkEventAndGetTypedResponse(SdkQueueEvent.request(
                new SdkRequest<>(SdkEventKeys.SETTINGS_GET_SETTINGS, settingKey), getAppName(), getPersistenceContext()),
                Setting.class);
    }

    public String getSettingAsString(String settingKey) {
        Setting setting = getSetting(settingKey);
        if (setting != null)
            return setting.getValue();

        return null;
    }

    public Integer getSettingAsInt(String settingKey) {
        Setting setting = getSetting(settingKey);
        if (setting != null) {
            try {
                return Integer.parseInt(setting.getValue());
            } catch (Exception e) {
                LOG.error("Get setting as int failed - " + e.getMessage());
                LOG.warn("Unable to retrieve setting as integer: key=" + settingKey);
            }
        }

        return null;
    }

    public Long getSettingAsLong(String settingKey) {
        Setting setting = getSetting(settingKey);
        if (setting != null) {
            try {
                return Long.parseLong(setting.getValue());
            } catch (Exception e) {
                LOG.error("Get setting as long failed - " + e.getMessage());
                LOG.warn("Unable to retrieve setting as long: key=" + settingKey);
            }
        }

        return null;
    }

    public Boolean getSettingAsBool(String settingKey) {
        Setting setting = getSetting(settingKey);
        if (setting != null) {
            try {
                return Boolean.parseBoolean(setting.getValue());
            } catch (Exception e) {
                LOG.error("Get setting as bool failed - " + e.getMessage());
                LOG.warn("Unable to retrieve setting as boolean: key=" + settingKey);
            }
        }

        return null;
    }

    /**
     * Remove a setting via its key. Only settings that belong to the app/service
     * making this call can be removed in this way.
     *
     * @param settingKey the unique configuration setting id
     * @return sdk response with a success indicator, true if the removal succeeds
     * @throws APIException if a non 200 response is received
     */
    public SDKResponse removeSetting(String settingKey) {
        checkSettingKey(settingKey);

        return sendSdkEventAndGetResponse(SdkQueueEvent.request(
                new SdkRequest<>(SdkEventKeys.SETTINGS_REMOVE_SETTING, settingKey),
                getAppName(), getPersistenceContext()));
    }

    private String getManagementPath(String appName) {
        return getManagingOtagUrl() + SETTINGS_SERVICE_PATH + appName + "/";
    }

    private void checkSettingKey(String settingKey) {
        if (isNullOrEmpty(settingKey)) {
            throw new IllegalArgumentException("The settingKey is required");
        }
    }
}
