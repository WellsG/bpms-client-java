package com.example.bpmsremote.client2;


public class BpmsClientUtil {

    private static BpmsClient client = null;
    private static final String url = "http://{host}:{port}/business-central/rest";

    public synchronized static BpmsClient getBpmsClient() {
        if (client == null){
            client = BpmsClientImpl.create(url);
        }
        return client;
    }

}
