package com.example.bpmsremote.client;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.task.TaskService;
import org.kie.api.task.model.TaskSummary;
import org.kie.remote.client.api.RemoteRuntimeEngineFactory;*/

public class ProcessService {
    
    public static void main(String[] args) throws Exception {
        /*RuntimeEngine engine = RemoteRuntimeEngineFactory.newRestBuilder()
                .addUrl(new URL("https://hostname:port/business-central")).addUserName("wguo").addPassword("redhat")
                .addDeploymentId("com.redhat.fls.repo:RepoRequest:1.1.8").build();
        KieSession ksession = engine.getKieSession();
        TaskService taskService = engine.getTaskService();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("repoName", "testrest");
        params.put("ldapGroup", "true");
        params.put("groupName", "devel");
        params.put("repoDesc", "test");
        ProcessInstance processInstance = ksession.startProcess("RepoRequest.RepoRequestProcess",params);
        String user = "wguo";
        String taskUserId = "wguo";
        long procId = processInstance.getId();

        taskService = engine.getTaskService();
        List<TaskSummary> tasks = taskService.getTasksAssignedAsPotentialOwner("wguo", "en-UK");

        long taskId = -1;
        for (TaskSummary task : tasks) {
            if (task.getProcessInstanceId() == procId) {
                taskId = task.getId();
            }
        }
        if (taskId == -1) {
            throw new IllegalStateException("Unable to find task for " + user + " in process instance " + procId);
        }
        taskService.start(taskId, taskUserId);*/
    }

}
