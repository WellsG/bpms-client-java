package com.example.bpmsremote.client2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.AuthPolicy;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicSchemeFactory;
import org.apache.http.impl.auth.NegotiateSchemeFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

@SuppressWarnings("deprecation")
public class BpmsClientImpl implements BpmsClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(BpmsClientImpl.class);
    /* /runtime/{deploymentId}/process/{processDefId}/start */
    private static final String PROCESS_START = "/runtime/%s/process/%s/start";

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
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json");
        try {
            HttpResponse response = client().execute(request);
            return parseResponse(response);
        } finally {
            request.abort();
        }
    }

    private String executePost(String url, Map<String, String> params) throws Exception {
        StringBuffer urls = new StringBuffer();
        urls.append(serverURL).append(url);
        String jsonParams = new Gson().toJson(params);
        LOGGER.info("Excute method: {}", urls.toString());
        LOGGER.info("Params: {}", jsonParams);
        HttpPost request = new HttpPost(urls.toString());
        //request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json");
        StringEntity entity = new StringEntity(jsonParams);
        
        
        ArrayList<NameValuePair> postParameters;
        postParameters = new ArrayList<NameValuePair>();
        for (String key : params.keySet()){
            postParameters.add(new BasicNameValuePair(key, params.get(key)));
        }
        entity.setContentType("application/json");
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
}
