package com.example.bpmsremote.model;

import java.util.List;

public class TaskSummary {

    private List<JaxbTaskSummary> taskSummaryList;

    public List<JaxbTaskSummary> getTaskSummaryList() {
        return taskSummaryList;
    }

    public void setTaskSummaryList(List<JaxbTaskSummary> taskSummaryList) {
        this.taskSummaryList = taskSummaryList;
    }
}
