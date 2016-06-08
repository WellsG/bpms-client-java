package com.example.bpmsremote.client2;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;


public class BpmsClientTest {

    private BpmsClient bpmsClient;

    @Before
    public void setUp(){
        bpmsClient = BpmsClientUtil.getBpmsClient();
    }

    @Test
    public void test(){
        try {
            String deploymentId = "com.redhat.fls.repo:RepoRequest:1.1.8";
            String processDefId = "RepoRequest.RepoRequestProcess";
            Map<String, String> params = new HashMap<String, String>();
            params.put("repoName", "testrest");
            params.put("ldapGroup", "true");
            params.put("groupName", "devel");
            params.put("repoDesc", "test");
            bpmsClient.startProcess(deploymentId, processDefId, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testStartEvaluation() {
        try {
            String deploymentId = "org.jbpm:evaluation:2.1";
            String processDefId = "evaluation";
            Map<String, String> params = new HashMap<String, String>();
            params.put("employee", "wguo");
            params.put("reason", "devel");
            bpmsClient.startProcess(deploymentId, processDefId, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testHumanTaskEvaluation() {
        try {
            long taskId = 72;
            Map<String, String> params = new HashMap<String, String>();
            params.put("performance", "good");
            params.put("userId", "wguo");
            bpmsClient.startHumantask(taskId, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testHumanTaskEvaluationc() {
        try {
            long taskId = 72;
            Map<String, String> params = new HashMap<String, String>();
            params.put("performance", "good");
            params.put("userId", "wguo");
            bpmsClient.completeHumantask(taskId, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testListTasks() {
        try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("potentialOwner", "wguo");
            bpmsClient.listAssignTask(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testListInstances() {
        try {
            bpmsClient.listInstances();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetTaskContent() {
        try {
            long taskId = 3;
            bpmsClient.getTaskContent(taskId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testGetProcessImage() {
        try {
            long processId = 93;
            bpmsClient.getProcessImage(processId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
