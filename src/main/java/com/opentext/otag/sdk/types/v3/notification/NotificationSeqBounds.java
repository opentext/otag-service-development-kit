/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.types.v3.notification;

import com.opentext.otag.sdk.types.v3.SDKType;

import java.io.Serializable;

/**
 * The Gateway assigns sequential ids to new notifications but periodically clears the table
 * down. This type represents the id of the oldest event the Gateway knows about as well as its
 * newest.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.2
 */
public class NotificationSeqBounds extends SDKType implements Serializable {

    private long currentMinSeq;

    private long currentMaxSeq;

    public NotificationSeqBounds() {
    }

    public NotificationSeqBounds(long currentMinSeq, long currentMaxSeq) {
        this.currentMinSeq = currentMinSeq;
        this.currentMaxSeq = currentMaxSeq;
    }

    public long getCurrentMinSeq() {
        return currentMinSeq;
    }

    public long getCurrentMaxSeq() {
        return currentMaxSeq;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        NotificationSeqBounds that = (NotificationSeqBounds) o;

        if (currentMinSeq != that.currentMinSeq) return false;
        return currentMaxSeq == that.currentMaxSeq;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (currentMinSeq ^ (currentMinSeq >>> 32));
        result = 31 * result + (int) (currentMaxSeq ^ (currentMaxSeq >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "NotificationSeqBounds{" +
                "currentMinSeq=" + currentMinSeq +
                ", currentMaxSeq=" + currentMaxSeq +
                '}';
    }

}

