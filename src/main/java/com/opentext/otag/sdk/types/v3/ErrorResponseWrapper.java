/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.types.v3;

import java.io.Serializable;

/**
 * General response returned by the Gateway to wrap an error message.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.2
 */
public class ErrorResponseWrapper implements Serializable {

    /**
     * Message error key.
     */
    private String key;

    /**
     * A localised error message.
     */
    private String errorMessage;

    /**
     * An object that may represent the error.
     */
    private Object entity;

    /**
     * HTTP status code for the error being reported.
     */
    private int status;

    public ErrorResponseWrapper() {
    }

    public ErrorResponseWrapper(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ErrorResponseWrapper(String errorMessage, int status) {
        this.errorMessage = errorMessage;
        this.status = status;
    }

    public ErrorResponseWrapper(String key, String errorMessage, int status) {
        this.key = key;
        this.errorMessage = errorMessage;
        this.status = status;
    }

    public String getKey() {
        return key;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getStatus() {
        return status;
    }

    public Object getEntity() {
        return entity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ErrorResponseWrapper that = (ErrorResponseWrapper) o;

        if (status != that.status) return false;
        if (key != null ? !key.equals(that.key) : that.key != null) return false;
        if (errorMessage != null ? !errorMessage.equals(that.errorMessage) : that.errorMessage != null) return false;
        return entity != null ? entity.equals(that.entity) : that.entity == null;
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (errorMessage != null ? errorMessage.hashCode() : 0);
        result = 31 * result + (entity != null ? entity.hashCode() : 0);
        result = 31 * result + status;
        return result;
    }

    @Override
    public String toString() {
        return "ErrorResponseWrapper{" +
                "key='" + key + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", entity=" + entity +
                ", status=" + status +
                '}';
    }

}
