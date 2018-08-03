/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.provided;

import com.opentext.otag.sdk.handlers.AbstractSettingChangeHandler;
import com.opentext.otag.sdk.types.v3.message.SettingsChangeMessage;
import com.opentext.otag.service.context.AWConfig;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

/**
 * We provide this handler out-of-the-box for our agent to talk to so it can
 * keep abreast of the endpoint we should be contacting for Gateway service
 * support services. As it is included in the SDK JAR it will be picked up
 * automatically when any AppWorks service is deployed.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.2
 */
public class OtagUrlUpdateHandler {

    private static final Log LOG = LogFactory.getLog(OtagUrlUpdateHandler.class);

    /**
     * The URL that the Gateway believes it should be contacted using.
     */
    private String otagUrl;

    public String getOtagUrl() {
        if (otagUrl == null) {
            // always use the value in the web.xml, which should be the local host name / address
            // for this node.  Don't use the otag.url setting, as this is an external address.
            AWConfig appWorksConfig = new AWConfig();
            otagUrl = appWorksConfig.getGatewayUrl();
            LOG.info("Using Gateway Service URL: " + otagUrl);
        }
        return otagUrl;
    }

}
