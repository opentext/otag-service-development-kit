/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.connector;

import com.opentext.otag.sdk.client.v3.AbstractOtagServiceClient;
import com.opentext.otag.sdk.client.v3.ServiceClient;
import com.opentext.otag.sdk.client.v3.SettingsClient;
import com.opentext.otag.sdk.handlers.AbstractSettingChangeHandler;
import com.opentext.otag.sdk.types.v3.api.SDKResponse;
import com.opentext.otag.sdk.types.v3.message.SettingsChangeMessage;
import com.opentext.otag.sdk.types.v3.sdk.EIMConnector;
import com.opentext.otag.sdk.types.v3.sdk.EIMConnectors;
import com.opentext.otag.service.context.components.AWComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * EIM Connector implementation. Connectors are instance of the
 * {@link EIMConnectorService} being managed by the Gateway. They represent a
 * shared connection to some Open Text back-end server/system.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.1
 *
 * @see EIMConnectorService
 */
public class EIMConnectorClientImpl extends AbstractOtagServiceClient implements EIMConnectorClient {

    private static final Logger LOG = LoggerFactory.getLogger(EIMConnectorClientImpl.class);

    private ServiceClient serviceClient;
    private SettingsClient settingsClient;

    private String connectorName;
    private String connectorVersion;

    private String connectionString;
    private String connectorConnectionSettingKey;

    /**
     * EIM Connector Client. Construction will fail if we are not able to
     * make a connection.
     *
     * @param connectorName    connector name
     * @param connectorVersion connector version
     */
    public EIMConnectorClientImpl(String connectorName,
                                  String connectorVersion) {
        super();

        this.connectorName = connectorName;
        this.connectorVersion = connectorVersion;

        serviceClient = new ServiceClient();
        settingsClient = new SettingsClient();
    }

    @Override
    public String getConnectorName() {
        return connectorName;
    }

    @Override
    public String getConnectorVersion() {
        return connectorVersion;
    }

    @Override
    public String getConnectionString() {
        return connectionString;
    }

    @Override
    public ConnectionResult connect() {
        if (connectorName == null || connectorVersion == null) {
            String errMsg = "Failed to get connector as we need a connector name and version";
            LOG.warn(errMsg);
            return new ConnectionResult(errMsg);
        }

        try {
            EIMConnectors availableConnectors = serviceClient.getEIMConnectors();
            EIMConnector connector = getConnector(availableConnectors.getConnectors());

            // we need to register for updates to the connectors connection URL
            if (connector != null) {
                String key = connector.getConnectionUrlSettingKey();
                if (key != null) {
                    connectorConnectionSettingKey = key;
                    // register for updates to the connectors connection String
                    SDKResponse sdkResponse = settingsClient.registerForUpdates(connectorConnectionSettingKey);
                    if (!sdkResponse.isSuccess()) {
                        String errMsg = "Failed to register for update to EIM " +
                                "connector key - " + connectorConnectionSettingKey;
                        LOG.error(errMsg);
                        return new ConnectionResult(errMsg);
                    }

                    // inject an anonymous handler into the AppWorksContext, we can then intercept messages
                    // that indicate the EIM connectors URL config setting has changed
                    AWComponentContext.add(new AbstractSettingChangeHandler() {
                        @Override
                        public String getSettingKey() {
                            return connectorConnectionSettingKey;
                        }

                        @Override
                        public void onSettingChanged(SettingsChangeMessage message) {
                            connectionString = message.getNewValue();
                        }
                    });

                    // set the properties of the connector
                    connectionString = connector.getConnectionUrl();
                    return new ConnectionResult(connector);
                } else {
                    String errMsg = "Failed to retrieve URL settings key from connector instance??";
                    LOG.error(errMsg);
                    return new ConnectionResult(errMsg);
                }
            } else {
                String errMsg = "Failed to find a connector for name=" + connectorName +
                        " version=" + connectorVersion;
                LOG.error(errMsg);
                return new ConnectionResult(errMsg);
            }
        } catch (Exception e) {
            String errMsg = "Failed to connect, an unexpected error occurred, " + e.getMessage();
            LOG.error(errMsg, e);
            return new ConnectionResult(errMsg);
        }
    }

    private EIMConnector getConnector(List<EIMConnector> available) {
        for (EIMConnector connector : available) {
            if (connectorName.equals(connector.getConnectorName()) &&
                    connectorVersion.equals(connector.getConnectorVersion())) {
                return connector;
            }
        }

        LOG.warn("Connector not found in available connectors");
        return null;
    }

}