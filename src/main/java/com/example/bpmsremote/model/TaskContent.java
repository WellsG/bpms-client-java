package com.example.bpmsremote.model;

import java.util.Map;

public class TaskContent {

    private long id;
    private Map<String, String> contentMap;
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public Map<String, String> getContentMap() {
        return contentMap;
    }
    public void setContentMap(Map<String, String> contentMap) {
        this.contentMap = contentMap;
    }
}
