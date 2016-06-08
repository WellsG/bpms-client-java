package com.example.bpmsremote.client2;

import java.util.List;
import java.util.Map;

import com.example.bpmsremote.model.ProcessInstanceLog;
import com.example.bpmsremote.model.StartProcessResponse;
import com.example.bpmsremote.model.TaskContent;
import com.example.bpmsremote.model.TaskSummary;

public interface BpmsClient {

    public StartProcessResponse startProcess(String deploymentId, String processDefId, Map<String, String> params) throws Exception;
    public void startHumantask(long taksId ,Map<String, String> params) throws Exception;
    public void completeHumantask(long taksId ,Map<String, String> params) throws Exception;
    public TaskSummary listAssignTask(Map<String, String> params) throws Exception;
    public List<ProcessInstanceLog> listInstances() throws Exception;
    public TaskContent getTaskContent(long taskId) throws Exception;
    public String getProcessImage(long processInstanceId) throws Exception;
    

}
