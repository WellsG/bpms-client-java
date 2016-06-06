package com.example.bpmsremote.client2;

import java.util.Map;

public interface BpmsClient {

    public void startProcess(String deploymentId, String processDefId, Map<String, String> params) throws Exception;
    public void startHumantask(long taksId ,Map<String, String> params) throws Exception;
    public void completeHumantask(long taksId ,Map<String, String> params) throws Exception;
    public void listAssignTask(Map<String, String> params) throws Exception;
    

}
