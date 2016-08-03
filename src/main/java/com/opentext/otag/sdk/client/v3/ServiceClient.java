/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.client.v3;

import com.fasterxml.jackson.core.type.TypeReference;
import com.opentext.otag.sdk.types.v3.api.SDKCallInfo;
import com.opentext.otag.sdk.types.v3.api.SDKResponse;
import com.opentext.otag.sdk.types.v3.api.error.APIException;
import com.opentext.otag.sdk.types.v3.management.DeploymentResult;
import com.opentext.otag.sdk.types.v3.sdk.EIMConnector;
import com.opentext.otag.sdk.types.v3.sdk.EIMConnectors;
import com.opentext.otag.sdk.util.UrlPathUtil;
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
import java.util.Objects;

/**
 * AppWorks Service services client. Used to perform administrative type
 * calls with the Gateway, service deployment completion notices, retrieval
 * and registration of EIM Connectors.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.0.1.1
 */
public class ServiceClient extends AbstractOtagServiceClient {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceClient.class);

    public ServiceClient() {
        super();
    }

    public ServiceClient(Client restClient, AWConfigFactory configurationLoaderFactory) {
        // testing constructor
        super(restClient, configurationLoaderFactory);
    }

    /**
     * Allow a remote service to tell us that is has deployed successfully or not, as
     * the case may be. We expect <strong>all</strong> services to to do this else they
     * will not be made available by the Gateway.
     *
     * @param deploymentResult the deployment outcome
     * @return sdk response with a success indicator, true if we registered the deployment completion
     * with our managing Gateway
     * @throws APIException if a non 200 response is received
     */
    public SDKResponse completeDeployment(DeploymentResult deploymentResult) {
        String registerUrl = getManagingOtagUrl() + OTAG_DEPLOYMENTS_SERVICE_PATH + "manage/" +
                appName + "/status";

        WebTarget target = restClient.target(UrlPathUtil.getBaseUrl(registerUrl))
                .path(UrlPathUtil.getPath(registerUrl));

        Entity<DeploymentResult> reqEntity = Entity.entity(
                deploymentResult, MediaType.APPLICATION_JSON_TYPE);

        MultivaluedMap<String, Object> requestHeaders = getSDKRequestHeaders();
        try {
            return processGenericPost(registerUrl, target, reqEntity, requestHeaders);
        } catch (Exception e) {
            LOG.error("Failed to complete deployment", e);
            throw processFailureResponse(registerUrl, requestHeaders, e);
        }

    }

    /**
     * Get the list of EIM connectors currently available at the Gateway.
     *
     * @return list of EIM Connectors, including info on how to use the connection
     * @throws APIException if a non 200 response is received
     */
    public EIMConnectors getEIMConnectors() {
        String getConnectorsUrl = getManagingOtagUrl() + OTAG_DEPLOYMENTS_SERVICE_PATH + "manage/" +
                appName + "/eimconnectors";

        WebTarget target = restClient.target(UrlPathUtil.getBaseUrl(getConnectorsUrl))
                .path(UrlPathUtil.getPath(getConnectorsUrl));

        MultivaluedMap<String, Object> requestHeaders = getSDKRequestHeaders();
        try {
            Response response = target.request()
                    .header(APP_KEY_HEADER, appKey)
                    .get();

            int responseStatus = response.getStatus();
            String responseBody = response.readEntity(String.class);
            MultivaluedMap<String, Object> responseHeaders = response.getHeaders();

            validateResponse(getConnectorsUrl, requestHeaders, responseStatus, responseBody, responseHeaders);

            List<EIMConnector> connectors = getMapper()
                    .readValue(responseBody, new TypeReference<List<EIMConnector>>() {
                    });

            return new EIMConnectors(connectors,
                    new SDKCallInfo(getConnectorsUrl, requestHeaders, responseStatus,
                            responseHeaders, responseBody));
        } catch (Exception e) {
            LOG.error("Failed to retrieve the EIM connectors from the Gateway", e);
            throw processFailureResponse(getConnectorsUrl, requestHeaders, e);
        }
    }

    /**
     * Allow a service to register itself as an EIM connector.
     *
     * @param eimConnector to register
     * @return sdk response with a success indicator, true if registration succeeded, false otherwise
     * @throws APIException if a non 200 response is received
     */
    public SDKResponse registerConnector(EIMConnector eimConnector) {
        Objects.requireNonNull(eimConnector);
        String getConnectorsUrl = getManagingOtagUrl() + OTAG_DEPLOYMENTS_SERVICE_PATH + "manage/" +
                appName + "/eimconnectors";

        WebTarget target = restClient.target(UrlPathUtil.getBaseUrl(getConnectorsUrl))
                .path(UrlPathUtil.getPath(getConnectorsUrl));

        Entity<EIMConnector> connectorEntity = Entity.entity(
                eimConnector, MediaType.APPLICATION_JSON_TYPE);

        MultivaluedMap<String, Object> requestHeaders = getSDKRequestHeaders();
        try {
            return processGenericPost(getConnectorsUrl, target, connectorEntity, requestHeaders);
        } catch (Exception e) {
            LOG.error("Failed to register EIM connector with the Gateway", e);
            throw processFailureResponse(getConnectorsUrl, requestHeaders, e);
        }
    }

}
