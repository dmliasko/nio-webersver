package com.github.dmliasko.main.model.impl;

import com.github.dmliasko.main.model.ResponseHeader;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by dmlia on 03.06.2017.
 */
public class ResponseHeaderImpl implements ResponseHeader {

    private String version = "HTTP/1.1";
    private int responseCode = 200;
    private String responseReason = "OK";

    private Map<String, String> headers = new LinkedHashMap<String, String>();

    public ResponseHeaderImpl() {
        headers.put("Server", "github.com/dmliasko server");
        headers.put("Connection", "Closed");
    }

    @Override
    public void setContentLength(int length){
        headers.put("Content-Length", Integer.toString(length));
    }

    @Override
    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    @Override
    public void setResponseReason(String responseReason) {
        this.responseReason = responseReason;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public int getResponseCode() {
        return responseCode;
    }

    @Override
    public String getResponseReason() {
        return responseReason;
    }

    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    public byte[] getHeaderAsByte() {

        Set<String> keys = getHeaders().keySet();

        StringBuilder buf = new StringBuilder();
        buf.append(version).append(" ").append(responseCode).append(" ").append(responseReason).append("\r\n");

        for(String key: keys){
            buf.append(key).append(": ").append(headers.get(key)).append("\r\n");
        }

        buf.append("\r\n");
        return buf.toString().getBytes();
    }

    @Override
    public String toString() {
        return version + ' ' + responseCode +
                " " + responseReason + "\n" +
                "Date: " + headers.get("Date") + "\n"
                + "ServerImpl: " + headers.get("ServerImpl") + "\n"
                + "Connection" + headers.get("Connection") + "\n"
                + "Content-Length" + headers.get("Content-Length") + "\n";
    }
}
