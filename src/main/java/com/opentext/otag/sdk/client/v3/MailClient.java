/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.client.v3;

import com.opentext.otag.sdk.types.v3.MailRequest;
import com.opentext.otag.sdk.types.v3.MailResult;
import com.opentext.otag.sdk.types.v3.api.SDKCallInfo;
import com.opentext.otag.sdk.types.v3.api.SDKResponse;
import com.opentext.otag.sdk.types.v3.api.error.APIException;
import com.opentext.otag.sdk.util.UrlPathUtil;
import com.opentext.otag.service.context.AWConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.AsyncInvoker;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.concurrent.Future;

/**
 * Mail API Service client that exposes the Gateways email sending functionality.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.0.1
 */
public class MailClient extends AbstractOtagServiceClient {

    private static final Logger LOG = LoggerFactory.getLogger(MailClient.class);

    public static final String MAIL_SERVICE_PATH = OTAG_DEPLOYMENTS_SERVICE_PATH + "email/";

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
     *         mail was not sent by the Gateway)
     * @throws APIException if a non 200 response is received
     */
    public SDKResponse sendMailAsync(MailRequest mailRequest) {

        String mailUrl = getManagingOtagUrl() + MAIL_SERVICE_PATH + appName + "/send";

        WebTarget target = restClient.target(UrlPathUtil.getBaseUrl(mailUrl))
                .path(UrlPathUtil.getPath(mailUrl));

        Entity<MailRequest> mailRequestEntity = Entity.entity(
                mailRequest, MediaType.APPLICATION_JSON_TYPE);

        MultivaluedMap<String, Object> requestHeaders = getSDKRequestHeaders();
        try {
            return processGenericPost(mailUrl, target, mailRequestEntity, requestHeaders);
        } catch (Exception e) {
            LOG.error("Failed to send mail via Gateway", e);
            throw processFailureResponse(mailUrl, requestHeaders, e);
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
     *
     * @see MailRequest
     */
    public MailResult sendMail(MailRequest mailRequest) {
        String mailUrl = getManagingOtagUrl() + MAIL_SERVICE_PATH + appName + "/important/send";

        WebTarget target = restClient.target(UrlPathUtil.getBaseUrl(mailUrl))
                .path(UrlPathUtil.getPath(mailUrl));

        MultivaluedMap<String, Object> requestHeaders = getSDKRequestHeaders();
        try {

            // sending mail is an asynchronous request
            AsyncInvoker asyncInvoker = target.request()
                    .headers(requestHeaders)
                    .async();

            Entity<MailRequest> mailRequestEntity = Entity.entity(
                    mailRequest, MediaType.APPLICATION_JSON_TYPE);
            Future<Response> responseFuture = asyncInvoker.post(mailRequestEntity);
            Response response = responseFuture.get();

            int responseStatus = response.getStatus();
            String responseBody = response.readEntity(String.class);
            MultivaluedMap<String, Object> responseHeaders = response.getHeaders();

            validateResponse(mailUrl, requestHeaders, responseStatus, responseBody, responseHeaders);

            MailResult mailResult = getMapper().readValue(responseBody, MailResult.class);
            mailResult.setSdkCallInfo(new SDKCallInfo(mailUrl, requestHeaders, responseStatus,
                    responseHeaders, responseBody));
            return mailResult;
        } catch (Exception e) {
            // pass back an unsuccessful mail result with the exception type and message
            LOG.error("Failed to send important mail", e);
            throw processFailureResponse(mailUrl, requestHeaders, e);
        }
    }

}
