package com.github.dmliasko.main.model.impl;

import com.github.dmliasko.main.model.RequestHeader;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by dmlia on 03.06.2017.
 */
public class RequestHeaderImpl implements RequestHeader {

    private String method;
    private String location;
    private String version;
    private Map<String, String> headers = new HashMap<String, String>();

    public RequestHeaderImpl(String requestData) {
            StringTokenizer tokenizer = new StringTokenizer(requestData);
            method = tokenizer.nextToken().toUpperCase();
            location = tokenizer.nextToken();
            version = tokenizer.nextToken();

            String[] lines = requestData.split("\r\n");
            for (int i = 1; i < lines.length; i++) {
                String[] keyVal = lines[i].split(":", 2);
                headers.put(keyVal[0], keyVal[1]);
            }
    }

    @Override
    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }
}
