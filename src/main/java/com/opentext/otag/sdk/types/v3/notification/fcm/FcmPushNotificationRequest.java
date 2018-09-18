package com.opentext.otag.sdk.types.v3.notification.fcm;

import com.opentext.otag.sdk.types.v3.notification.ClientPushNotificationRequest;

/**
 * A push notification request that accommodates extra functionality provided by
 * Google's Firebase Cloud Messaging platform.
 */
public class FcmPushNotificationRequest extends ClientPushNotificationRequest {

    /**
     * The name of the AppWorks app this message is for.
     */
    private String targetAppName;

    /**
     * A collapse key that should be used to overwrite previous messages that also have the same
     * collapse key.
     */
    private String collapseKey;

    /**
     * The name of the topic you wish to send this message to, this topic will be created automatically
     * if it does not exist already.
     */
    private String topicName;

    /**
     * Topic messages are sent for a specific Firebase project.
     */
    private String topicDestinationProjectId;

    private AndroidConfig androidConfig;

    private ApnsConfig apnsConfig;

    private WebpushConfig webpushConfig;

    public FcmPushNotificationRequest() {
    }

    public FcmPushNotificationRequest(ClientPushNotificationRequest.Builder builder) {
        super(builder);
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicDestinationProjectId() {
        return topicDestinationProjectId;
    }

    public void setTopicDestinationProjectId(String topicDestinationProjectId) {
        this.topicDestinationProjectId = topicDestinationProjectId;
    }

    public AndroidConfig getAndroidConfig() {
        return androidConfig;
    }

    public void setAndroidConfig(AndroidConfig androidConfig) {
        this.androidConfig = androidConfig;
    }

    public ApnsConfig getApnsConfig() {
        return apnsConfig;
    }

    public void setApnsConfig(ApnsConfig apnsConfig) {
        this.apnsConfig = apnsConfig;
    }

    public WebpushConfig getWebpushConfig() {
        return webpushConfig;
    }

    public void setWebpushConfig(WebpushConfig webpushConfig) {
        this.webpushConfig = webpushConfig;
    }

    public String getTargetAppName() {
        return targetAppName;
    }

    public void setTargetAppName(String targetAppName) {
        this.targetAppName = targetAppName;
    }

    public String getCollapseKey() {
        return collapseKey;
    }

    public void setCollapseKey(String collapseKey) {
        this.collapseKey = collapseKey;
    }

    @Override
    public String toString() {
        return "FcmPushNotificationRequest{" +
                "targetAppName='" + targetAppName + '\'' +
                ", collapseKey='" + collapseKey + '\'' +
                ", topicName='" + topicName + '\'' +
                ", topicDestinationProjectId='" + topicDestinationProjectId + '\'' +
                ", androidConfig=" + androidConfig +
                ", apnsConfig=" + apnsConfig +
                ", webpushConfig=" + webpushConfig +
                "} " + super.toString();
    }

    public boolean hasTopic() {
        return topicName != null && topicName.trim().length() > 0 &&
                topicDestinationProjectId != null  && topicDestinationProjectId.trim().length() > 0;
    }

    public boolean hasCollapseKey() {
        return collapseKey != null;
    }

}
