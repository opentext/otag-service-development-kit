/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.types.v3.management;

import java.io.Serializable;

/**
 * Represents the result of an AppWorks app/service deployment.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.2
 */
public class DeploymentResult implements Serializable {

    private boolean success;

    private String errorMessage;

    public DeploymentResult() {
    }

    public DeploymentResult(boolean success) {
        this.success = success;
    }

    // failed deployment ctor
    public DeploymentResult(String errorMessage) {
        this.success = false;
        this.errorMessage = errorMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeploymentResult that = (DeploymentResult) o;

        if (success != that.success) return false;
        return errorMessage != null ? errorMessage.equals(that.errorMessage) : that.errorMessage == null;
    }

    @Override
    public int hashCode() {
        int result = (success ? 1 : 0);
        result = 31 * result + (errorMessage != null ? errorMessage.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DeploymentResult{" +
                "success=" + success +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }

}
