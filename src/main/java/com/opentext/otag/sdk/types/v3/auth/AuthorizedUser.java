/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.types.v3.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthorizedUser extends ExpiringSession implements Cloneable, User {

    private String clientID;

    private String userID;

    private String userName;

    private String firstName;

    private String lastName;

    private String email;

    private boolean admin;

    private boolean external;

    /**
     * Internal version number we retain for clustered access consistency.
     */
    private int version;

    public AuthorizedUser() {
    }

    public AuthorizedUser(String firstName, String lastName, String userName, String clientID, String userID,
                          boolean isAdmin, boolean isExternal, String email, int version, long maxAge) {
        super(maxAge);
        this.userName = userName;
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.clientID = clientID;
        this.admin = isAdmin;
        this.external = isExternal;
        this.email = email;
        this.version = version;
    }

    public AuthorizedUser(AuthorizedUser u, long maxAge) {
        super(maxAge);
        this.userName = u.userName;
        this.clientID = u.clientID;
        this.admin = u.admin;
        this.expires = u.expires;
        this.external = u.external;
        this.version = u.version;
    }

    /**
     * Allows us to relay user info to those clients who aren't using Gateway sessions
     * (OAuth clients and the like).
     *
     * @param user user
     */
    public AuthorizedUser(User user) {
        // no max-age or version associated with this user
        super(0);
        this.version = 0;
        this.userName = user.getUserName();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.clientID = user.getClientID();
        this.userID = user.getUserID();
        this.admin = user.isAdmin();
        this.external = user.isExternal();
        this.email = user.getEmail();
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getClientID() {
        return clientID;
    }

    @Override
    public String getUserID() {
        return userID;
    }

    @JsonProperty("admin")
    @Override
    public boolean isAdmin() {
        return admin;
    }

    @JsonProperty("external")
    @Override
    public boolean isExternal() {
        return external;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @JsonIgnore
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void touch(long maxAge) {
        super.touch(maxAge);
        this.version++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        AuthorizedUser that = (AuthorizedUser) o;

        if (admin != that.admin) return false;
        if (external != that.external) return false;
        if (version != that.version) return false;
        if (clientID != null ? !clientID.equals(that.clientID) : that.clientID != null) return false;
        if (userID != null ? !userID.equals(that.userID) : that.userID != null) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        return email != null ? email.equals(that.email) : that.email == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (clientID != null ? clientID.hashCode() : 0);
        result = 31 * result + (userID != null ? userID.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (admin ? 1 : 0);
        result = 31 * result + (external ? 1 : 0);
        result = 31 * result + version;
        return result;
    }

    @Override
    public String toString() {
        return "AuthorizedUser{" +
                "clientID='" + clientID + '\'' +
                ", userID='" + userID + '\'' +
                ", userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", admin=" + admin +
                ", external=" + external +
                ", version=" + version +
                '}';
    }

}

