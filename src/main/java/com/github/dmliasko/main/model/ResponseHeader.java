package com.github.dmliasko.main.model;

import java.util.Map;

/**
 * Created by dmlia on 03.06.2017.
 */
public interface ResponseHeader {

    void setVersion(String version);
    void setResponseCode(int responseCode);
    void setResponseReason(String responseReason);

    void setContentLength(int length);

    String getVersion();
    int getResponseCode();
    String getResponseReason();

    Map<String, String> getHeaders();

    byte[] getHeaderAsByte();


}
