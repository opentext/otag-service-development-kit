/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.types.v3.auth;

import com.opentext.otag.sdk.types.v3.SDKType;
import com.opentext.otag.sdk.types.v3.api.SDKCallInfo;

import java.util.ArrayList;
import java.util.List;

public class UserGroupIdList extends SDKType {

    private List<String> groupIds = new ArrayList<>();

    public UserGroupIdList() {
    }

    public UserGroupIdList(List<String> groupIds) {
        this.groupIds = groupIds;
    }

    public UserGroupIdList(List<String> groupIds, SDKCallInfo sdkCallInfo) {
        super(sdkCallInfo);
        this.groupIds = groupIds;
    }

    public List<String> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(List<String> groupIds) {
        this.groupIds = groupIds;
    }

}
