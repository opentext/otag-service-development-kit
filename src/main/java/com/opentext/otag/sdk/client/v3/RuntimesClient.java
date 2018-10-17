/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.client.v3;

import com.opentext.otag.sdk.bus.SdkEventKeys;
import com.opentext.otag.sdk.bus.SdkQueueEvent;
import com.opentext.otag.sdk.types.v3.api.error.APIException;
import com.opentext.otag.sdk.types.v3.apps.Runtimes;
import com.opentext.otag.sdk.types.v4.SdkRequest;
import com.opentext.otag.service.context.AWConfigFactory;

import javax.ws.rs.client.Client;

/**
 * Runtime API service client. Runtimes represent client applications that
 * use the AppWorks Gateway. These applications indicate (on login) that a user
 * is using a specific Runtime. Runtimes are identified by a unique name. Examples
 * of Runtimes include the iOS mobile client, the Android client and even the
 * Gateway administration UI.
 * <p>
 * The Gateways push notifications API offers the ability to send notifications
 * to all users that connect via a specific Runtime, and Gateway administrators
 * can also limit a specific apps use by Runtime.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.2
 */
public class RuntimesClient extends AbstractOtagServiceClient {

    public static final String RUNTIMES_SERVICE_PATH = OTAG_DEPLOYMENTS_SERVICE_PATH + "runtimes/";

    public RuntimesClient() {
        super();
    }

    public RuntimesClient(Client restClient, AWConfigFactory configurationLoaderFactory) {
        // testing constructor
        super(restClient, configurationLoaderFactory);
    }

    /**
     * Retrieve the known list of runtimes.
     *
     * @return a list of runtimes, client applications that use the Gateway
     * @throws APIException if a non 200 response is received
     */
    public Runtimes getAllRuntimes() {
        SdkQueueEvent getRuntimesEvt = SdkQueueEvent.request(new SdkRequest<>(SdkEventKeys.GET_RUNTIMES),
                getAppName(), getPersistenceContext());

        try {
            return sendSdkEventAndGetTypedResponse(getRuntimesEvt, Runtimes.class);
        } catch (Exception e) {
            throw new APIException("Failed to retrieve all runtimes via the SDK", e);
        }
    }

    private String getManagementPath(String appName) {
        return getManagingOtagUrl() + RUNTIMES_SERVICE_PATH + appName + "/";
    }

}
