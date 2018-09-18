package com.opentext.otag.sdk.types.v3.notification.fcm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Android specific data for a push notification, as supported by Firebase.
 * <p>
 * See https://firebase.google.com/docs/reference/admin/java/reference/com/google/firebase/messaging/AndroidNotification
 */
public class AndroidNotification implements Serializable {

    private String title;

    private String body;

    /**
     * Icon resource for the notification.
     */
    private String icon;

    /**
     * Color specified in the #rrggbb format.
     */
    private String color;

    /**
     * 	File name of the sound resource or "default".
     */
    private String sound;

    private String tag;

    private String clickAction;

    private String bodyLocKey;

    private List<String> bodyLocArgs = new ArrayList<>();

    private String titleLocKey;

    private List<String> titleLocArgs = new ArrayList<>();

    public AndroidNotification() {
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getClickAction() {
        return clickAction;
    }

    public void setClickAction(String clickAction) {
        this.clickAction = clickAction;
    }

    public String getBodyLocKey() {
        return bodyLocKey;
    }

    public void setBodyLocKey(String bodyLocKey) {
        this.bodyLocKey = bodyLocKey;
    }

    public List<String> getBodyLocArgs() {
        return bodyLocArgs;
    }

    public void setBodyLocArgs(List<String> bodyLocArgs) {
        this.bodyLocArgs = bodyLocArgs;
    }

    public String getTitleLocKey() {
        return titleLocKey;
    }

    public void setTitleLocKey(String titleLocKey) {
        this.titleLocKey = titleLocKey;
    }

    public List<String> getTitleLocArgs() {
        return titleLocArgs;
    }

    public void setTitleLocArgs(List<String> titleLocArgs) {
        this.titleLocArgs = titleLocArgs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AndroidNotification that = (AndroidNotification) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(body, that.body) &&
                Objects.equals(icon, that.icon) &&
                Objects.equals(color, that.color) &&
                Objects.equals(sound, that.sound) &&
                Objects.equals(tag, that.tag) &&
                Objects.equals(clickAction, that.clickAction) &&
                Objects.equals(bodyLocKey, that.bodyLocKey) &&
                Objects.equals(bodyLocArgs, that.bodyLocArgs) &&
                Objects.equals(titleLocKey, that.titleLocKey) &&
                Objects.equals(titleLocArgs, that.titleLocArgs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, body, icon, color, sound, tag,
                clickAction, bodyLocKey, bodyLocArgs, titleLocKey, titleLocArgs);
    }

    @Override
    public String toString() {
        return "AndroidNotification{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", icon='" + icon + '\'' +
                ", color='" + color + '\'' +
                ", sound='" + sound + '\'' +
                ", tag='" + tag + '\'' +
                ", clickAction='" + clickAction + '\'' +
                ", bodyLocKey='" + bodyLocKey + '\'' +
                ", bodyLocArgs=" + bodyLocArgs +
                ", titleLocKey='" + titleLocKey + '\'' +
                ", titleLocArgs=" + titleLocArgs +
                '}';
    }

}
