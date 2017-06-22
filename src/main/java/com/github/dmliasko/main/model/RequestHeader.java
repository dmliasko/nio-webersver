package com.github.dmliasko.main.model;

import java.util.Map;

/**
 * Created by dmlia on 03.06.2017.
 */
public interface RequestHeader {

    void setMethod(String method);

    void setLocation(String location);

    void setVersion(String version);

    String getMethod();

    String getLocation();

    String getVersion();

    Map<String, String> getHeaders();

}
