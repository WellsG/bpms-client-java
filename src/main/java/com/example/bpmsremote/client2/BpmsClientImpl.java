package com.example.bpmsremote.client2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class BpmsClientImpl implements BpmsClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(BpmsClientImpl.class);
    /* /runtime/{deploymentId}/process/{processDefId}/start */
    private static final String PROCESS_START = "/runtime/%s/process/%s/start";
    private static final String HUMANTASK_START = "/task/%d/start";
    private static final String HUMANTASK_COMPLETE= "/task/%s/complete";
    private static final String LIST_TASK = "/task/query";
    private static final String HISTORY_INSTANCES = "/history/instances";
    private static final String GET_TASK_CONTENT = "/task/%d/content";
    private String serverURL;
    private HttpClient httpclient;

    public static BpmsClient create(String url) {
        final BpmsClientImpl impl = new BpmsClientImpl(url);
        return impl;
    }

    public BpmsClientImpl(String serverURL) {
        LOGGER.info("Starting BPMS client for server url: {}", serverURL);
        this.serverURL = serverURL;
    }

    public synchronized HttpClient client() {
        if (httpclient == null) {
            System.setProperty("sun.security.krb5.debug", "true");
            System.setProperty("jsse.enableSNIExtension", "false");
            System.setProperty("javax.security.auth.useSubjectCredsOnly", "false");
            httpclient = HttpClientUtils.wrapBasicClient(new DefaultHttpClient());
        }
        return httpclient;
    }

    private String execute(String url, Map<String, String> params) throws Exception {
        StringBuffer urls = new StringBuffer();
        urls.append(serverURL).append(url);
        if (params != null) {
            final List<NameValuePair> qparams = new ArrayList<NameValuePair>();
            if (params != null) {
                for (Map.Entry<String, String> param : params.entrySet()) {
                    qparams.add(new BasicNameValuePair(param.getKey(), param.getValue()));
                }
            }
            urls.append("?").append(URLEncodedUtils.format(qparams, "utf-8"));
        }
        
        
        LOGGER.info("Excute method: {}", urls.toString());
        HttpUriRequest request = new HttpGet(urls.toString());
        //request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json");
        try {
            HttpResponse response = client().execute(request);
            return parseResponse(response);
        } finally {
            request.abort();
        }
    }

    /*
     *  If you're triggering an operation with a REST API call that would normally
     *  (e.g. when interacting the same operation on a local KieSession or TaskService instance) 
     *  take an instance of a java.util.Map as one of its parameters, you can submit key-value 
     *  pairs to the operation to simulate this behaviour by passing a query parameter whose name
     *   starts with map_ 
     */
    private String executePost(String url, Map<String, String> params) throws Exception {
        StringBuffer urls = new StringBuffer();
        urls.append(serverURL).append(url);
        String jsonParams = new Gson().toJson(params);
        LOGGER.info("Excute method: {}", urls.toString());
        LOGGER.info("Params: {}", jsonParams);
        HttpPost request = new HttpPost(urls.toString());
        request.setHeader("Accept", "application/json");
        
        ArrayList<NameValuePair> postParameters;
        postParameters = new ArrayList<NameValuePair>();
        for (String key : params.keySet()){
            postParameters.add(new BasicNameValuePair("map_" + key, params.get(key)));
        }
        request.setEntity(new UrlEncodedFormEntity(postParameters));
        try {
            HttpResponse response = client().execute(request);
            return parseResponse(response);
        } finally {
            request.abort();
        }
    }

    private String executePut(String url, Map<String, Object> params) throws Exception {
        StringBuffer urls = new StringBuffer();
        urls.append(serverURL).append(url);
        String jsonParams = new Gson().toJson(params);
        LOGGER.info("Excute method: {}", urls.toString());
        LOGGER.info("Params: {}", jsonParams);
        HttpPut request = new HttpPut(urls.toString());
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json");
        StringEntity entity = new StringEntity(jsonParams);
        entity.setContentType("application/json");
        request.setEntity(entity);
        try {
            HttpResponse response = client().execute(request);
            return parseResponse(response);
        } finally {
            request.abort();
        }
    }

    private String parseResponse(HttpResponse response) throws Exception {
        StringBuffer sb = new StringBuffer();
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line = "";
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        if (sb.toString().contains("errors") || sb.toString().contains("error")) {
            LOGGER.error("{}", sb.toString());
            throw new Exception("http response code error: " + response.getStatusLine().getStatusCode() + "  " + sb.toString());
        }
        return sb.toString();
    }

    public void startProcess(String deploymentId, String processDefId, Map<String, String> params) throws Exception {
        String result = executePost(String.format(PROCESS_START, deploymentId, processDefId), params);
        System.out.println(result);
    }
    
    public void startHumantask(long taksId ,Map<String, String> params) throws Exception {
        String result = executePost(String.format(HUMANTASK_START, taksId), params);
        System.out.println(result);
    }
    
    public void completeHumantask(long taksId ,Map<String, String> params) throws Exception {
        String result = executePost(String.format(HUMANTASK_COMPLETE, taksId), params);
        System.out.println(result);
    }

    public void listAssignTask(Map<String, String> params) throws Exception {
        String result = execute(LIST_TASK, params);
        System.out.println(result);
    }

    public void listInstances() throws Exception {
        String result = execute(HISTORY_INSTANCES, null);
        System.out.println(result);
    }

    public void getTaskContent(long taskId) throws Exception {
        String result = execute(String.format(GET_TASK_CONTENT, taskId),null);
        System.out.println(result);
    }
}
