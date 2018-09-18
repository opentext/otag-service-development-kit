package com.opentext.otag.sdk.types.v3.notification.fcm;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * See https://firebase.google.com/docs/reference/admin/java/reference/com/google/firebase/messaging/WebpushConfig
 */
public class WebpushConfig implements Serializable {

    /**
     * See https://tools.ietf.org/html/rfc8030#section-5 for supported HTTP push headers
     */
    private Map<String, String> headers = new HashMap<>();

    private Map<String, String> data = new HashMap<>();

    private WebpushNotification notification;

    public WebpushConfig() {
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public WebpushNotification getNotification() {
        return notification;
    }

    public void setNotification(WebpushNotification notification) {
        this.notification = notification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WebpushConfig that = (WebpushConfig) o;
        return Objects.equals(headers, that.headers) &&
                Objects.equals(data, that.data) &&
                Objects.equals(notification, that.notification);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headers, data, notification);
    }

    @Override
    public String toString() {
        return "WebpushConfig{" +
                "headers=" + headers +
                ", data=" + data +
                ", notification=" + notification +
                '}';
    }

}
