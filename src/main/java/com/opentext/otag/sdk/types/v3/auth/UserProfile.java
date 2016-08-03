/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.types.v3.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.opentext.otag.sdk.types.v3.SDKType;
import com.opentext.otag.sdk.types.v3.api.SDKCallInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * A general user profile, we hope it has enough info to convey a user
 * profile across our auth providers.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.0.1.1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProfile extends SDKType {

    protected String userName;

    protected String firstName;

    protected String lastName;

    protected String fullName;

    protected String email;

    protected String userID;

    protected String phone;

    protected String title;

    protected String location;

    protected Integer userPhotoSuffix = 0;

    @JsonProperty("isAdmin")
    protected boolean admin = false;

    /**
     * Security attributes.
     */
    protected Map<String, String> attributes = new HashMap<>();

    /**
     * Allows a back-end service to customise their user profile, adding any additional
     * properties they need.
     */
    protected Map<String, String> additionalProperties = new HashMap<>();

    /**
     * The seq no of the last Notification record this user saw. These values
     * are assigned sequentially giving us some kind of idea if the user has
     * an up to date view of things.
     */
    private Long lastSeenEvent;

    public UserProfile() {
    }

    public UserProfile(SDKCallInfo sdkCallInfo) {
        super(sdkCallInfo);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String getUserID) {
        this.userID = getUserID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Long getLastSeenEvent() {
        return lastSeenEvent;
    }

    public void setLastSeenEvent(Long getLastSeenEvent) {
        this.lastSeenEvent = getLastSeenEvent;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public Integer getUserPhotoSuffix() {
        return userPhotoSuffix;
    }

    public void setUserPhotoSuffix(Integer userPhotoSuffix) {
        this.userPhotoSuffix = userPhotoSuffix;
    }

    public Map<String, String> getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, String> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public void addProfileProperty(String name, String value) {
        additionalProperties.put(name, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserProfile that = (UserProfile) o;

        if (admin != that.admin) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (fullName != null ? !fullName.equals(that.fullName) : that.fullName != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (userID != null ? !userID.equals(that.userID) : that.userID != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (location != null ? !location.equals(that.location) : that.location != null) return false;
        if (userPhotoSuffix != null ? !userPhotoSuffix.equals(that.userPhotoSuffix) : that.userPhotoSuffix != null)
            return false;
        if (attributes != null ? !attributes.equals(that.attributes) : that.attributes != null) return false;
        if (additionalProperties != null ? !additionalProperties.equals(that.additionalProperties) : that.additionalProperties != null)
            return false;
        return !(lastSeenEvent != null ? !lastSeenEvent.equals(that.lastSeenEvent) : that.lastSeenEvent != null);

    }

    @Override
    public int hashCode() {
        int result = userName != null ? userName.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (userID != null ? userID.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (userPhotoSuffix != null ? userPhotoSuffix.hashCode() : 0);
        result = 31 * result + (admin ? 1 : 0);
        result = 31 * result + (attributes != null ? attributes.hashCode() : 0);
        result = 31 * result + (additionalProperties != null ? additionalProperties.hashCode() : 0);
        result = 31 * result + (lastSeenEvent != null ? lastSeenEvent.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", userID='" + userID + '\'' +
                ", phone='" + phone + '\'' +
                ", title='" + title + '\'' +
                ", location='" + location + '\'' +
                ", userPhotoSuffix=" + userPhotoSuffix +
                ", admin=" + admin +
                ", attributes=" + attributes +
                ", additionalProperties=" + additionalProperties +
                ", lastSeenEvent=" + lastSeenEvent +
                '}';
    }

}
