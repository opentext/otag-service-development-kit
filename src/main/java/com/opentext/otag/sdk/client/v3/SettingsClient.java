/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.client.v3;

import com.fasterxml.jackson.core.type.TypeReference;
import com.opentext.otag.sdk.handlers.AbstractSettingChangeHandler;
import com.opentext.otag.sdk.handlers.SettingChangeHandler;
import com.opentext.otag.sdk.types.v3.api.SDKCallInfo;
import com.opentext.otag.sdk.types.v3.api.SDKResponse;
import com.opentext.otag.sdk.types.v3.api.error.APIException;
import com.opentext.otag.sdk.types.v3.settings.Setting;
import com.opentext.otag.sdk.types.v3.settings.Settings;
import com.opentext.otag.service.context.AWConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.List;

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
     *
     * @see SettingChangeHandler
     * @see AbstractSettingChangeHandler
     */
    public SDKResponse registerForUpdates(String settingKey) {
        String registerUrl = getManagementPath(appName) + settingKey + "/listeners";
        WebTarget target = getTarget(registerUrl);

        MultivaluedMap<String, Object> requestHeaders = getSDKRequestHeaders();
        try {
            Response response = target.request()
                    .headers(requestHeaders)
                    .post(null);

            int responseStatus = response.getStatus();
            String responseBody = response.readEntity(String.class);
            MultivaluedMap<String, Object> responseHeaders = response.getHeaders();
            validateResponse(registerUrl, requestHeaders, responseStatus, responseBody, responseHeaders);

            return new SDKResponse(true, new SDKCallInfo(registerUrl, requestHeaders, responseStatus,
                    responseHeaders, responseBody));
        } catch (Exception e) {
            LOG.error("Register for update request failed, " + e.getMessage(), e);
            throw processFailureResponse(registerUrl, requestHeaders, e);
        }
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
        String registerUrl = getManagementPath(appName) + setting.getKey();
        WebTarget target = getTarget(registerUrl);

        MultivaluedMap<String, Object> requestHeaders = getSDKRequestHeaders();
        try {
            Entity<Setting> settingEntity = Entity.entity(setting, MediaType.APPLICATION_JSON_TYPE);
            return processGenericPost(registerUrl, target, settingEntity, requestHeaders);
        } catch (Exception e) {
            LOG.error("Create setting request failed " + e.getMessage(), e);
            throw processFailureResponse(registerUrl, requestHeaders, e);
        }

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
        String updateSettingUrl = getManagementPath(appName) + setting.getKey();
        WebTarget target = getTarget(updateSettingUrl);
        Entity<Setting> settingEntity = Entity.entity(setting, MediaType.APPLICATION_JSON_TYPE);

        MultivaluedMap<String, Object> requestHeaders = getSDKRequestHeaders();
        try {
            Response response = target.request()
                    .headers(requestHeaders)
                    .put(settingEntity);

            int responseStatus = response.getStatus();
            String responseBody = response.readEntity(String.class);
            MultivaluedMap<String, Object> responseHeaders = response.getHeaders();
            validateResponse(updateSettingUrl, requestHeaders, responseStatus, responseBody, responseHeaders);

            return new SDKResponse(true, new SDKCallInfo(updateSettingUrl, requestHeaders, responseStatus,
                    responseHeaders, responseBody));
        } catch (Exception e) {
            LOG.error("Failed to update setting " + setting.getKey() + ", " + e.getMessage(), e);
            throw processFailureResponse(updateSettingUrl, requestHeaders, e);
        }

    }

    /**
     * Retrieve all the Settings for the app.
     *
     * @return A collection of Settings.
     * @throws APIException if a non 200 response is received
     */
    public Settings getSettings() {
        String getUrl = getManagementPath(appName);
        WebTarget target = getTarget(getUrl);

        MultivaluedMap<String, Object> requestHeaders = getSDKRequestHeaders();
        try {
            Response response = target
                    .request()
                    .headers(requestHeaders)
                    .get();

            int responseStatus = response.getStatus();
            String responseBody = response.readEntity(String.class);
            MultivaluedMap<String, Object> responseHeaders = response.getHeaders();

            validateResponse(getUrl, requestHeaders, responseStatus, responseBody, responseHeaders);

            List<Setting> settings = getMapper().readValue(responseBody, new TypeReference<List<Setting>>() {});
            SDKCallInfo callInfo = new SDKCallInfo(getUrl, requestHeaders, responseStatus,
                    responseHeaders, responseBody);
            return new Settings(settings, callInfo);
        } catch (Exception e) {
            LOG.error("Getting settings failed ", e);
            throw processFailureResponse(getUrl, requestHeaders, e);
        }
    }

    /**
     * Retrieve a Setting via its unique id.
     *
     * @param settingKey the unique configuration setting id
     * @return a Setting, or null if no Setting was found for the supplied identifier
     * @throws APIException if a non 200 response is received
     */
    public Setting getSetting(String settingKey) {
        String getUrl = getManagementPath(appName) + settingKey;
        WebTarget target = getTarget(getUrl);

        MultivaluedMap<String, Object> requestHeaders = getSDKRequestHeaders();
        try {
            Response response = target.request()
                    .headers(requestHeaders)
                    .get();

            int responseStatus = response.getStatus();
            String responseBody = response.readEntity(String.class);
            MultivaluedMap<String, Object> responseHeaders = response.getHeaders();

            validateResponse(getUrl, requestHeaders, responseStatus, responseBody, responseHeaders);

            Setting setting = getMapper().readValue(responseBody, Setting.class);
            SDKCallInfo callInfo = new SDKCallInfo(getUrl, requestHeaders, responseStatus,
                    responseHeaders, responseBody);
            setting.setSdkCallInfo(callInfo);
            return setting;
        } catch (Exception e) {
            LOG.error("Failed to retrieve setting", e);
            throw processFailureResponse(getUrl, requestHeaders, e);
        }
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
        String removeSettingUrl = getManagementPath(appName) + settingKey;
        WebTarget target = getTarget(removeSettingUrl);

        MultivaluedMap<String, Object> requestHeaders = getSDKRequestHeaders();
        try {
            Response response = target.request()
                    .headers(requestHeaders)
                    .delete();

            int responseStatus = response.getStatus();
            String responseBody = response.readEntity(String.class);
            MultivaluedMap<String, Object> responseHeaders = response.getHeaders();
            validateResponse(removeSettingUrl, requestHeaders, responseStatus, responseBody, responseHeaders);

            return new SDKResponse(true, new SDKCallInfo(
                    removeSettingUrl, requestHeaders, responseStatus,
                    responseHeaders, responseBody));
        } catch (Exception e) {
            LOG.error("Failed to remove setting " + settingKey + ", " + e.getMessage(), e);
            throw processFailureResponse(removeSettingUrl, requestHeaders, e);
        }
    }

    private String getManagementPath(String appName) {
        return getManagingOtagUrl() + SETTINGS_SERVICE_PATH + appName + "/";
    }

}
