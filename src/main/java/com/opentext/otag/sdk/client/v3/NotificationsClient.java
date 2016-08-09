/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.client.v3;

import com.opentext.otag.sdk.types.v3.api.SDKResponse;
import com.opentext.otag.sdk.types.v3.api.error.APIException;
import com.opentext.otag.sdk.types.v3.notification.ClientPushNotificationRequest;
import com.opentext.otag.sdk.types.v3.notification.NotificationRequest;
import com.opentext.otag.sdk.types.v3.notification.NotificationSeqBounds;
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

/**
 * Notifications service client. Allows a service to issue a notification to
 * users/clients that it knows about. Also provides some of the other features
 * associated with the Gateways Notifications. To connect to the "real-time"
 * Notification service itself the Servlet exposed in the Gateway must be used.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.0.1.1
 *
 * @see NotificationRequest
 */
public class NotificationsClient extends AbstractOtagServiceClient {

    private static final Logger LOG = LoggerFactory.getLogger(NotificationsClient.class);

    private static final String NOTIFICATION_SERVICE_PATH = OTAG_DEPLOYMENTS_SERVICE_PATH + "notifications/";

    public NotificationsClient() {
        super();
    }

    public NotificationsClient(Client restClient, AWConfigFactory configurationLoaderFactory) {
        // testing constructor
        super(restClient, configurationLoaderFactory);
    }

    /**
     * Send a push notification request to the AppWorks Gateway to issue on your behalf to its
     * connected GCM clients (iOS and Android). This notification can be sent to multiple
     * clients based on the content of the request.
     *
     * @param notificationRequest push notification request
     * @return true if the request was successful, false otherwise
     * @throws APIException if a non 200 response is received
     */
    public SDKResponse sendPushNotification(ClientPushNotificationRequest notificationRequest) {
        String requestUrl = getManagingOtagUrl() + NOTIFICATION_SERVICE_PATH + appName + "/push";

        WebTarget target = restClient.target(UrlPathUtil.getBaseUrl(requestUrl))
                .path(UrlPathUtil.getPath(requestUrl));

        Entity<ClientPushNotificationRequest> requestEntity = Entity.entity(
                notificationRequest, MediaType.APPLICATION_JSON_TYPE);

        MultivaluedMap<String, Object> requestHeaders = getSDKRequestHeaders();
        try {
            return processGenericPost(requestUrl , target, requestEntity, requestHeaders);
        } catch (Exception e) {
            LOG.error("Failed to send push notification", e);
            throw processFailureResponse(requestUrl, requestHeaders, e);
        }

    }

    /**
     * Send a legacy notification request to the AppWorks Gateway to issue on your behalf to its
     * connected users.
     *
     * @param notificationRequest request
     * @return sdk response with a success indicator
     * @throws APIException if a non 200 response is received
     */
    @Deprecated
    public SDKResponse sendNotification(NotificationRequest notificationRequest) {
        String requestUrl = getManagingOtagUrl() + NOTIFICATION_SERVICE_PATH + appName;

        WebTarget target = restClient.target(UrlPathUtil.getBaseUrl(requestUrl))
                .path(UrlPathUtil.getPath(requestUrl));

        Entity<NotificationRequest> requestEntity = Entity.entity(
                notificationRequest, MediaType.APPLICATION_JSON_TYPE);

        MultivaluedMap<String, Object> requestHeaders = getSDKRequestHeaders();
        try {
            return processGenericPost(requestUrl, target, requestEntity, requestHeaders);
        } catch (Exception e) {
            LOG.error("Failed to send Notification", e);
            throw processFailureResponse(requestUrl, requestHeaders, e);
        }

    }

    /**
     * Retrieve the lowest and highest sequence numbers currently found in the
     * Notifications table. This is useful for clients to understand where they
     * are in terms of the notifications stream, the Gateway regularly performs
     * housekeeping on that table so these bounds value are actually dynamic.
     * <p>
     * For instance, if a clients last known sequence is before the current low
     * bound we know they are definitely need to request new notifications.
     *
     * @return bounds object representing the most recent and oldest notification ids
     */
    public NotificationSeqBounds getNotificationsMinMaxSeq() {
        String requestUrl = getManagingOtagUrl() + NOTIFICATION_SERVICE_PATH + appName + "/seqBounds";

        WebTarget target = restClient.target(UrlPathUtil.getBaseUrl(requestUrl))
                .path(UrlPathUtil.getPath(requestUrl));

        MultivaluedMap<String, Object> requestHeaders = getSDKRequestHeaders();
        try {
            Response response = target.request()
                    .headers(requestHeaders)
                    .get();

            int responseStatus = response.getStatus();
            String responseBody = response.readEntity(String.class);
            MultivaluedMap<String, Object> responseHeaders = response.getHeaders();

            validateResponse(requestUrl, requestHeaders, responseStatus, responseBody, responseHeaders);

            return getMapper().readValue(responseBody, NotificationSeqBounds.class);
        } catch (Exception e) {
            LOG.error("Failed to get notification min/max seq", e);
            return null;
        }
    }

}
