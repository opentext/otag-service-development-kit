package com.opentext.otag.sdk.types.v3.notification.fcm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ApsAlert implements Serializable {

    private String title;

    private String body;

    private String locKey;

    private List<String> locArgs;

    private String titleLocKey;

    private List<String> titleLocArgs = new ArrayList<>();

    private String actionLocKey;

    private String launchImage;

    public ApsAlert() {
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

    public String getLocKey() {
        return locKey;
    }

    public void setLocKey(String locKey) {
        this.locKey = locKey;
    }

    public List<String> getLocArgs() {
        return locArgs;
    }

    public void setLocArgs(List<String> locArgs) {
        this.locArgs = locArgs;
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

    public String getActionLocKey() {
        return actionLocKey;
    }

    public void setActionLocKey(String actionLocKey) {
        this.actionLocKey = actionLocKey;
    }

    public String getLaunchImage() {
        return launchImage;
    }

    public void setLaunchImage(String launchImage) {
        this.launchImage = launchImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApsAlert apsAlert = (ApsAlert) o;
        return Objects.equals(title, apsAlert.title) &&
                Objects.equals(body, apsAlert.body) &&
                Objects.equals(locKey, apsAlert.locKey) &&
                Objects.equals(locArgs, apsAlert.locArgs) &&
                Objects.equals(titleLocKey, apsAlert.titleLocKey) &&
                Objects.equals(titleLocArgs, apsAlert.titleLocArgs) &&
                Objects.equals(actionLocKey, apsAlert.actionLocKey) &&
                Objects.equals(launchImage, apsAlert.launchImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, body, locKey, locArgs, titleLocKey, titleLocArgs, actionLocKey, launchImage);
    }

    @Override
    public String toString() {
        return "ApsAlert{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", locKey='" + locKey + '\'' +
                ", locArgs=" + locArgs +
                ", titleLocKey='" + titleLocKey + '\'' +
                ", titleLocArgs=" + titleLocArgs +
                ", actionLocKey='" + actionLocKey + '\'' +
                ", launchImage='" + launchImage + '\'' +
                '}';
    }
}
