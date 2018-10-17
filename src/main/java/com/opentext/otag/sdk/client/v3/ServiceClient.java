/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.client.v3;

import com.opentext.otag.sdk.bus.SdkQueueEvent;
import com.opentext.otag.sdk.bus.SdkQueueManager;
import com.opentext.otag.sdk.types.v3.api.SDKResponse;
import com.opentext.otag.sdk.types.v3.api.error.APIException;
import com.opentext.otag.sdk.types.v3.management.DeploymentResult;
import com.opentext.otag.sdk.types.v3.sdk.EIMConnector;
import com.opentext.otag.sdk.types.v3.sdk.EIMConnectors;
import com.opentext.otag.sdk.types.v4.SdkRequest;
import com.opentext.otag.service.context.AWConfigFactory;

import javax.ws.rs.client.Client;

import static com.opentext.otag.sdk.bus.SdkEventKeys.*;

/**
 * AppWorks Service services client. Used to perform administrative type
 * calls with the Gateway, service deployment completion notices, retrieval
 * and registration of EIM Connectors.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.2
 */
public class ServiceClient extends AbstractOtagServiceClient {

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
        SdkRequest<DeploymentResult> sdkRequest = new SdkRequest<>(SERVICE_MGMT_COMPLETE_DEPLOYMENT, deploymentResult);

        SdkQueueEvent completeDeployReq = SdkQueueEvent.request(sdkRequest, getAppName(), getPersistenceContext());

        return sendSdkEventAndGetResponse(completeDeployReq);
    }

    /**
     * Get the list of EIM connectors currently available at the Gateway.
     *
     * @return list of EIM Connectors, including info on how to use the connection
     * @throws APIException if a non 200 response is received
     */
    public EIMConnectors getEIMConnectors() {
        SdkQueueEvent sdkQueueEvent = SdkQueueEvent.request(
                new SdkRequest<>(GET_EIM_CONNECTORS),getAppName(), getPersistenceContext());

        SdkQueueManager.sendEventToGateway(sdkQueueEvent);
        return sendSdkEventAndGetTypedResponse(sdkQueueEvent, EIMConnectors.class);
    }

    /**
     * Allow a service to register itself as an EIM connector.
     *
     * @param eimConnector to register
     * @return sdk response with a success indicator, true if registration succeeded, false otherwise
     * @throws APIException if a non 200 response is received
     */
    public SDKResponse registerConnector(EIMConnector eimConnector) {
        SdkQueueEvent registerEvent = SdkQueueEvent.request(
                new SdkRequest<>(REGISTER_EIM_CONNECTOR, eimConnector),
                getAppName(), getPersistenceContext());

        return sendSdkEventAndGetResponse(registerEvent);
    }

}
