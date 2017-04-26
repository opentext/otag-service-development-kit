/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.types.v3.sdk;

import com.opentext.otag.sdk.connector.EIMConnectorService;
import com.opentext.otag.sdk.types.v3.SDKType;
import com.opentext.otag.sdk.types.v3.TrustedProvider;

import java.io.Serializable;

/**
 * Simple type that represents a shared connection to a Enterprise Information
 * Management (EIM) system, a convenience when multiple AppWorks services share
 * the same EIM system entry point (URL).
 * <p>
 * A trusted server key (Gateway API key) can be added via this type, and even some
 * proxy rules. Templates can also be used to configure proxy entries via the Gateway
 * Administration UI.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.2
 */
public class EIMConnector extends SDKType implements Serializable {

    /**
     * Name of this connector.
     */
    private String connectorName;

    /**
     * Unique version of this connector.
     */
    private String connectorVersion;

    /**
     * Base connection URL to EIM backend.
     */
    private String connectionUrl;

    /**
     * The Gateway configuration setting "key" associated with this connector.
     */
    private String connectionUrlSettingKey;

    /**
     * Trusted provider name. This is the unique (within a Gateway instance) Trusted Provider Id. When
     * used in conjunction with its API key that can be used with a limited set of the Gateways
     * administration REST API endpoints.
     *
     * @see TrustedProvider#getName()
     */
    private String providerName;

    /**
     * Trusted provider key. Gateway API key that can be used with a limited set of the Gateways
     * administration REST API endpoints.
     *
     * @see TrustedProvider#getKey()
     */
    private String providerKey;

    public EIMConnector() {
    }

    // we don't retain the proxy settings associated with the connector at the Gateway
    public EIMConnector(String connectorName, String connectorVersion,
                        String connectionUrl, String urlSettingsKey,
                        String providerName, String providerKey) {
        this.connectorName = connectorName;
        this.connectorVersion = connectorVersion;
        this.connectionUrl = connectionUrl;
        this.connectionUrlSettingKey = urlSettingsKey;
        this.providerName = providerName;
        this.providerKey = providerKey;
    }

    public EIMConnector(EIMConnectorService connectorService) {
        this.connectorName = connectorService.getConnectorName();
        this.connectorVersion = connectorService.getConnectorVersion();
        this.connectionUrl = connectorService.getConnectionString();
        this.connectionUrlSettingKey = connectorService.getConnectionStringSettingKey();
        this.providerKey = connectorService.getTrustedProviderKey();
    }

    public String getConnectorName() {
        return connectorName;
    }

    public String getConnectorVersion() {
        return connectorVersion;
    }

    public String getConnectionUrl() {
        return connectionUrl;
    }

    public String getConnectionUrlSettingKey() {
        return connectionUrlSettingKey;
    }

    public String getProviderName() {
        return providerName;
    }

    public String getProviderKey() {
        return providerKey;
    }

    // equality is defined in terms of the name and version of the connector as there
    // may be multiple versions of the EIM system in production.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EIMConnector that = (EIMConnector) o;

        return !(connectorName != null ? !connectorName.equals(that.connectorName) :
                that.connectorName != null) && !(connectorVersion != null ?
                !connectorVersion.equals(that.connectorVersion) : that.connectorVersion != null);
    }

    @Override
    public int hashCode() {
        int result = connectorName != null ? connectorName.hashCode() : 0;
        result = 31 * result + (connectorVersion != null ? connectorVersion.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EIMConnector{" +
                ", connectorName='" + connectorName + '\'' +
                ", connectorVersion='" + connectorVersion + '\'' +
                ", connectionUrl='" + connectionUrl + '\'' +
                ", connectionUrlSettingKey='" + connectionUrlSettingKey + '\'' +
                ", providerName='" + providerName + '\'' +
                ", providerKey='" + providerKey + '\'' +
                ", sdkCallInfo='" +  getSdkCallInfo() + '\'' +
                '}';
    }

}
