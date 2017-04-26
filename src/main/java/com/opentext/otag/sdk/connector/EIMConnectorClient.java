/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.connector;

import com.opentext.otag.sdk.types.v3.sdk.EIMConnector;

import java.io.Serializable;

/**
 * Type that manages a connection to a specific {@link EIMConnector}.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.2
 *
 * @see EIMConnector
 */
public interface EIMConnectorClient {

    String getConnectorName();

    String getConnectorVersion();

    /**
     * Retrieve the EIM Connectors connection URL. This may change over time as the
     * EIM connector is updated, so call this to get the current value.
     *
     * @return connection String
     */
    String getConnectionString();

    /**
     * Connect this client to an EIM connector identified by the required connector name and version.
     *
     * @return connection result
     */
    ConnectionResult connect();

    /**
     * Encapsulates the outcome of a connection attempt.
     */
    class ConnectionResult implements Serializable {

        /**
         * EIM Connector, should be populated if the connection attempt was successful.
         */
        private EIMConnector connector;

        /**
         * Was the connection successful?
         */
        private boolean success = false;

        /**
         * Outcome message.
         */
        private String message;

        public ConnectionResult() {
        }

        public ConnectionResult(EIMConnector connector) {
            this.success = true;
            this.connector = connector;
        }

        public ConnectionResult(String message) {
            this.success = false;
            this.message = message;
        }

        public EIMConnector getConnector() {
            return connector;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }
    }

}
