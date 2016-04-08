/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.types.v3.auth;

public interface User {

    String getUserName();

    String getClientID();

    String getUserID();

    boolean isAdmin();

    boolean isExternal();

    String getFirstName();

    String getLastName();

    String getEmail();

}

