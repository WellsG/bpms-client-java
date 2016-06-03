package com.example.bpmsremote.client2;


public class BpmsClientUtil {

    static final BpmsClient client;

    static {
        String url = "https://{host}:{port}/business-central/rest";
        client = BpmsClientImpl.create(url);
    }

    public static BpmsClient setUp() {
        return client;
    }

}
