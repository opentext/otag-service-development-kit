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
 * @version 16.0.0
 */
public class OtagUrlUpdateHandler extends AbstractSettingChangeHandler {

    private static final Log LOG = LogFactory.getLog(OtagUrlUpdateHandler.class);

    private static final String OTAG_URL = "otag.url";

    /**
     * The URL that the Gateway believes it should be contacted using.
     */
    private String otagUrl;

    @Override
    public String getSettingKey() {
        return OTAG_URL;
    }

    @Override
    public void onSettingChanged(SettingsChangeMessage message) {
        if (LOG.isDebugEnabled())
            LOG.debug("OTAG URL change detected");

        String newOtagUrl = message.getNewValue();
        otagUrl = newOtagUrl;

        if (LOG.isDebugEnabled())
            LOG.debug("updated URL to " + newOtagUrl);
    }

    public String getOtagUrl() {
        if (otagUrl == null) {
            // try the meta-data file if we haven't been informed of any changes to the
            // url settings value
            AWConfig appWorksConfig = new AWConfig();
            otagUrl = appWorksConfig.getGatewayUrl();
        }
        return otagUrl;
    }

}
