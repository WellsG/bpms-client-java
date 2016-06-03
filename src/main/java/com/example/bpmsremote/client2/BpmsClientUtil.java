package com.example.bpmsremote.client2;


public class BpmsClientUtil {

    static final BpmsClient client;

    static {
        String url = "https://maitai-bpms-dt.host.qe.eng.pek2.redhat.com/business-central/rest";
        client = BpmsClientImpl.create(url);
    }

    public static BpmsClient setUp() {
        return client;
    }

}
