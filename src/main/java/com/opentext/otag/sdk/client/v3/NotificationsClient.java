/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.client.v3;

import com.opentext.otag.sdk.bus.SdkEventKeys;
import com.opentext.otag.sdk.bus.SdkQueueEvent;
import com.opentext.otag.sdk.types.v3.api.SDKResponse;
import com.opentext.otag.sdk.types.v3.api.error.APIException;
import com.opentext.otag.sdk.types.v3.notification.ClientPushNotificationRequest;
import com.opentext.otag.sdk.types.v3.notification.NotificationRequest;
import com.opentext.otag.sdk.types.v3.notification.NotificationSeqBounds;
import com.opentext.otag.sdk.types.v3.notification.fcm.FcmPushNotificationRequest;
import com.opentext.otag.sdk.types.v4.SdkRequest;
import com.opentext.otag.service.context.AWConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;

/**
 * Notifications service client. Allows a service to issue a notification to
 * users/clients that it knows about. Also provides some of the other features
 * associated with the Gateways Notifications. To connect to the "real-time"
 * Notification service itself the Servlet exposed in the Gateway must be used.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.2
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
     * @deprecated  since 16.5.0, replaced by {@link #sendFcmPushNotification}
     */
    @Deprecated
    public SDKResponse sendPushNotification(ClientPushNotificationRequest notificationRequest) {
        // we just wrap the notification in the FCM version
        FcmPushNotificationRequest fcmReq = new FcmPushNotificationRequest(
                new ClientPushNotificationRequest.Builder(notificationRequest));
        return sendFcmPushNotification(fcmReq);
    }

    /**
     * Send a push notification request to the AppWorks Gateway to issue on your behalf to its
     * connected GCM clients (iOS and Android). This notification can be sent to multiple
     * clients based on the content of the request.
     *
     * @param notificationRequest FCM push notification request
     * @return true if the request was successful, false otherwise
     * @throws APIException if a non 200 response is received
     */
    public SDKResponse sendFcmPushNotification(FcmPushNotificationRequest notificationRequest) {
        SdkRequest<FcmPushNotificationRequest> sdkRequest = new SdkRequest<>(
                SdkEventKeys.NOTIFICATIONS_PUSH, notificationRequest);

        SdkQueueEvent sdkEvt = SdkQueueEvent.request(sdkRequest, getAppName(), getPersistenceContext());

        return sendSdkEventAndGetResponse(sdkEvt);
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
        SdkRequest<NotificationRequest> sdkRequest = new SdkRequest<>(
                SdkEventKeys.NOTIFICATIONS_SEND_WEB, notificationRequest);

        SdkQueueEvent sdkEvt = SdkQueueEvent.request(sdkRequest, getAppName(), getPersistenceContext());
        return sendSdkEventAndGetResponse(sdkEvt);
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
        SdkRequest request = new SdkRequest(SdkEventKeys.NOTIFICATIONS_GET_NOTIF_SEQ_BOUNDS);
        SdkQueueEvent getBoundsEvt = SdkQueueEvent.request(request, getAppName(), getPersistenceContext());

        return sendSdkEventAndGetTypedResponse(getBoundsEvt, NotificationSeqBounds.class);
    }

}
