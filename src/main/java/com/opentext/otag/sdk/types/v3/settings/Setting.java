/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.types.v3.settings;

import com.opentext.otag.sdk.types.v3.SDKType;

import java.io.Serializable;

/**
 * An AppWorks configuration setting. Settings can be owned by the Gateway
 * or by an AppWorks service.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.2
 */
public class Setting extends SDKType implements Serializable {

    /**
     * Unique configuration setting id.
     */
    private String key;

    /**
     * The name (id) of the AppWorks app/service that owns this configuration setting.
     */
    private String appName;

    /**
     * Configuration setting data type.
     */
    private SettingType type = SettingType.string;

    /**
     * Display name for the setting used by the Gateway administration console.
     */
    private String displayName;

    /**
     * Current configuration setting value.
     */
    private String value;

    /**
     * Default configuration setting value.
     */
    private String defaultValue;

    /**
     * Readable description of what this setting is recording.
     */
    private String description;

    /**
     * Configuration settings group.
     */
    private String group;

    /**
     * Has this setting been modified at all since its creation?
     */
    private boolean modified = false;

    /**
     * Can the setting be updated via the Gateway administration console.
     */
    private boolean readOnly = false;

    /**
     * Should the setting be relayed via the Gateway administration console.
     */
    private boolean confidential = false;

    /**
     * Sequence number used for ordering configuration settings within the Gateway
     * administration console.
     */
    private String seqNo;

    public Setting() {
    }

    public Setting(String key, String appName, SettingType type, String displayName,
                   String value, String defaultValue, String description, String group,
                   boolean modified, boolean readOnly, String seqNo) {
        this.key = key;
        this.appName = appName;
        this.type = type;
        this.displayName = displayName;
        this.value = value;
        this.defaultValue = defaultValue;
        this.description = description;
        this.group = group;
        this.modified = modified;
        this.readOnly = readOnly;
        this.seqNo = seqNo;
    }

    public Setting(String key, String appName, SettingType type, String displayName,
                   String value, String defaultValue, String description, boolean modified,
                   boolean readOnly, String seqNo) {
        this.key = key;
        this.appName = appName;
        this.type = type;
        this.displayName = displayName;
        this.value = value;
        this.defaultValue = defaultValue;
        this.description = description;
        this.modified = modified;
        this.readOnly = readOnly;
        this.seqNo = seqNo;
    }

    public Setting(String key, String appName, SettingType type, String displayName,
                   String value, String defaultValue, String description,
                   boolean modified, String seqNo) {
        this.key = key;
        this.appName = appName;
        this.type = type;
        this.displayName = displayName;
        this.value = value;
        this.defaultValue = defaultValue;
        this.description = description;
        this.modified = modified;
        this.seqNo = seqNo;
    }

    public String getKey() {
        return key;
    }

    public String getAppName() {
        return appName;
    }

    public SettingType getType() {
        return type;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public String getDescription() {
        return description;
    }

    public String getGroup() {
        return group;
    }

    public boolean isModified() {
        return modified;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public boolean isConfidential() {
        return confidential;
    }

    public void setConfidential(boolean confidential) {
        this.confidential = confidential;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Setting setting = (Setting) o;

        return key != null ? key.equals(setting.key) : setting.key == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (key != null ? key.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Setting{" +
                "key='" + key + '\'' +
                ", appName='" + appName + '\'' +
                ", type=" + type +
                ", displayName='" + displayName + '\'' +
                ", value='" + value + '\'' +
                ", defaultValue='" + defaultValue + '\'' +
                ", description='" + description + '\'' +
                ", group='" + group + '\'' +
                ", modified=" + modified +
                ", readOnly=" + readOnly +
                ", confidential=" + confidential +
                ", seqNo='" + seqNo + '\'' +
                '}';
    }

}
