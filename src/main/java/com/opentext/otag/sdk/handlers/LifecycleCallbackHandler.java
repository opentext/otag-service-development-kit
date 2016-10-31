/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.handlers;

import com.opentext.otag.sdk.types.v3.OtagServiceEvent;
import com.opentext.otag.sdk.types.v3.message.LifecycleChangeMessage;

/**
 * Handles AppWorks service life-cycle events. Allowing a service to perform actions
 * on install, uninstall, and upgrade/downgrade. These actions are initiated by
 * an administrator who performs those actions via the UI (usually) or REST API.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.1
 */
public interface LifecycleCallbackHandler extends OtagMessageHandler<LifecycleChangeMessage> {

    default boolean handles(OtagServiceEvent event) {
        return event != null && LifecycleChangeMessage.LIFE_CYCLE_EVENTS.contains(event);
    }

    default Class<LifecycleChangeMessage> getHandledType() {
        return LifecycleChangeMessage.class;
    }

    /**
     * Called when the service has been installed successfully.
     *
     * @param otagMessage install message
     */
    void onInstall(LifecycleChangeMessage otagMessage);

    /**
     * Called after the service has re-deployed successfully after upgrade/downgrade action.
     *
     * @param otagMessage upgrade/downgrade message
     */
    void onChangeVersion(LifecycleChangeMessage otagMessage);

    /**
     * Called when the Gateway has been asked to uninstall the service. The Gateway will retain a record
     * of the service for historical purposes.
     *
     * @param otagMessage uninstall message
     */
    void onUninstall(LifecycleChangeMessage otagMessage);

}
