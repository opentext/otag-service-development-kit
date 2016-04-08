/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.types.v3.client;

import java.io.Serializable;

public class UserInfo implements Serializable {

    private String userID;

    private String language;

    public UserInfo() {
    }

    public UserInfo(String userID, String language) {
        this.userID = userID;
        this.language = language;
    }

    public String getUserID() {
        return userID;
    }

    public String getLanguage() {
        return language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserInfo userInfo = (UserInfo) o;

        if (userID != null ? !userID.equals(userInfo.userID) : userInfo.userID != null) return false;
        return language != null ? language.equals(userInfo.language) : userInfo.language == null;
    }

    @Override
    public int hashCode() {
        int result = userID != null ? userID.hashCode() : 0;
        result = 31 * result + (language != null ? language.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userID='" + userID + '\'' +
                ", language='" + language + '\'' +
                '}';
    }

}
