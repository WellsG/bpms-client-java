package com.example.bpmsremote.client2;

import java.io.IOException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.params.AuthPolicy;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.auth.BasicSchemeFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(BpmsClientImpl.class);

    public static HttpClient wrapBasicClient(org.apache.http.client.HttpClient base) {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
                        throws java.security.cert.CertificateException {
                }
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
                        throws java.security.cert.CertificateException {
                }
            };
            X509HostnameVerifier hostnameVerifier = new AllowAllHostnameVerifier();
            ctx.init(null, new TrustManager[] { tm }, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx, hostnameVerifier);
            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("https", 443, ssf));
            registry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
            ClientConnectionManager mgr = new PoolingClientConnectionManager(registry);

            BasicSchemeFactory bsf = new BasicSchemeFactory();
            DefaultHttpClient httpclient = new DefaultHttpClient(mgr, base.getParams());
            httpclient.getAuthSchemes().register(AuthPolicy.BASIC, bsf);
            Credentials credential = new UsernamePasswordCredentials("wguo", "redhat");
            httpclient.getCredentialsProvider().setCredentials(AuthScope.ANY, credential);
            return httpclient;
        } catch (Exception ex) {
            LOGGER.error("Wrap httpclient error.", ex);
            return null;
        }
    }
}
