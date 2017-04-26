/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.types.v3.auth;

import com.opentext.otag.sdk.types.v3.api.SDKCallInfo;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.BasicAttributes;
import java.util.HashMap;

/**
 * OTDS profile, our base Gateway profile.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.2
 */
public class OtdsUserProfile extends UserProfile {

    public OtdsUserProfile() {
    }

    /**
     * Constructs an OtdsUserProfile representation that is safe to be exposed over the wire
     * to general clients (otds attributes not set).
     *
     * @param username user name
     * @param isAdmin  is this user an admin?
     */
    public OtdsUserProfile(String username, boolean isAdmin) {
        this.userName = username;
        this.userID = username;

        this.admin = isAdmin;
        this.attributes = new HashMap<>();
    }

    public OtdsUserProfile(String username, boolean isAdmin, SDKCallInfo sdkCallInfo) {
        super(sdkCallInfo);

        this.userName = username;
        this.userID = username;

        this.admin = isAdmin;
        this.attributes = new HashMap<>();
    }

    /**
     * Constructs an OtdsUserProfile representation that contains a list
     * of OTDS attributes, only admin level clients (including service agents)
     * should be able to retrieve this version.
     *
     * @param username   user name
     * @param isAdmin    is the user ad admin?
     * @param attributes OTDS user attributes
     */
    public OtdsUserProfile(String username, boolean isAdmin, BasicAttributes attributes) {
        this.userName = username;
        this.userID = username;

        this.admin = isAdmin;
        this.attributes = new HashMap<>();
        NamingEnumeration<Attribute> iter = attributes.getAll();
        try {
            while (iter.hasMore()) {
                Attribute attribute = iter.next();
                if (attribute.get() instanceof String) {
                    this.attributes.put(attribute.getID(), (String) attribute.get());
                }
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

}

