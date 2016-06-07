package com.example.bpmsremote.model;

public class JaxbTaskSummary {

    private long id;
    private String name;
    private String deploymentId;
    private String status;
    private long createdOn;
    public long getCreatedOn() {
        return createdOn;
    }
    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }
    public String getDeploymentId() {
        return deploymentId;
    }
    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
