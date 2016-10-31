/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.types.v3.notification;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Our general push notification payload object, sent within the "data" of our JSON requests to the
 * push notification service.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.1
 */
public class GeneralPayload implements Serializable {

    public static final String PARAM_TARGET = "target";
    public static final String PARAM_SUMMARY = "summary";
    public static final String PARAM_CREATED = "created";

    /**
     * Useful when we have existing JSON friendly types to wrap.
     */
    protected static final ObjectMapper objectMapper = new ObjectMapper();

    protected static final TypeReference<HashMap<String, Object>> typeRef
            = new TypeReference<HashMap<String, Object>>() {
    };
    /**
     * The target application on the device/runtime. If the target it the runtime
     * itself (for example app install events) then this field is blank (empty string).
     */
    private String target = "";

    /**
     * The summary of the message.
     */
    private String summary;

    /**
     * The time we created it (as opposed to when GCM delivers it)
     */
    private long created;

    public GeneralPayload() {
    }

    public GeneralPayload(String summary) {
        this.summary = summary;
        this.created = new Date().getTime();
    }

    public GeneralPayload(String target, String summary) {
        this.target = target;
        this.summary = summary;
        this.created = new Date().getTime();
    }

    public GeneralPayload(String summary, long created, String target) {
        this.summary = summary;
        this.created = created;
        this.target = target;
    }

    public String getTarget() {
        return target;
    }

    public String getSummary() {
        return summary;
    }

    public long getCreated() {
        return created;
    }

    public Map<String, Object> asMap() {
        Map<String, Object> asMap = new HashMap<>();

        asMap.put(PARAM_TARGET, target);
        asMap.put(PARAM_SUMMARY, summary);
        asMap.put(PARAM_CREATED, created);

        return asMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GeneralPayload that = (GeneralPayload) o;

        if (created != that.created) return false;
        if (target != null ? !target.equals(that.target) : that.target != null) return false;
        return summary != null ? summary.equals(that.summary) : that.summary == null;
    }

    @Override
    public int hashCode() {
        int result = target != null ? target.hashCode() : 0;
        result = 31 * result + (summary != null ? summary.hashCode() : 0);
        result = 31 * result + (int) (created ^ (created >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "GeneralPayload{" +
                "target='" + target + '\'' +
                ", summary='" + summary + '\'' +
                ", created=" + created +
                '}';
    }

}


