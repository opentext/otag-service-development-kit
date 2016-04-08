/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.client.v3;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.opentext.otag.sdk.types.v3.ErrorResponseWrapper;
import com.opentext.otag.sdk.types.v3.api.SDKCallInfo;
import com.opentext.otag.sdk.types.v3.api.SDKResponse;
import com.opentext.otag.sdk.types.v3.api.error.*;
import com.opentext.otag.sdk.util.UrlPathUtil;
import com.opentext.otag.service.context.AWConfig;
import com.opentext.otag.service.context.AWConfigFactory;
import com.opentext.otag.tomcat.TomcatLifecycleListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * This class provides the base implementation for an AppWorks Service REST client.
 * These clients make use of the Gateways dedicated Deployment Management/Service
 * APIs. These APIs expose Gateway services and features to the deployed services
 * that the Gateway manages.
 * <p>
 * A Service client implementation's methods tend to return null, empty collections or false
 * when they fail depending on the type of action they are performing. Exceptions are logged at
 * the ERROR level. The service clients make use of the Jersey implementation of the standard
 * JAX-RS {@link Client}, and use the Jackson library for JSON marshalling.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.0.0
 *
 * @see AWConfig
 */
public class AbstractOtagServiceClient {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractOtagServiceClient.class);

    public static final String APP_KEY_HEADER = "otagAppKey";
    static final String OTAG_DEPLOYMENTS_SERVICE_PATH = "/deployments/";
    private static final List<Integer> HTTP_ERROR_STATUS_CODES = Arrays.asList(400, 401, 403, 404, 500);
    private static final String OTAGTOKEN_HEADER = "otagtoken";

    private static final String EARLY_CLIENT_CONSTRUCTION_ERROR = "ServiceClient instances cannot be used safely before the " +
            "host container has been setup and the Gateway has started, please delay client " +
            "construction until after AWServiceContextHandler#onStart has fired within your" +
            "services @AWServiceStartupComplete method has fired";


    /**
     * Jersey client used to facilitate communications with the OTAG service endpoints.
     */
    protected Client restClient;

    /**
     * Shared secret, between Gateway and the AppWorks service.
     */
    protected String appKey;

    /**
     * Unique name of the app.
     */
    protected String appName;

    /**
     * URL we should use for Gateway service APIs.
     */
    private String managingOtagUrl;

    /**
     * A factory used to configure how the {@link AWConfig} instances
     * used to retrieve service details are constructed. Useful for
     * testing client implementations outside of a real web context.
     */
    private AWConfigFactory configurationLoaderFactory;

    /**
     * Used to resolve JSON responses into our SDK types.
     */
    private static ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    /**
     * ServiceClient constructor, performs various setup duties, including
     * reading the service configuration meta-data. This data allows us
     * to understand how we should be communicating with our managing Gateway instance.
     */
    public AbstractOtagServiceClient() {
        ensureGatewayIsReady();

        configurationLoaderFactory = AWConfigFactory.defaultFactory();
        restClient = ClientBuilder.newClient().register(JacksonJsonProvider.class);
        // read config from deployment meta-data file created by managing service
        setConfig();
    }

    /**
     * Testing constructor.
     *
     * @param restClient                 the REST client that should be used for HTTP communications
     * @param configurationLoaderFactory the factory that produces {@link AWConfig} instances that will
     *                                   read the service configuration (otagUrl, appKey, etc.) from somewhere
     */
    public AbstractOtagServiceClient(Client restClient, AWConfigFactory configurationLoaderFactory) {
        ensureGatewayIsReady();

        this.restClient = restClient;
        this.configurationLoaderFactory = configurationLoaderFactory;

        setConfig();
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getManagingOtagUrl() {
        if (managingOtagUrl != null && !managingOtagUrl.isEmpty())
            return managingOtagUrl;

        // attempt to read file
        AWConfig appWorksConfig = configurationLoaderFactory.getConfig();
        setManagingOtagUrl(appWorksConfig.getGatewayUrl());
        return managingOtagUrl;
    }

    public void setManagingOtagUrl(String managingOtagUrl) {
        this.managingOtagUrl = managingOtagUrl;
    }

    public Client getRestClient() {
        return restClient;
    }

    // overwrite REST client for testing purposes
    public void setRestClient(Client restClient) {
        this.restClient = restClient;
    }

    public AWConfigFactory getConfigurationLoaderFactory() {
        return configurationLoaderFactory;
    }

    public void setConfigurationLoaderFactory(AWConfigFactory configurationLoaderFactory) {
        this.configurationLoaderFactory = configurationLoaderFactory;
    }

    public APIException processFailureResponse(String url, MultivaluedMap<String, Object> requestHeaders,
                                               int responseStatus, String responseBody,
                                               MultivaluedMap<String, Object> responseHeaders) {
        return processErrorResponse(url, requestHeaders, responseStatus, responseBody, responseHeaders, null);
    }

    public APIException processFailureResponse(String url,
                                               MultivaluedMap<String, Object> requestHeaders,
                                               Exception exception) throws APIException {
        if (exception instanceof APIException) throw (APIException) exception;

        return processErrorResponse(url, requestHeaders, -1, null, null, exception);
    }

    protected ObjectMapper getMapper() {
        return OBJECT_MAPPER;
    }

    protected WebTarget getTarget(String registerUrl) {
        return restClient.target(UrlPathUtil.getBaseUrl(registerUrl))
                .path(UrlPathUtil.getPath(registerUrl));
    }

    protected MultivaluedMap<String, Object> getSDKRequestHeaders(String otagToken) {
        MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.add(APP_KEY_HEADER, appKey);
        if (otagToken != null)
            headers.add(OTAGTOKEN_HEADER, otagToken);
        return headers;
    }

    protected MultivaluedMap<String, Object> getSDKRequestHeaders() {
        return getSDKRequestHeaders(null);
    }

    protected boolean isErrorStatus(int status) {
        return HTTP_ERROR_STATUS_CODES.contains(status);
    }

    /**
     * Perform a POST via the SDK that results in a boolean outcome.
     *
     * @param requestUrl     post URL
     * @param target         web target request builder
     * @param requestEntity  resource entity to post
     * @param requestHeaders headers to include in the post
     * @param <T>            request entity type
     * @return SDK response relaying the outcome
     * @throws APIException for non 200 status responses
     */
    protected <T> SDKResponse processGenericPost(String requestUrl,
                                                 WebTarget target,
                                                 Entity<T> requestEntity,
                                                 MultivaluedMap<String, Object> requestHeaders) throws APIException {
        Response response = target.request()
                .headers(requestHeaders)
                .post(requestEntity);

        int responseStatus = response.getStatus();
        String responseBody = response.readEntity(String.class);
        MultivaluedMap<String, Object> responseHeaders = response.getHeaders();
        validateResponse(requestUrl, requestHeaders, responseStatus, responseBody, responseHeaders);

        return new SDKResponse(true, new SDKCallInfo(requestUrl, requestHeaders, responseStatus,
                responseHeaders, responseBody));
    }

    protected void validateResponse(String requestUrl, MultivaluedMap<String, Object> requestHeaders,
                                    int responseStatus, String responseBody,
                                    MultivaluedMap<String, Object> responseHeaders) throws APIException {
        if (isErrorStatus(responseStatus))
            throw processFailureResponse(requestUrl, requestHeaders, responseStatus,
                    responseBody, responseHeaders);
    }

    private void ensureGatewayIsReady() {
        if (!TomcatLifecycleListener.containerReady()) {
            LOG.error(EARLY_CLIENT_CONSTRUCTION_ERROR);
            throw new IllegalStateException(EARLY_CLIENT_CONSTRUCTION_ERROR);
        }
    }

    private void setConfig() {
        AWConfig appWorksConfig = configurationLoaderFactory.getConfig();
        appName = appWorksConfig.getAppName();
        appKey = appWorksConfig.getAppKey();
        managingOtagUrl = appWorksConfig.getGatewayUrl();

        if (appKey == null || managingOtagUrl == null)
            throw new IllegalStateException("Unable to resolve local " +
                    "management.properties file. The deployment area has not been " +
                    "initialised correctly by the Gateway");
    }

    /**
     * Process a non 200 status API response. The supplied details are packaged in the
     * status relevant {@link APIException} type, and a {@link SDKCallInfo} instance
     * is embedded for debugging purposes.
     *
     * @param url             API URL
     * @param requestHeaders  outbound HTTP headers
     * @param responseStatus  response HTTP status code
     * @param responseBody    response body as text
     * @param responseHeaders response HTTP headers
     * @param exception       am Exception that may have been thrown by an SDK client request
     * @return an Exception representing the error outcome
     */
    private APIException processErrorResponse(String url,
                                              MultivaluedMap<String, Object> requestHeaders,
                                              int responseStatus,
                                              String responseBody,
                                              MultivaluedMap<String, Object> responseHeaders,
                                              Exception exception) {
        SDKCallInfo callInfo = new SDKCallInfo(url, requestHeaders, responseStatus,
                responseHeaders, responseBody);

        callInfo.setException(exception);

        try {
            ErrorResponseWrapper errorResponseWrapper = getMapper().readValue(responseBody, ErrorResponseWrapper.class);
            if (errorResponseWrapper != null) {
                callInfo.setErrorCode(errorResponseWrapper.getKey());
                callInfo.setErrorMessage(errorResponseWrapper.getErrorMessage());
            }
        } catch (IOException ignore) {
            // we may not have a sensible
        }

        switch (responseStatus) {
            case 400:
                return new BadRequestException(callInfo);
            case 401:
                return new UnauthorizedException(callInfo);
            case 404:
                return new NotFoundException(callInfo);
            case 500:
                return new InternalServerErrorException(callInfo);
            default:
                return new APIException("Unknown response status " + responseStatus, callInfo);
        }
    }

}
