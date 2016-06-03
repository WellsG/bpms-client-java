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
            params.put("map_repoName", "testrest");
            params.put("map_ldapGroup", "true");
            params.put("map_groupName", "devel");
            params.put("map_repoDesc", "test");
            bpmsClient.startProcess(deploymentId, processDefId, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
