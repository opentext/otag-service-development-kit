/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.types.v3;

import com.opentext.otag.sdk.util.StringUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Returned by the AppWorks Gateway in response to a mail request.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.2
 */
public class MailResult extends SDKType implements Serializable {

    private boolean success = false;

    private String message;

    private String from;

    private String subject;

    private String body;

    /**
     * The list of recipients we did not manage to send the email to.
     */
    private List<String> failedTo = new ArrayList<>();

    public MailResult() {
    }

    public MailResult(String from, boolean success, String subject,
                      String body, List<String> failedTo, String message) {
        this.from = from;
        this.success = success;
        this.subject = subject;
        this.body = body;
        this.failedTo = failedTo;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getFrom() {
        return from;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public List<String> getFailedTo() {
        return failedTo;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        MailResult that = (MailResult) o;

        if (success != that.success) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        if (from != null ? !from.equals(that.from) : that.from != null) return false;
        if (subject != null ? !subject.equals(that.subject) : that.subject != null) return false;
        if (body != null ? !body.equals(that.body) : that.body != null) return false;
        return failedTo != null ? failedTo.equals(that.failedTo) : that.failedTo == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (success ? 1 : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (from != null ? from.hashCode() : 0);
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (failedTo != null ? failedTo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MailResult{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", from='" + from + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", failedTo=" + StringUtil.toListString(failedTo, true) +
                ", sdkCallInfo'" + getSdkCallInfo() + '\'' +
                '}';
    }

}

