package com.example.bpmsremote.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class HistoryLog {

    @SerializedName("historyLogList")
    private List<ProcessInstanceLog> instanceLogs;

    public List<ProcessInstanceLog> getInstanceLogs() {
        return instanceLogs;
    }

    public void setInstanceLogs(List<ProcessInstanceLog> instanceLogs) {
        this.instanceLogs = instanceLogs;
    }

}
