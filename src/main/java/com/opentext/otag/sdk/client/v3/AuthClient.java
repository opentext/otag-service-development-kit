/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.client.v3;

import com.opentext.otag.sdk.bus.SdkEventKeys;
import com.opentext.otag.sdk.bus.SdkQueueEvent;
import com.opentext.otag.sdk.handlers.AuthRequestHandler;
import com.opentext.otag.sdk.types.v3.api.SDKResponse;
import com.opentext.otag.sdk.types.v3.api.error.APIException;
import com.opentext.otag.sdk.types.v3.auth.*;
import com.opentext.otag.sdk.types.v4.SdkRequest;
import com.opentext.otag.service.context.AWConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import java.util.Objects;

/**
 * AppWorks Deployments Service API client designed to work with the auth mechanism the
 * AppWorks Gateway provides. You can validate requests from clients that may hit your
 * service directly rather than using the Gateways auth endpoint.
 * <p>
 * There is also functionality to allow you to retrieve information about users you already
 * know about, and to register {@link AuthRequestHandler}s if your service contains any.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.2
 *
 * @see com.opentext.otag.sdk.handlers.AuthRequestHandler
 */
public class AuthClient extends AbstractOtagServiceClient {

    private static final Logger LOG = LoggerFactory.getLogger(AuthClient.class);

    public static final String ID_SERVICE_PATH = OTAG_DEPLOYMENTS_SERVICE_PATH + "auth/";

    public AuthClient() {
        super();
    }

    public AuthClient(Client restClient, AWConfigFactory configurationLoaderFactory) {
        // testing constructor, allows a custom AWConfigFactory to be passed
        // to a client under test, avoiding the bootstrapping which involves IO
        super(restClient, configurationLoaderFactory);
    }

    /**
     * Get the user for the supplied otagToken.
     *
     * @param otagToken otag token
     * @return a user
     * @throws APIException if a non 200 response is received
     */
    public AuthorizedUser getUserForToken(String otagToken) {
        SdkQueueEvent getUserEvt = SdkQueueEvent.request(
                new SdkRequest<>(SdkEventKeys.AUTH_GET_TOKEN_FOR_USER, otagToken),
                getAppName(), getPersistenceContext());

        return sendSdkEventAndGetTypedResponse(getUserEvt, AuthorizedUser.class);
    }

    /**
     * OTDS based auth only! Get a list of OTDS group ids that are associated
     * with the supplied user.
     *
     * @param user user
     * @return group ids or an empty list if we fail
     * @throws APIException if a non 200 response is received
     */
    public UserGroupIdList getUsersGroupIds(User user) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(user.getUserName());

        SdkQueueEvent getUserGroupsIds = SdkQueueEvent.request(
                new SdkRequest<>(SdkEventKeys.AUTH_LIST_GROUP_USER_IDS, user.getUserName()),
                getAppName(), getPersistenceContext());

        return sendSdkEventAndGetTypedResponse(getUserGroupsIds, UserGroupIdList.class);
    }

    /**
     * Get the user profile for a given username.
     *
     * @param userName username
     * @return a user profile is one is found, or null if the request fails or no such user exists
     * @throws APIException if a non 200 response is received
     */
    public UserProfile getUserProfie(String userName) {
        SdkQueueEvent getUserGroupsIds = SdkQueueEvent.request(
                new SdkRequest<>(SdkEventKeys.AUTH_GET_USER_PROFILE, userName),
                getAppName(), getPersistenceContext());

        return sendSdkEventAndGetTypedResponse(getUserGroupsIds, UserProfile.class);
    }

    /**
     * Register a list of auth handlers that can respond to Gateway auth
     * requests.
     *
     * @param request handler registration request
     * @return sdk response with a success indicator
     * @throws APIException if a non 200 response is received
     *
     * @see com.opentext.otag.sdk.handlers.AuthRequestHandler
     */
    public SDKResponse registerAuthHandlers(RegisterAuthHandlersRequest request) {
        SdkQueueEvent registerHandlerEvt = SdkQueueEvent.request(
                new SdkRequest<>(SdkEventKeys.AUTH_REGISTER_AUTH_HANDLERS, request),
                getAppName(), getPersistenceContext());

        return sendSdkEventAndGetResponse(registerHandlerEvt);

    }

    private String getManagementPath(String appName) {
        return getManagingOtagUrl() + ID_SERVICE_PATH + appName + "/";
    }

}
