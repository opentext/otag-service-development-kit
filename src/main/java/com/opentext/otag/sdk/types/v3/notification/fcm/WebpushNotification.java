package com.opentext.otag.sdk.types.v3.notification.fcm;

import java.io.Serializable;
import java.util.Objects;

/**
 * See https://firebase.google.com/docs/reference/admin/java/reference/com/google/firebase/messaging/WebpushNotification
 */
public class WebpushNotification implements Serializable {

    private  String title;

    private  String body;

    /**
     * URL to the notifications icon.
     */
    private  String icon;

    public WebpushNotification() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WebpushNotification that = (WebpushNotification) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(body, that.body) &&
                Objects.equals(icon, that.icon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, body, icon);
    }

    @Override
    public String toString() {
        return "WebpushNotification{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }

}
