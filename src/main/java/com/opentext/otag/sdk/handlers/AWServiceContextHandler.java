/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.handlers;

import com.opentext.otag.service.context.components.AWComponent;

/**
 * Provide an implementation of this class to boostrap and tear down your
 * AppWorks service safely.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.2
 */
public interface AWServiceContextHandler extends AWComponent {

    /**
     * Called when the host web context is ready for the AppWorks service
     * to start safely.
     *
     * @param appName the name of the service
     */
    void onStart(String appName);

    /**
     * Called before the web context hosting the AppWorks service is stopped.
     *
     * @param appName the name of the service
     */
    void onStop(String appName);

}
