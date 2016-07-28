/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.client.v3;

import com.fasterxml.jackson.core.type.TypeReference;
import com.opentext.otag.sdk.handlers.AuthRequestHandler;
import com.opentext.otag.sdk.types.v3.api.SDKCallInfo;
import com.opentext.otag.sdk.types.v3.api.SDKResponse;
import com.opentext.otag.sdk.types.v3.api.error.APIException;
import com.opentext.otag.sdk.types.v3.auth.*;
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
 * AppWorks Deployments Service API client designed to work with the auth mechanism the
 * AppWorks Gateway provides. You can validate requests from clients that may hit your
 * service directly rather than using the Gateways auth endpoint.
 * <p>
 * There is also functionality to allow you to retrieve information about users you already
 * know about, and to register {@link AuthRequestHandler}s if your service contains any.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.0.1
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
        // testing constructor
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
        String getUrl = getManagementPath(appName) + "user";

        WebTarget target = restClient.target(UrlPathUtil.getBaseUrl(getUrl))
                .path(UrlPathUtil.getPath(getUrl));

        MultivaluedMap<String, Object> requestHeaders = getSDKRequestHeaders(otagToken);

        try {
            Response response = target.request()
                    .headers(requestHeaders)
                    .get();

            int responseStatus = response.getStatus();
            String responseBody = response.readEntity(String.class);
            MultivaluedMap<String, Object> responseHeaders = response.getHeaders();

            validateResponse(getUrl, requestHeaders, responseStatus, responseBody, responseHeaders);

            AuthorizedUser authorizedUser = getMapper().readValue(responseBody, AuthorizedUser.class);
            SDKCallInfo callInfo = new SDKCallInfo(getUrl, requestHeaders, responseStatus,
                    responseHeaders, responseBody);
            authorizedUser.setSdkCallInfo(callInfo);
            return authorizedUser;
        } catch (Exception e) {
            LOG.warn("Unable to get user for token, returning null", e);
            throw processFailureResponse(getUrl, requestHeaders, e);
        }
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

        String getUrl = getManagementPath(appName) + "users/" + user.getUserName() + "/groups";

        WebTarget target = restClient.target(UrlPathUtil.getBaseUrl(getUrl))
                .path(UrlPathUtil.getPath(getUrl));

        MultivaluedMap<String, Object> requestHeaders = getSDKRequestHeaders();
        try {
            Response response = target.request().headers(requestHeaders).get();

            int responseStatus = response.getStatus();
            String responseBody = response.readEntity(String.class);
            MultivaluedMap<String, Object> responseHeaders = response.getHeaders();

            validateResponse(getUrl, requestHeaders, responseStatus, responseBody, responseHeaders);

            List<String> ids = getMapper().readValue(responseBody, new TypeReference<List<String>>() {});
            SDKCallInfo callInfo = new SDKCallInfo(getUrl, requestHeaders, responseStatus,
                    responseHeaders, responseBody);

            return new UserGroupIdList(ids, callInfo);
        } catch (Exception e) {
            LOG.error("Unable to get users group ids, returning empty list", e);
            throw processFailureResponse(getUrl, requestHeaders, e);
        }
    }

    /**
     * Get the user profile for a given username.
     *
     * @param userName username
     * @return a user profile is one is found, or null if the request fails or no such user exists
     * @throws APIException if a non 200 response is received
     */
    public UserProfile getUserProfie(String userName) {
        String getUrl = getManagementPath(appName) + "users/" + userName + "/profile";

        WebTarget target = restClient.target(UrlPathUtil.getBaseUrl(getUrl))
                .path(UrlPathUtil.getPath(getUrl));

        MultivaluedMap<String, Object> requestHeaders = getSDKRequestHeaders();
        try {
            Response response = target.request()
                    .header(APP_KEY_HEADER, appKey)
                    .accept(MediaType.APPLICATION_JSON_TYPE)
                    .get();

            int responseStatus = response.getStatus();
            String responseBody = response.readEntity(String.class);
            MultivaluedMap<String, Object> responseHeaders = response.getHeaders();

            validateResponse(getUrl, requestHeaders, responseStatus, responseBody, responseHeaders);

            OtdsUserProfile userProfile = getMapper().readValue(responseBody, OtdsUserProfile.class);
            userProfile.setSdkCallInfo(new SDKCallInfo(getUrl, requestHeaders, responseStatus,
                    responseHeaders, responseBody));

            return userProfile;
        } catch (Exception e) {
            LOG.error("Unable to get user profile, returning null", e);
            throw processFailureResponse(getUrl, requestHeaders, e);
        }
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
        String registerUrl = getManagementPath(appName) + "handlers";

        WebTarget target = restClient.target(UrlPathUtil.getBaseUrl(registerUrl))
                .path(UrlPathUtil.getPath(registerUrl));

        Entity<RegisterAuthHandlersRequest> requestEntity = Entity.entity(
                request, MediaType.APPLICATION_JSON_TYPE);

        MultivaluedMap<String, Object> requestHeaders = getSDKRequestHeaders();
        try {
            return processGenericPost(registerUrl, target, requestEntity, requestHeaders);
        } catch (Exception e) {
            LOG.error("Failed to register auth handlers", e);
            throw processFailureResponse(registerUrl, requestHeaders, e);
        }
    }

    private String getManagementPath(String appName) {
        return getManagingOtagUrl() + ID_SERVICE_PATH + appName + "/";
    }

}
