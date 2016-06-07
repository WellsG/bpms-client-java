package com.example.bpmsremote.client2;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;


public class BpmsClientTest {

    private BpmsClient bpmsClient;

    @Before
    public void setUp(){
        bpmsClient = BpmsClientUtil.client;
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
            long taskId = 68;
            Map<String, String> params = new HashMap<String, String>();
            params.put("performance", "good");
            bpmsClient.startHumantask(taskId, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testHumanTaskEvaluationc() {
        try {
            long taskId = 68;
            Map<String, String> params = new HashMap<String, String>();
            params.put("performance", "good");
            bpmsClient.completeHumantask(taskId, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testListTasks() {
        try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("potentialOwner", "xuliu");
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
            long taskId = 6;
            bpmsClient.getTaskContent(taskId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
