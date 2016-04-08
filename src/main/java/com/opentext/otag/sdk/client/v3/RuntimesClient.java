/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.client.v3;

import com.fasterxml.jackson.core.type.TypeReference;
import com.opentext.otag.sdk.types.v3.api.SDKCallInfo;
import com.opentext.otag.sdk.types.v3.api.error.APIException;
import com.opentext.otag.sdk.types.v3.apps.Runtime;
import com.opentext.otag.sdk.types.v3.apps.Runtimes;
import com.opentext.otag.sdk.util.UrlPathUtil;
import com.opentext.otag.service.context.AWConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.List;

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
 * @version 16.0.0
 */
public class RuntimesClient extends AbstractOtagServiceClient {

    private static final Logger LOG = LoggerFactory.getLogger(RuntimesClient.class);

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
    public Runtimes getAllRuntimes() throws APIException {
        String getAllUrl = getManagementPath(appName);

        WebTarget target = restClient.target(UrlPathUtil.getBaseUrl(getAllUrl))
                .path(UrlPathUtil.getPath(getAllUrl));

        MultivaluedMap<String, Object> requestHeaders = getSDKRequestHeaders();
        try {
            Response response = target.request()
                    .headers(requestHeaders)
                    .get();

            int responseStatus = response.getStatus();
            String responseBody = response.readEntity(String.class);
            MultivaluedMap<String, Object> responseHeaders = response.getHeaders();
            validateResponse(getAllUrl, requestHeaders, responseStatus, responseBody, responseHeaders);

            List<Runtime> runtimes = getMapper().readValue(responseBody, new TypeReference<List<Runtime>>() {});

            return new Runtimes(runtimes, new SDKCallInfo(getAllUrl, requestHeaders, responseStatus,
                    responseHeaders, responseBody));
        } catch (Exception e) {
            LOG.error("Failed to list runtimes", e);
            throw processFailureResponse(getAllUrl, requestHeaders, e);
        }
    }

    private String getManagementPath(String appName) {
        return getManagingOtagUrl() + RUNTIMES_SERVICE_PATH + appName + "/";
    }

}
