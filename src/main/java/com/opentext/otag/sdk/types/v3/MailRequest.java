/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.types.v3;

import com.opentext.otag.sdk.util.StringUtil;

import java.io.Serializable;
import java.util.List;

/**
 * Request that allows an email be sent to a number of recipients.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.2
 */
public class MailRequest implements Serializable {

    /**
     * The number of emails to be processed per batch, the AppWorks Gateway will refuse to send
     * more than this number in a given request.
     */
    public static final int MAX_TO_ADDRESSES = 100;

    private String from;

    private List<String> to;

    private String subject;

    private String body;

    public MailRequest() {
    }

    public MailRequest(String from, List<String> to, String subject, String body) {
        if (to.size() > MAX_TO_ADDRESSES)
            throw new IllegalArgumentException(
                    "\"To\" list can contain a maximum of 100 addresses");

        this.from = from;
        this.to = to;
        this.subject = subject;
        this.body = body;
    }

    public String getFrom() {
        return from;
    }

    public List<String> getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MailRequest that = (MailRequest) o;

        if (from != null ? !from.equals(that.from) : that.from != null) return false;
        if (to != null ? !to.equals(that.to) : that.to != null) return false;
        if (subject != null ? !subject.equals(that.subject) : that.subject != null) return false;
        return body != null ? body.equals(that.body) : that.body == null;
    }

    @Override
    public int hashCode() {
        int result = from != null ? from.hashCode() : 0;
        result = 31 * result + (to != null ? to.hashCode() : 0);
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MailRequest{" +
                "from='" + from + '\'' +
                ", to=" + StringUtil.toListString(to, true) +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                '}';
    }

}

