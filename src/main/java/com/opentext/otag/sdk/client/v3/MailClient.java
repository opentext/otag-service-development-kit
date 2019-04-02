/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.client.v3;

import com.opentext.otag.sdk.SdkEventCallbackHandler;
import com.opentext.otag.sdk.bus.SdkEventKeys;
import com.opentext.otag.sdk.bus.SdkQueueEvent;
import com.opentext.otag.sdk.bus.SdkQueueManager;
import com.opentext.otag.sdk.types.v3.MailRequest;
import com.opentext.otag.sdk.types.v3.MailResult;
import com.opentext.otag.sdk.types.v3.api.SDKResponse;
import com.opentext.otag.sdk.types.v3.api.error.APIException;
import com.opentext.otag.sdk.types.v4.SdkRequest;
import com.opentext.otag.service.context.AWConfigFactory;

import javax.ws.rs.client.Client;
import java.util.Optional;

/**
 * Mail API Service client that exposes the Gateways email sending functionality.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.2
 */
public class MailClient extends AbstractOtagServiceClient {

    public MailClient() {
        super();
    }

    public MailClient(Client restClient, AWConfigFactory configurationLoaderFactory) {
        // testing constructor
        super(restClient, configurationLoaderFactory);
    }

    /**
     * Send an email to as many recipients as you like in a fire-and-forget fashion. Errors will be
     * logged at the Gateway but they will not be indicated in the response to this call.
     *
     * @param mailRequest a mail request
     * @return sdk response with a success indicator, true if the request manages to occur (even if the
     * mail was not sent by the Gateway)
     * @throws APIException if a non 200 response is received
     */
    public SDKResponse sendMailAsync(MailRequest mailRequest) {
        SdkRequest<MailRequest> request = new SdkRequest<>(SdkEventKeys.SEND_IMPORTANT_MAIL, mailRequest);
        SdkQueueEvent sdkRequest = SdkQueueEvent.request(request, getAppName(), getPersistenceContext());

        String sdkEventIdentifier = sdkRequest.getSdkEventIdentifier();
        try {
            SdkEventCallbackHandler.getSdkCallbackMgr().prepareForResponse(sdkEventIdentifier);
            SdkQueueManager.sendEventToGateway(sdkRequest);
            SdkQueueEvent responseEvent = SdkEventCallbackHandler.getSdkCallbackMgr()
                    .getResponseForEvent(sdkEventIdentifier);

            SDKResponse sdkResponse = responseEvent.getSdkResponse();

            if (sdkResponse != null) {
                return sdkResponse;
            } else {
                throw new APIException("The SDK response did not contain any information");
            }

        } catch (InterruptedException e) {
            throw new APIException("The sendMail SDK operation timed out", e);
        }
    }

    /**
     * Send a limited number of email/s via the AppWorks Gateway Mail service and
     * get a response. This method will return a MailResult letting you know
     * the outcome of your request unlike the bulk mail endpoint. As
     * mail is sent asynchronously by the Gateway then this method will
     * wait for completion.
     * <p>
     * Use this when you really care about the result of sending the MailRequest,
     * the Gateway will reject sending of large number of emails this way as well.
     * We doubt you care about who on a list of hundreds of users fails to get a
     * particular message hence the different methods.
     *
     * @param mailRequest email request
     * @return true if the request succeeded false otherwise
     * @throws APIException if a non 200 response is received
     * @see MailRequest
     */
    public MailResult sendMail(MailRequest mailRequest) {
        String endpointId = SdkEventKeys.SEND_IMPORTANT_MAIL;
        SdkRequest<MailRequest> request = new SdkRequest<>(endpointId, mailRequest);
        SdkQueueEvent sdkRequest = SdkQueueEvent.request(request, getAppName(), getPersistenceContext());

        String sdkEventIdentifier = sdkRequest.getSdkEventIdentifier();
        try {
            SdkEventCallbackHandler.getSdkCallbackMgr().prepareForResponse(sdkEventIdentifier);

            SdkQueueManager.sendEventToGateway(sdkRequest);
            SdkQueueEvent responseEvent = SdkEventCallbackHandler.getSdkCallbackMgr()
                    .getResponseForEvent(sdkEventIdentifier);
            Optional<MailResult> mailResult = SdkQueueEvent.extractBodyFromResponse(responseEvent, MailResult.class);
            return mailResult.orElseThrow(() -> new APIException("Response body missing from SDK event"));
        } catch (InterruptedException e) {
            throw new APIException("The sendMail SDK operation timed out", e);
        }
    }

}
