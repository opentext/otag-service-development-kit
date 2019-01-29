package com.opentext.otag.sdk;

import com.opentext.otag.sdk.bus.SdkEventBusLog;
import com.opentext.otag.sdk.bus.SdkQueueCallbackManager;
import com.opentext.otag.sdk.bus.SdkQueueEvent;
import com.opentext.otag.sdk.bus.SdkQueueManager;
import com.opentext.otag.service.context.AWConfig;
import com.opentext.otag.service.context.AWConfigFactory;

public class SdkEventCallbackHandler {

    private static final Object lock = new Object();

    /**
     * Local callback manager for the SDK, use it register message ids before you send them.
     */
    private static volatile SdkQueueCallbackManager callbackManager;

    public static SdkQueueCallbackManager getSdkCallbackMgr() {
        if (callbackManager == null) {
            synchronized (lock) {
                callbackManager = initCallbackMgr();
            }
        }

        // return the local reference to lower access to the
        return callbackManager;
    }

    private static SdkQueueCallbackManager initCallbackMgr() {
        // load data from web.xml about our name and tenancy
        AWConfig appConfig = AWConfigFactory.defaultFactory().getConfig();
        String ctx = appConfig.getPersistenceContext();
        String name = appConfig.getAppName();

        callbackManager = SdkQueueCallbackManager.serviceCbackManager(name, ctx);
        SdkEventBusLog.info("Kick starting SDK event callback manager for " + name);
        SdkQueueManager.sendEventToService(name, ctx, SdkQueueEvent.start());

        return callbackManager;
    }

}
