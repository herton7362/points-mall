package com.framework.module.record.web;

import com.framework.module.record.domain.OperationRecord;
import com.kratos.module.auth.domain.OauthClientDetails;

public class OperationRecordResult extends OperationRecord {
    private OauthClientDetails client;

    public OauthClientDetails getClient() {
        return client;
    }

    public void setClient(OauthClientDetails client) {
        this.client = client;
    }
}
