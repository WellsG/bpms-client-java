package com.example.bpmsremote.model;

public class JaxbProcessInstanceLog {

    private long processInstanceId;
    private String processId;
    private long start;
    private String externalId;

    public long getProcessInstanceId() {
        return processInstanceId;
    }
    public void setProcessInstanceId(long processInstanceId) {
        this.processInstanceId = processInstanceId;
    }
    public String getProcessId() {
        return processId;
    }
    public void setProcessId(String processId) {
        this.processId = processId;
    }
    public long getStart() {
        return start;
    }
    public void setStart(long start) {
        this.start = start;
    }
    public String getExternalId() {
        return externalId;
    }
    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }
}
