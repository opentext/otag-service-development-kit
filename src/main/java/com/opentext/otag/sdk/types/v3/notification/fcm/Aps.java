package com.opentext.otag.sdk.types.v3.notification.fcm;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Aps implements Serializable {

    private String alertString;

    private ApsAlert alert;

    private Integer badge;

    private String sound;

    private boolean contentAvailable;

    private boolean mutableContent;

    private String category;

    private String threadId;

    private Map<String, Object> customData = new HashMap<>();

    public Aps() {
    }

    public String getAlertString() {
        return alertString;
    }

    public void setAlertString(String alertString) {
        this.alertString = alertString;
    }

    public ApsAlert getAlert() {
        return alert;
    }

    public void setAlert(ApsAlert alert) {
        this.alert = alert;
    }

    public Integer getBadge() {
        return badge;
    }

    public void setBadge(Integer badge) {
        this.badge = badge;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public boolean isContentAvailable() {
        return contentAvailable;
    }

    public void setContentAvailable(boolean contentAvailable) {
        this.contentAvailable = contentAvailable;
    }

    public boolean isMutableContent() {
        return mutableContent;
    }

    public void setMutableContent(boolean mutableContent) {
        this.mutableContent = mutableContent;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public Map<String, Object> getCustomData() {
        return customData;
    }

    public void setCustomData(Map<String, Object> customData) {
        this.customData = customData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aps aps = (Aps) o;
        return contentAvailable == aps.contentAvailable &&
                mutableContent == aps.mutableContent &&
                Objects.equals(alertString, aps.alertString) &&
                Objects.equals(alert, aps.alert) &&
                Objects.equals(badge, aps.badge) &&
                Objects.equals(sound, aps.sound) &&
                Objects.equals(category, aps.category) &&
                Objects.equals(threadId, aps.threadId) &&
                Objects.equals(customData, aps.customData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alertString, alert, badge, sound, contentAvailable, mutableContent, category, threadId, customData);
    }

    @Override
    public String toString() {
        return "Aps{" +
                "alertString='" + alertString + '\'' +
                ", alert=" + alert +
                ", badge=" + badge +
                ", sound='" + sound + '\'' +
                ", contentAvailable=" + contentAvailable +
                ", mutableContent=" + mutableContent +
                ", category='" + category + '\'' +
                ", threadId='" + threadId + '\'' +
                ", customData=" + customData +
                '}';
    }
}
