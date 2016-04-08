/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.types.v3.api;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

/**
 * Retains information regarding an SDK call.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.0.0
 */
public class SDKCallInfo {

    /**
     * Full request URL.
     */
    private String requestUrl;

    /**
     * Outbound HTTP headers. Use concrete type for marshalling.
     */
    private MultivaluedHashMap<String, Object> requestHeaders;

    /**
     * Response status code.
     */
    private int responseStatus;

    /**
     * Response HTTP headers. Use concrete type for marshalling.
     */
    private MultivaluedHashMap<String, Object> reponseHeaders;

    /**
     * Raw response body.
     */
    private String responseBody;

    /**
     * The Gateway may include an error code in a failure response.
     */
    private String errorCode;

    /**
     * The Gateway may include a human readable error message in a failure response.
     */
    private String errorMessage;


    /**
     * Exception thrown in response to call.
     */
    private Exception exception;

    public SDKCallInfo() {
    }

    public SDKCallInfo(String requestUrl, MultivaluedMap<String, Object> requestHeaders, int responseStatus,
                       MultivaluedMap<String, Object> responseHeaders, String responseBody) {
        this.responseBody = responseBody;
        this.reponseHeaders = asConcreteMap(responseHeaders);
        this.responseStatus = responseStatus;
        this.requestHeaders = asConcreteMap(requestHeaders);
        this.requestUrl = requestUrl;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public MultivaluedMap<String, Object> getRequestHeaders() {
        return requestHeaders;
    }

    public void setRequestHeaders(MultivaluedHashMap<String, Object> requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    public int getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(int responseStatus) {
        this.responseStatus = responseStatus;
    }

    public MultivaluedMap<String, Object> getReponseHeaders() {
        return reponseHeaders;
    }

    public void setReponseHeaders(MultivaluedHashMap<String, Object> reponseHeaders) {
        this.reponseHeaders = reponseHeaders;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    private MultivaluedHashMap<String, Object> asConcreteMap(MultivaluedMap<String, Object> abstractMap) {
        MultivaluedHashMap<String, Object> returnMap = new MultivaluedHashMap<>();
        if (abstractMap != null)
            abstractMap.keySet().stream().forEach(key -> returnMap.put(key, abstractMap.get(key)));
        return returnMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SDKCallInfo callInfo = (SDKCallInfo) o;

        if (responseStatus != callInfo.responseStatus) return false;
        if (requestUrl != null ? !requestUrl.equals(callInfo.requestUrl) : callInfo.requestUrl != null) return false;
        if (requestHeaders != null ? !requestHeaders.equals(callInfo.requestHeaders) : callInfo.requestHeaders != null)
            return false;
        if (reponseHeaders != null ? !reponseHeaders.equals(callInfo.reponseHeaders) : callInfo.reponseHeaders != null)
            return false;
        if (responseBody != null ? !responseBody.equals(callInfo.responseBody) : callInfo.responseBody != null)
            return false;
        if (errorCode != null ? !errorCode.equals(callInfo.errorCode) : callInfo.errorCode != null) return false;
        if (errorMessage != null ? !errorMessage.equals(callInfo.errorMessage) : callInfo.errorMessage != null)
            return false;
        return exception != null ? exception.equals(callInfo.exception) : callInfo.exception == null;
    }

    @Override
    public int hashCode() {
        int result = requestUrl != null ? requestUrl.hashCode() : 0;
        result = 31 * result + (requestHeaders != null ? requestHeaders.hashCode() : 0);
        result = 31 * result + responseStatus;
        result = 31 * result + (reponseHeaders != null ? reponseHeaders.hashCode() : 0);
        result = 31 * result + (responseBody != null ? responseBody.hashCode() : 0);
        result = 31 * result + (errorCode != null ? errorCode.hashCode() : 0);
        result = 31 * result + (errorMessage != null ? errorMessage.hashCode() : 0);
        result = 31 * result + (exception != null ? exception.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SDKCallInfo{" +
                "requestUrl='" + requestUrl + '\'' +
                ", responseStatus=" + responseStatus +
                ", responseBody='" + responseBody + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", exception=" + exception +
                '}';
    }

}
