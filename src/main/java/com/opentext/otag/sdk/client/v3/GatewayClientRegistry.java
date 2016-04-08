/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.client.v3;

import com.opentext.otag.sdk.handlers.AWServiceContextHandler;
import com.opentext.otag.sdk.handlers.AWServiceStartupComplete;
import com.opentext.otag.service.context.components.AWComponent;
import com.opentext.otag.service.context.components.AWComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Convenience class to gain access to the full suite of AppWorks Deployments Service API clients.
 * <p>
 * To use the client the static {@link #init} method must be called at some point after the Gateway
 * has confirmed startup can proceed (i.e. in your services startup complete method), or in a
 * context handlers onStart method). Once initialised the component will be available via the
 * {@link AWComponentContext}, use:
 * <p>
 * {@link AWComponentContext#getComponent(Class)}
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.0.0
 *
 * @see AWServiceStartupComplete
 * @see AWServiceContextHandler#onStart(String)
 */
public class GatewayClientRegistry implements AWComponent {

    private static final Logger LOG = LoggerFactory.getLogger(GatewayClientRegistry.class);
    private static GatewayClientRegistry instance;
    private AuthClient authClient;
    private MailClient mailClient;
    private NotificationsClient notificationsClient;
    private RuntimesClient runtimesClient;
    private ServiceClient serviceClient;
    private SettingsClient settingsClient;
    private TrustedProviderClient trustedProviderClient;
    /**
     * Private singleton constructor, initialises all of our services.
     *
     * @throws IllegalStateException if called before the Gateway is ready or the app finds its deployment
     *                               area to be in an invalid state
     */
    private GatewayClientRegistry() {
        LOG.info("Initializing Gateway Service clients ...");

        LOG.info("Creating AuthClient");
        authClient = new AuthClient();
        LOG.info("Creating MailClient");
        mailClient = new MailClient();
        LOG.info("Creating NotificationClient");
        notificationsClient = new NotificationsClient();
        LOG.info("Creating RuntimesClient");
        runtimesClient = new RuntimesClient();
        LOG.info("Creating ServiceClient");
        serviceClient = new ServiceClient();
        LOG.info("Creating SettingsClient");
        settingsClient = new SettingsClient();
        LOG.info("Creating TrustedProviderClient");
        trustedProviderClient = new TrustedProviderClient();

        // once initialised add a reference to the AppWorks context
        AWComponentContext.add(this);
    }

    public static GatewayClientRegistry init() {
        if (instance == null)
            instance = new GatewayClientRegistry();
        return instance;
    }

    private static GatewayClientRegistry instance() {
        if (instance == null)
            throw new IllegalStateException("Please init() before using the services");
        return instance;
    }

    // service methods

    public AuthClient getAuthClient() {
        return instance().authClient;
    }

    public MailClient getMailClient() {
        return instance().mailClient;
    }

    public NotificationsClient getNotificationsClient() {
        return instance().notificationsClient;
    }

    public RuntimesClient getRuntimesClient() {
        return instance().runtimesClient;
    }

    public ServiceClient getServiceClient() {
        return instance().serviceClient;
    }

    public SettingsClient getSettingsClient() {
        return instance().settingsClient;
    }

    public TrustedProviderClient getTrustedProviderClient() {
        return instance().trustedProviderClient;
    }

    /**
     * Base class for registry users.
     */
    public static class RegistryUser {

        private final Logger LOG = LoggerFactory.getLogger(RegistryUser.class);

        private GatewayClientRegistry clients;

        protected GatewayClientRegistry gatewayClients() {
            if (clients == null) {
                clients = AWComponentContext.getComponent(GatewayClientRegistry.class);
                if (clients == null) LOG.warn("Failed to find the GatewayClientRegistry component in " +
                        "AppWorks service context, it must not have been initialised yet");
            }
            return clients;
        }

    }

}
