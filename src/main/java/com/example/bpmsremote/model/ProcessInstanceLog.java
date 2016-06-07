package com.example.bpmsremote.model;

import com.google.gson.annotations.SerializedName;

public class ProcessInstanceLog {

    @SerializedName("org.kie.services.client.serialization.jaxb.impl.audit.JaxbProcessInstanceLog")
    private JaxbProcessInstanceLog log;

    public JaxbProcessInstanceLog getLog() {
        return log;
    }

    public void setLog(JaxbProcessInstanceLog log) {
        this.log = log;
    }

}
