package com.opentext.otag.sdk.types.v3.notification.fcm;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * See https://firebase.google.com/docs/reference/admin/java/reference/com/google/firebase/messaging/AndroidConfig
 */
public class AndroidConfig implements Serializable {

    private String collapseKey;

    private String priority;

    private String ttl;

    private String restrictedPackageName;

    private Map<String, String> data = new HashMap<>();

    private AndroidNotification notification;

    public AndroidConfig() {
    }

    public String getCollapseKey() {
        return collapseKey;
    }

    public void setCollapseKey(String collapseKey) {
        this.collapseKey = collapseKey;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    public String getRestrictedPackageName() {
        return restrictedPackageName;
    }

    public void setRestrictedPackageName(String restrictedPackageName) {
        this.restrictedPackageName = restrictedPackageName;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public AndroidNotification getNotification() {
        return notification;
    }

    public void setNotification(AndroidNotification notification) {
        this.notification = notification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AndroidConfig that = (AndroidConfig) o;
        return Objects.equals(collapseKey, that.collapseKey) &&
                Objects.equals(priority, that.priority) &&
                Objects.equals(ttl, that.ttl) &&
                Objects.equals(restrictedPackageName, that.restrictedPackageName) &&
                Objects.equals(data, that.data) &&
                Objects.equals(notification, that.notification);
    }

    @Override
    public int hashCode() {
        return Objects.hash(collapseKey, priority, ttl, restrictedPackageName, data, notification);
    }

    @Override
    public String toString() {
        return "AndroidConfig{" +
                "collapseKey='" + collapseKey + '\'' +
                ", priority='" + priority + '\'' +
                ", ttl='" + ttl + '\'' +
                ", restrictedPackageName='" + restrictedPackageName + '\'' +
                ", data=" + data +
                ", notification=" + notification +
                '}';
    }
}
